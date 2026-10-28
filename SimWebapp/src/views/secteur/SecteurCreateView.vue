<template>
  <div class="w-full">
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Secteur' }) }}
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

    <secteur-form
      :submit-label="$t('entity.create.submitLabel', { entity: 'Secteur' })"
      :membresData="initialMembres"
      
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
import SecteurForm from '@/entities/secteur/SecteurForm.vue'
import AlertPopup from '@/components/popup/AlertPopup.vue'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import { SecteurFormDTO } from '@/models/SecteurModel'
import { useSecteurs } from '@/composables/useSecteurs'
import { usePopup } from '@/composables/usePopup'
import { useFreezeScreenStore } from '@/stores/useFreezeScreenStore'
import { ref } from 'vue'
import type { Membre, MembreFormDTO } from '@/models/MembreModel'


const { createSecteur, goToListView, message, viewSecteur } = useSecteurs()
const { openPopup, closePopup, visible: alertPopup } = usePopup()
const freezeStore = useFreezeScreenStore()
const violations = ref<
  | {
    secteur: Record<string, string>
    membres: Record<string, string>[]
    
    }
  | undefined
>(undefined)
const initialMembres = ref<Membre[]>([])


const createHandler = async (payload: {
  secteur: Partial<SecteurFormDTO>
  membres: MembreFormDTO[]
  
}) => {
  freezeStore.freeze('Creating a new Secteur ...')
  violations.value = undefined
  message.value = null
  try {
    const { data, errors } = await createSecteur(payload)
    if (errors && Object.keys(errors).length > 0){
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
    console.error('Error creating secteur:',error)
    openPopup()
  } finally {
    freezeStore.unfreeze()
  }
}


</script>
<style scoped></style>
