<template>
  <div class="w-full">
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Sujet' }) }}
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

    <sujet-form
      :submit-label="$t('entity.create.submitLabel', { entity: 'Sujet' })"
      :sujetsMembresData="initialSujetsMembres"
      
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
import SujetForm from '@/entities/sujet/SujetForm.vue'
import AlertPopup from '@/components/popup/AlertPopup.vue'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import { SujetFormDTO } from '@/models/SujetModel'
import { useSujets } from '@/composables/useSujets'
import { usePopup } from '@/composables/usePopup'
import { useFreezeScreenStore } from '@/stores/useFreezeScreenStore'
import { ref } from 'vue'
import type { SujetsMembre, SujetsMembreFormDTO } from '@/models/SujetsMembreModel'


const { createSujet, goToListView, message, viewSujet } = useSujets()
const { openPopup, closePopup, visible: alertPopup } = usePopup()
const freezeStore = useFreezeScreenStore()
const violations = ref<
  | {
    sujet: Record<string, string>
    sujetsMembres: Record<string, string>[]
    
    }
  | undefined
>(undefined)
const initialSujetsMembres = ref<SujetsMembre[]>([])


const createHandler = async (payload: {
  sujet: Partial<SujetFormDTO>
  sujetsMembres: SujetsMembreFormDTO[]
  
}) => {
  freezeStore.freeze('Creating a new Sujet ...')
  violations.value = undefined
  message.value = null
  try {
    const { data, errors } = await createSujet(payload)
    if (errors && Object.keys(errors).length > 0){
      violations.value = errors as {
        sujet: Record<string, string>
        sujetsMembres: Record<string, string>[]
        
      }
    }
    if (data && !message.value) {
      viewSujet(data)
    } else {
      throw new Error(String(message.value))
    }
  } catch (error: unknown) {
    console.error('Error creating sujet:',error)
    openPopup()
  } finally {
    freezeStore.unfreeze()
  }
}


</script>
<style scoped></style>
