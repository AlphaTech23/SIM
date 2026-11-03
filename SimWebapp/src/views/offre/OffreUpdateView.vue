<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        Offre /
        <span class="text-base-content/50 font-normal">{{ $t('entity.update.nav') }}</span>
      </h3>
      <GenesisButton :title="$t('button.backToListDescription')" @click="goToListView" class="btn-secondary">
        <LeftArrowIcon />
        {{ $t('button.backToList') }}
      </GenesisButton>
    </div>

    <!-- Form -->
    <div v-if="entity&& ciblesOffreLoaded&& histoOffreLoaded">
      <offre-form
        :offre="entity"
        :ciblesOffresData="ciblesOffres"
        :histoOffresData="histoOffres"
        
        :violations="violations"
        :submit-label="$t('entity.update.submitLabel', { entity: 'Offre' })"
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
import OffreForm from '@/entities/offre/OffreForm.vue'
import AlertPopup from '@/components/popup/AlertPopup.vue'
import { useOffres } from '@/composables/useOffres'
import { Offre, OffreFormDTO } from '@/models/OffreModel'
import { usePopup } from '@/composables/usePopup'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import { useFreezeScreenStore } from '@/stores/useFreezeScreenStore.ts'
import { useCiblesOffres } from '@/composables/useCiblesOffres'
import { CiblesOffreFormDTO } from '@/models/CiblesOffreModel'
import { useHistoOffres } from '@/composables/useHistoOffres'
import { HistoOffreFormDTO } from '@/models/HistoOffreModel'

import { PaginationRequestParameter } from '@/models/api/RequestModel'


const route = useRoute()
const pathId = Number(route.params.id)
const { closePopup, openPopup, visible: alertPopup } = usePopup()
const { getOffreById, goToListView, updateOffre, viewOffre, message } = useOffres()
const entity = ref<Offre | null>(null)
const freezeStore = useFreezeScreenStore()

const { ciblesOffres, getAllCiblesOffresByOffreId } = useCiblesOffres();
const ciblesOffreLoaded = ref(false);
const loadCiblesOffres = async () => {
  try {
    await getAllCiblesOffresByOffreId(pathId, true, new PaginationRequestParameter(), [])
    ciblesOffreLoaded.value = true
  } catch (error) {
    console.error('Error loading offre with details:', error)
    ciblesOffreLoaded.value = true // Continuer même en cas d'erreur
  }
}
const { histoOffres, getAllHistoOffresByOffreId } = useHistoOffres();
const histoOffreLoaded = ref(false);
const loadHistoOffres = async () => {
  try {
    await getAllHistoOffresByOffreId(pathId, true, new PaginationRequestParameter(), [])
    histoOffreLoaded.value = true
  } catch (error) {
    console.error('Error loading offre with details:', error)
    histoOffreLoaded.value = true // Continuer même en cas d'erreur
  }
}

const violations = ref<
  | {
    offre: Record<string, string>
    ciblesOffres: Record<string, string>[]
    histoOffres: Record<string, string>[]
    
    }
  | undefined
>(undefined)

const updateHandler = async (payload: {
  offre: Partial<OffreFormDTO>
  ciblesOffres: CiblesOffreFormDTO[]
  histoOffres: HistoOffreFormDTO[]
  
}) => {
  freezeStore.freeze('Updating offre ' + pathId + ' ...')
  message.value = null
  violations.value = {
    offre: {},
    ciblesOffres: [],
    histoOffres: [],
    
  }
  try {
    const { data, errors } = await updateOffre(pathId, payload)

    if (errors && Object.keys(errors).length > 0) {
      // Gérer les erreurs de validation
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
      console.error(error)
  } finally {
    freezeStore.unfreeze()
  }
}        


const cancelHandler = () => {
  if (entity.value){
    viewOffre(entity.value)
  }
}

onMounted(async () => {
  const result = await getOffreById(pathId)
  if (result.data){
      entity.value = result.data
      await loadCiblesOffres()
      await loadHistoOffres()
      
  }
  else {
    openPopup()
  }
})

</script>
<style scoped></style>
