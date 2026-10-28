<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        Statut /
        <span class="text-base-content/50 font-normal">{{ $t('entity.update.nav') }}</span>
      </h3>
      <GenesisButton :title="$t('button.backToListDescription')" @click="goToListView" class="btn-secondary">
        <LeftArrowIcon />
        {{ $t('button.backToList') }}
      </GenesisButton>
    </div>

    <!-- Form -->
    <div v-if="entity&& statutRendezVouLoaded">
      <statut-form
        :statut="entity"
        :statutRendezVousData="statutRendezVous"
        
        :violations="violations"
        :submit-label="$t('entity.update.submitLabel', { entity: 'Statut' })"
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
import StatutForm from '@/entities/statut/StatutForm.vue'
import AlertPopup from '@/components/popup/AlertPopup.vue'
import { useStatuts } from '@/composables/useStatuts'
import { Statut, StatutFormDTO } from '@/models/StatutModel'
import { usePopup } from '@/composables/usePopup'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import { useFreezeScreenStore } from '@/stores/useFreezeScreenStore.ts'
import { useStatutRendezVous } from '@/composables/useStatutRendezVous'
import { StatutRendezVouFormDTO } from '@/models/StatutRendezVouModel'

import { PaginationRequestParameter } from '@/models/api/RequestModel'


const route = useRoute()
const pathId = Number(route.params.id)
const { closePopup, openPopup, visible: alertPopup } = usePopup()
const { getStatutById, goToListView, updateStatut, viewStatut, message } = useStatuts()
const entity = ref<Statut | null>(null)
const freezeStore = useFreezeScreenStore()

const { statutRendezVous, getAllStatutRendezVousByStatutId } = useStatutRendezVous();
const statutRendezVouLoaded = ref(false);
const loadStatutRendezVous = async () => {
  try {
    await getAllStatutRendezVousByStatutId(pathId, true, new PaginationRequestParameter(), [])
    statutRendezVouLoaded.value = true
  } catch (error) {
    console.error('Error loading statut with details:', error)
    statutRendezVouLoaded.value = true // Continuer même en cas d'erreur
  }
}

const violations = ref<
  | {
    statut: Record<string, string>
    statutRendezVous: Record<string, string>[]
    
    }
  | undefined
>(undefined)

const updateHandler = async (payload: {
  statut: Partial<StatutFormDTO>
  statutRendezVous: StatutRendezVouFormDTO[]
  
}) => {
  freezeStore.freeze('Updating statut ' + pathId + ' ...')
  message.value = null
  violations.value = {
    statut: {},
    statutRendezVous: [],
    
  }
  try {
    const { data, errors } = await updateStatut(pathId, payload)

    if (errors && Object.keys(errors).length > 0) {
      // Gérer les erreurs de validation
      violations.value = errors as {
        statut: Record<string, string>
        statutRendezVous: Record<string, string>[]
      
      }
    } 
    if (data && !message.value) {
      viewStatut(data)
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
    viewStatut(entity.value)
  }
}

onMounted(async () => {
  const result = await getStatutById(pathId)
  if (result.data){
      entity.value = result.data
      await loadStatutRendezVous()
      
  }
  else {
    openPopup()
  }
})

</script>
<style scoped></style>
