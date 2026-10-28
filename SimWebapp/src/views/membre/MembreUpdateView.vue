<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        Membre /
        <span class="text-base-content/50 font-normal">{{ $t('entity.update.nav') }}</span>
      </h3>
      <GenesisButton :title="$t('button.backToListDescription')" @click="goToListView" class="btn-secondary">
        <LeftArrowIcon />
        {{ $t('button.backToList') }}
      </GenesisButton>
    </div>

    <!-- Form -->
    <div v-if="entity&& activitesMembreLoaded&& rendezVouLoaded&& sujetsMembreLoaded">
      <membre-form
        :membre="entity"
        :activitesMembresData="activitesMembres"
        :rendezVousData="rendezVous"
        :sujetsMembresData="sujetsMembres"
        
        :violations="violations"
        :submit-label="$t('entity.update.submitLabel', { entity: 'Membre' })"
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
import MembreForm from '@/entities/membre/MembreForm.vue'
import AlertPopup from '@/components/popup/AlertPopup.vue'
import { useMembres } from '@/composables/useMembres'
import { Membre, MembreFormDTO } from '@/models/MembreModel'
import { usePopup } from '@/composables/usePopup'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import { useFreezeScreenStore } from '@/stores/useFreezeScreenStore.ts'
import { useActivitesMembres } from '@/composables/useActivitesMembres'
import { ActivitesMembreFormDTO } from '@/models/ActivitesMembreModel'
import { useRendezVous } from '@/composables/useRendezVous'
import { RendezVouFormDTO } from '@/models/RendezVouModel'
import { useSujetsMembres } from '@/composables/useSujetsMembres'
import { SujetsMembreFormDTO } from '@/models/SujetsMembreModel'

import { PaginationRequestParameter } from '@/models/api/RequestModel'


const route = useRoute()
const pathId = Number(route.params.id)
const { closePopup, openPopup, visible: alertPopup } = usePopup()
const { getMembreById, goToListView, updateMembre, viewMembre, message } = useMembres()
const entity = ref<Membre | null>(null)
const freezeStore = useFreezeScreenStore()

const { activitesMembres, getAllActivitesMembresByMembreId } = useActivitesMembres();
const activitesMembreLoaded = ref(false);
const loadActivitesMembres = async () => {
  try {
    await getAllActivitesMembresByMembreId(pathId, true, new PaginationRequestParameter(), [])
    activitesMembreLoaded.value = true
  } catch (error) {
    console.error('Error loading membre with details:', error)
    activitesMembreLoaded.value = true // Continuer même en cas d'erreur
  }
}
const { rendezVous, getAllRendezVousByMembreId } = useRendezVous();
const rendezVouLoaded = ref(false);
const loadRendezVous = async () => {
  try {
    await getAllRendezVousByMembreId(pathId, true, new PaginationRequestParameter(), [])
    rendezVouLoaded.value = true
  } catch (error) {
    console.error('Error loading membre with details:', error)
    rendezVouLoaded.value = true // Continuer même en cas d'erreur
  }
}
const { sujetsMembres, getAllSujetsMembresByMembreId } = useSujetsMembres();
const sujetsMembreLoaded = ref(false);
const loadSujetsMembres = async () => {
  try {
    await getAllSujetsMembresByMembreId(pathId, true, new PaginationRequestParameter(), [])
    sujetsMembreLoaded.value = true
  } catch (error) {
    console.error('Error loading membre with details:', error)
    sujetsMembreLoaded.value = true // Continuer même en cas d'erreur
  }
}

const violations = ref<
  | {
    membre: Record<string, string>
    activitesMembres: Record<string, string>[]
    rendezVous: Record<string, string>[]
    sujetsMembres: Record<string, string>[]
    
    }
  | undefined
>(undefined)

const updateHandler = async (payload: {
  membre: Partial<MembreFormDTO>
  activitesMembres: ActivitesMembreFormDTO[]
  rendezVous: RendezVouFormDTO[]
  sujetsMembres: SujetsMembreFormDTO[]
  
}) => {
  freezeStore.freeze('Updating membre ' + pathId + ' ...')
  message.value = null
  violations.value = {
    membre: {},
    activitesMembres: [],
    rendezVous: [],
    sujetsMembres: [],
    
  }
  try {
    const { data, errors } = await updateMembre(pathId, payload)

    if (errors && Object.keys(errors).length > 0) {
      // Gérer les erreurs de validation
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
      console.error(error)
  } finally {
    freezeStore.unfreeze()
  }
}        


const cancelHandler = () => {
  if (entity.value){
    viewMembre(entity.value)
  }
}

onMounted(async () => {
  const result = await getMembreById(pathId)
  if (result.data){
      entity.value = result.data
      await loadActivitesMembres()
      await loadRendezVous()
      await loadSujetsMembres()
      
  }
  else {
    openPopup()
  }
})

</script>
<style scoped></style>
