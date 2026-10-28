<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        Secteur /
        <span class="text-base-content/50 font-normal">{{ $t('entity.update.nav') }}</span>
      </h3>
      <GenesisButton :title="$t('button.backToListDescription')" @click="goToListView" class="btn-secondary">
        <LeftArrowIcon />
        {{ $t('button.backToList') }}
      </GenesisButton>
    </div>

    <!-- Form -->
    <div v-if="entity&& membreLoaded">
      <secteur-form
        :secteur="entity"
        :membresData="membres"
        
        :violations="violations"
        :submit-label="$t('entity.update.submitLabel', { entity: 'Secteur' })"
        @submit="updateHandler"
        @cancel="cancelHandler"
      />
    </div>

    <!-- Alert -->
    <AlertPopup
      :message="message ?? undefined"
      title="Error 500"
      :visible="alertPopup"
      @close="closePopup"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import SecteurForm from '@/entities/secteur/SecteurForm.vue'
import AlertPopup from '@/components/popup/AlertPopup.vue'
import { useSecteurs } from '@/composables/useSecteurs'
import { Secteur, SecteurFormDTO } from '@/models/SecteurModel'
import { usePopup } from '@/composables/usePopup'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import { useFreezeScreenStore } from '@/stores/useFreezeScreenStore.ts'
import { useMembres } from '@/composables/useMembres'
import { MembreFormDTO } from '@/models/MembreModel'

import { PaginationRequestParameter } from '@/models/api/RequestModel'


const route = useRoute()
const pathId = Number(route.params.id)
const { closePopup, openPopup, visible: alertPopup } = usePopup()
const { getSecteurById, goToListView, updateSecteur, viewSecteur, message } = useSecteurs()
const entity = ref<Secteur | null>(null)
const freezeStore = useFreezeScreenStore()

const { membres, getAllMembresBySecteurId } = useMembres();
const membreLoaded = ref(false);
const loadMembres = async () => {
  try {
    await getAllMembresBySecteurId(pathId, true, new PaginationRequestParameter(), [])
    membreLoaded.value = true
  } catch (error) {
    console.error('Error loading secteur with details:', error)
    membreLoaded.value = true // Continuer même en cas d'erreur
  }
}

const violations = ref<
  | {
    secteur: Record<string, string>
    membres: Record<string, string>[]
    
    }
  | undefined
>(undefined)

const updateHandler = async (payload: {
  secteur: Partial<SecteurFormDTO>
  membres: MembreFormDTO[]
  
}) => {
  freezeStore.freeze('Updating secteur ' + pathId + ' ...')
  message.value = null
  violations.value = {
    secteur: {},
    membres: [],
    
  }
  try {
    const { data, errors } = await updateSecteur(pathId, payload)

    if (errors && Object.keys(errors).length > 0) {
      // Gérer les erreurs de validation
      violations.value = errors as {
        secteur: Record<string, string>
        membres: Record<string, string>[]
      
      }
    } 
    if (data && !message.value) {
      viewSecteur(data)
    } else {
      throw new Error(String(message.value))
    }
  } catch (error: unknown) {
      console.error(error)
  } finally {
    freezeStore.unfreeze()
  }
}        


const cancelHandler = () => {
  if (entity.value){
    viewSecteur(entity.value)
  }
}

onMounted(async () => {
  const result = await getSecteurById(pathId)
  if (result.data){
      entity.value = result.data
      await loadMembres()
      
  }
  else {
    openPopup()
  }
})

</script>
<style scoped></style>
