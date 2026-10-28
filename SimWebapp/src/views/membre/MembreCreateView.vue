<template>
  <div class="w-full">
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Membre' }) }}
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

    <membre-form
      :submit-label="$t('entity.create.submitLabel', { entity: 'Membre' })"
      :activitesMembresData="initialActivitesMembres"
      :rendezVousData="initialRendezVous"
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
import MembreForm from '@/entities/membre/MembreForm.vue'
import AlertPopup from '@/components/popup/AlertPopup.vue'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import { MembreFormDTO } from '@/models/MembreModel'
import { useMembres } from '@/composables/useMembres'
import { usePopup } from '@/composables/usePopup'
import { useFreezeScreenStore } from '@/stores/useFreezeScreenStore'
import { ref } from 'vue'
import type { ActivitesMembre, ActivitesMembreFormDTO } from '@/models/ActivitesMembreModel'
import type { RendezVou, RendezVouFormDTO } from '@/models/RendezVouModel'
import type { SujetsMembre, SujetsMembreFormDTO } from '@/models/SujetsMembreModel'


const { createMembre, goToListView, message, viewMembre } = useMembres()
const { openPopup, closePopup, visible: alertPopup } = usePopup()
const freezeStore = useFreezeScreenStore()
const violations = ref<
  | {
    membre: Record<string, string>
    activitesMembres: Record<string, string>[]
    rendezVous: Record<string, string>[]
    sujetsMembres: Record<string, string>[]
    
    }
  | undefined
>(undefined)
const initialActivitesMembres = ref<ActivitesMembre[]>([])
const initialRendezVous = ref<RendezVou[]>([])
const initialSujetsMembres = ref<SujetsMembre[]>([])


const createHandler = async (payload: {
  membre: Partial<MembreFormDTO>
  activitesMembres: ActivitesMembreFormDTO[]
  rendezVous: RendezVouFormDTO[]
  sujetsMembres: SujetsMembreFormDTO[]
  
}) => {
  freezeStore.freeze('Creating a new Membre ...')
  violations.value = undefined
  message.value = null
  try {
    const { data, errors } = await createMembre(payload)
    if (errors && Object.keys(errors).length > 0){
      violations.value = errors as {
        membre: Record<string, string>
        activitesMembres: Record<string, string>[]
        rendezVous: Record<string, string>[]
        sujetsMembres: Record<string, string>[]
        
      }
    }
    if (data && !message.value) {
      viewMembre(data)
    } else {
      throw new Error(String(message.value))
    }
  } catch (error: unknown) {
    console.error('Error creating membre:',error)
    openPopup()
  } finally {
    freezeStore.unfreeze()
  }
}


</script>
<style scoped></style>
