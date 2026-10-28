<template>
  <div class="w-full">
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Activite' }) }}
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

    <activite-form
      :submit-label="$t('entity.create.submitLabel', { entity: 'Activite' })"
      :activitesMembresData="initialActivitesMembres"
      
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
import ActiviteForm from '@/entities/activite/ActiviteForm.vue'
import AlertPopup from '@/components/popup/AlertPopup.vue'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import { ActiviteFormDTO } from '@/models/ActiviteModel'
import { useActivites } from '@/composables/useActivites'
import { usePopup } from '@/composables/usePopup'
import { useFreezeScreenStore } from '@/stores/useFreezeScreenStore'
import { ref } from 'vue'
import type { ActivitesMembre, ActivitesMembreFormDTO } from '@/models/ActivitesMembreModel'


const { createActivite, goToListView, message, viewActivite } = useActivites()
const { openPopup, closePopup, visible: alertPopup } = usePopup()
const freezeStore = useFreezeScreenStore()
const violations = ref<
  | {
    activite: Record<string, string>
    activitesMembres: Record<string, string>[]
    
    }
  | undefined
>(undefined)
const initialActivitesMembres = ref<ActivitesMembre[]>([])


const createHandler = async (payload: {
  activite: Partial<ActiviteFormDTO>
  activitesMembres: ActivitesMembreFormDTO[]
  
}) => {
  freezeStore.freeze('Creating a new Activite ...')
  violations.value = undefined
  message.value = null
  try {
    const { data, errors } = await createActivite(payload)
    if (errors && Object.keys(errors).length > 0){
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
    console.error('Error creating activite:',error)
    openPopup()
  } finally {
    freezeStore.unfreeze()
  }
}


</script>
<style scoped></style>
