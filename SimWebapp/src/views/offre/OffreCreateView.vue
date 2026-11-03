<template>
  <div class="w-full">
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Offre' }) }}
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

    <offre-form
      :submit-label="$t('entity.create.submitLabel', { entity: 'Offre' })"
      :ciblesOffresData="initialCiblesOffres"
      :histoOffresData="initialHistoOffres"
      
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
import OffreForm from '@/entities/offre/OffreForm.vue'
import AlertPopup from '@/components/popup/AlertPopup.vue'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import { OffreFormDTO } from '@/models/OffreModel'
import { useOffres } from '@/composables/useOffres'
import { usePopup } from '@/composables/usePopup'
import { useFreezeScreenStore } from '@/stores/useFreezeScreenStore'
import { ref } from 'vue'
import type { CiblesOffre, CiblesOffreFormDTO } from '@/models/CiblesOffreModel'
import type { HistoOffre, HistoOffreFormDTO } from '@/models/HistoOffreModel'


const { createOffre, goToListView, message, viewOffre } = useOffres()
const { openPopup, closePopup, visible: alertPopup } = usePopup()
const freezeStore = useFreezeScreenStore()
const violations = ref<
  | {
    offre: Record<string, string>
    ciblesOffres: Record<string, string>[]
    histoOffres: Record<string, string>[]
    
    }
  | undefined
>(undefined)
const initialCiblesOffres = ref<CiblesOffre[]>([])
const initialHistoOffres = ref<HistoOffre[]>([])


const createHandler = async (payload: {
  offre: Partial<OffreFormDTO>
  ciblesOffres: CiblesOffreFormDTO[]
  histoOffres: HistoOffreFormDTO[]
  
}) => {
  freezeStore.freeze('Creating a new Offre ...')
  violations.value = undefined
  message.value = null
  try {
    const { data, errors } = await createOffre(payload)
    if (errors && Object.keys(errors).length > 0){
      violations.value = errors as {
        offre: Record<string, string>
        ciblesOffres: Record<string, string>[]
        histoOffres: Record<string, string>[]
        
      }
    }
    if (data && !message.value) {
      viewOffre(data)
    } else {
      throw new Error(String(message.value))
    }
  } catch (error: unknown) {
    console.error('Error creating offre:',error)
    openPopup()
  } finally {
    freezeStore.unfreeze()
  }
}


</script>
<style scoped></style>
