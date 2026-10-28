<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        Sujet /
        <span class="text-base-content/50 font-normal">{{ $t('entity.update.nav') }}</span>
      </h3>
      <GenesisButton :title="$t('button.backToListDescription')" @click="goToListView" class="btn-secondary">
        <LeftArrowIcon />
        {{ $t('button.backToList') }}
      </GenesisButton>
    </div>

    <!-- Form -->
    <div v-if="entity&& sujetsMembreLoaded">
      <sujet-form
        :sujet="entity"
        :sujetsMembresData="sujetsMembres"
        
        :violations="violations"
        :submit-label="$t('entity.update.submitLabel', { entity: 'Sujet' })"
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
import SujetForm from '@/entities/sujet/SujetForm.vue'
import AlertPopup from '@/components/popup/AlertPopup.vue'
import { useSujets } from '@/composables/useSujets'
import { Sujet, SujetFormDTO } from '@/models/SujetModel'
import { usePopup } from '@/composables/usePopup'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import { useFreezeScreenStore } from '@/stores/useFreezeScreenStore.ts'
import { useSujetsMembres } from '@/composables/useSujetsMembres'
import { SujetsMembreFormDTO } from '@/models/SujetsMembreModel'

import { PaginationRequestParameter } from '@/models/api/RequestModel'


const route = useRoute()
const pathId = Number(route.params.id)
const { closePopup, openPopup, visible: alertPopup } = usePopup()
const { getSujetById, goToListView, updateSujet, viewSujet, message } = useSujets()
const entity = ref<Sujet | null>(null)
const freezeStore = useFreezeScreenStore()

const { sujetsMembres, getAllSujetsMembresBySujetId } = useSujetsMembres();
const sujetsMembreLoaded = ref(false);
const loadSujetsMembres = async () => {
  try {
    await getAllSujetsMembresBySujetId(pathId, true, new PaginationRequestParameter(), [])
    sujetsMembreLoaded.value = true
  } catch (error) {
    console.error('Error loading sujet with details:', error)
    sujetsMembreLoaded.value = true // Continuer même en cas d'erreur
  }
}

const violations = ref<
  | {
    sujet: Record<string, string>
    sujetsMembres: Record<string, string>[]
    
    }
  | undefined
>(undefined)

const updateHandler = async (payload: {
  sujet: Partial<SujetFormDTO>
  sujetsMembres: SujetsMembreFormDTO[]
  
}) => {
  freezeStore.freeze('Updating sujet ' + pathId + ' ...')
  message.value = null
  violations.value = {
    sujet: {},
    sujetsMembres: [],
    
  }
  try {
    const { data, errors } = await updateSujet(pathId, payload)

    if (errors && Object.keys(errors).length > 0) {
      // Gérer les erreurs de validation
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
      console.error(error)
  } finally {
    freezeStore.unfreeze()
  }
}        


const cancelHandler = () => {
  if (entity.value){
    viewSujet(entity.value)
  }
}

onMounted(async () => {
  const result = await getSujetById(pathId)
  if (result.data){
      entity.value = result.data
      await loadSujetsMembres()
      
  }
  else {
    openPopup()
  }
})

</script>
<style scoped></style>
