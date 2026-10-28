<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        Activite /
        <span class="text-base-content/50 font-normal">{{ $t('entity.update.nav') }}</span>
      </h3>
      <GenesisButton :title="$t('button.backToListDescription')" @click="goToListView" class="btn-secondary">
        <LeftArrowIcon />
        {{ $t('button.backToList') }}
      </GenesisButton>
    </div>

    <!-- Form -->
    <div v-if="entity&& activitesMembreLoaded">
      <activite-form
        :activite="entity"
        :activitesMembresData="activitesMembres"
        
        :violations="violations"
        :submit-label="$t('entity.update.submitLabel', { entity: 'Activite' })"
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
import ActiviteForm from '@/entities/activite/ActiviteForm.vue'
import AlertPopup from '@/components/popup/AlertPopup.vue'
import { useActivites } from '@/composables/useActivites'
import { Activite, ActiviteFormDTO } from '@/models/ActiviteModel'
import { usePopup } from '@/composables/usePopup'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import { useFreezeScreenStore } from '@/stores/useFreezeScreenStore.ts'
import { useActivitesMembres } from '@/composables/useActivitesMembres'
import { ActivitesMembreFormDTO } from '@/models/ActivitesMembreModel'

import { PaginationRequestParameter } from '@/models/api/RequestModel'


const route = useRoute()
const pathId = Number(route.params.id)
const { closePopup, openPopup, visible: alertPopup } = usePopup()
const { getActiviteById, goToListView, updateActivite, viewActivite, message } = useActivites()
const entity = ref<Activite | null>(null)
const freezeStore = useFreezeScreenStore()

const { activitesMembres, getAllActivitesMembresByActiviteId } = useActivitesMembres();
const activitesMembreLoaded = ref(false);
const loadActivitesMembres = async () => {
  try {
    await getAllActivitesMembresByActiviteId(pathId, true, new PaginationRequestParameter(), [])
    activitesMembreLoaded.value = true
  } catch (error) {
    console.error('Error loading activite with details:', error)
    activitesMembreLoaded.value = true // Continuer même en cas d'erreur
  }
}

const violations = ref<
  | {
    activite: Record<string, string>
    activitesMembres: Record<string, string>[]
    
    }
  | undefined
>(undefined)

const updateHandler = async (payload: {
  activite: Partial<ActiviteFormDTO>
  activitesMembres: ActivitesMembreFormDTO[]
  
}) => {
  freezeStore.freeze('Updating activite ' + pathId + ' ...')
  message.value = null
  violations.value = {
    activite: {},
    activitesMembres: [],
    
  }
  try {
    const { data, errors } = await updateActivite(pathId, payload)

    if (errors && Object.keys(errors).length > 0) {
      // Gérer les erreurs de validation
      violations.value = errors as {
        activite: Record<string, string>
        activitesMembres: Record<string, string>[]
      
      }
    } 
    if (data && !message.value) {
      viewActivite(data)
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
    viewActivite(entity.value)
  }
}

onMounted(async () => {
  const result = await getActiviteById(pathId)
  if (result.data){
      entity.value = result.data
      await loadActivitesMembres()
      
  }
  else {
    openPopup()
  }
})

</script>
<style scoped></style>
