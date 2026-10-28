<template>
  <div class="w-full">
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Statut' }) }}
        <span class="text-base-content/50 font-normal">{{ $t('entity.create.nav') }}</span>
      </h3>
      <GenesisButton
        :title="$t('button.backToListDescription')"
        @click="goToListView"
        class="btn-secondary"
      >
        <LeftArrowIcon />
        {{ $t('button.backToList') }}
      </GenesisButton>
    </div>

    <statut-form
      :submit-label="$t('entity.create.submitLabel', { entity: 'Statut' })"
      :statutRendezVousData="initialStatutRendezVous"
      
      :violations="violations"
      @submit="createHandler"
      @cancel="goToListView"
    />

    <AlertPopup
      :message="message ?? undefined"
      title="Error"
      :visible="alertPopup"
      @close="closePopup"
    />
  </div>
</template>

<script setup lang="ts">
import StatutForm from '@/entities/statut/StatutForm.vue'
import AlertPopup from '@/components/popup/AlertPopup.vue'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import { StatutFormDTO } from '@/models/StatutModel'
import { useStatuts } from '@/composables/useStatuts'
import { usePopup } from '@/composables/usePopup'
import { useFreezeScreenStore } from '@/stores/useFreezeScreenStore'
import { ref } from 'vue'
import type { StatutRendezVou, StatutRendezVouFormDTO } from '@/models/StatutRendezVouModel'


const { createStatut, goToListView, message, viewStatut } = useStatuts()
const { openPopup, closePopup, visible: alertPopup } = usePopup()
const freezeStore = useFreezeScreenStore()
const violations = ref<
  | {
    statut: Record<string, string>
    statutRendezVous: Record<string, string>[]
    
    }
  | undefined
>(undefined)
const initialStatutRendezVous = ref<StatutRendezVou[]>([])


const createHandler = async (payload: {
  statut: Partial<StatutFormDTO>
  statutRendezVous: StatutRendezVouFormDTO[]
  
}) => {
  freezeStore.freeze('Creating a new Statut ...')
  violations.value = undefined
  message.value = null
  try {
    const { data, errors } = await createStatut(payload)
    if (errors && Object.keys(errors).length > 0){
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
    console.error('Error creating statut:',error)
    openPopup()
  } finally {
    freezeStore.unfreeze()
  }
}


</script>
<style scoped></style>
