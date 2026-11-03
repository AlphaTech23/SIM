<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Offre' }) }}
        <span class="text-base-content/50 font-normal">{{ $t('entity.details.nav') }}</span>
      </h3>

      <!-- Back button -->
      <GenesisButton
        :title="$t('button.backToListDescription')"
        @click="goToListView"
        class="btn-secondary"
      >
        <LeftArrowIcon />
        {{ $t('button.backToList') }}
      </GenesisButton>
    </div>
    <!-- Entity details -->
    <OffreDetails v-if="entity" :offre="entity" />
    
    <div class="tabs tabs-border">
      <input type="radio" name="details_tab" class="tab" :aria-label="$t('entities.ciblesOffre.nav.title')" checked />
      <div class="tab-content card bg-base-100 border border-base-300">
        <div class="card-body">
          <div class="mt-4">
            <EntityTable
              :entity-model="ciblesOffreEntity"
              :entity-search-fields="ciblesOffreSearchFields"
              :default-active-filters="['idoffreOffres']"
              :searchFn="getCiblesOffresData"
              :getPaginationData="ciblesOffreGetPaginationData"
              :listComponent="CiblesOffreList"
              :entities="ciblesOffres"
              :message="ciblesOffreMessage"
              :show-filters="false"
              :edit-action="false"
              :remove-action="false"
            />
          </div>
        </div>
      </div>            
    </div>
    <div class="tabs tabs-border">
      <input type="radio" name="details_tab" class="tab" :aria-label="$t('entities.histoOffre.nav.title')" checked />
      <div class="tab-content card bg-base-100 border border-base-300">
        <div class="card-body">
          <div class="mt-4">
            <EntityTable
              :entity-model="histoOffreEntity"
              :entity-search-fields="histoOffreSearchFields"
              :default-active-filters="['idoffreOffres']"
              :searchFn="getHistoOffresData"
              :getPaginationData="histoOffreGetPaginationData"
              :listComponent="HistoOffreList"
              :entities="histoOffres"
              :message="histoOffreMessage"
              :show-filters="false"
              :edit-action="false"
              :remove-action="false"
            />
          </div>
        </div>
      </div>            
    </div>
    
    
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import OffreDetails from '@/entities/offre/OffreDetails.vue'
import { useOffres } from '@/composables/useOffres'
import { Offre } from '@/models/OffreModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import EntityTable from '@/components/table/EntityTable.vue'
import type { EntitySearchField } from '@/models/EntityModel'
import type { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { useCiblesOffres } from '@/composables/useCiblesOffres'
import { CiblesOffre } from '@/models/CiblesOffreModel'
import CiblesOffreList from '@/entities/ciblesOffre/CiblesOffreList.vue'
import { useHistoOffres } from '@/composables/useHistoOffres'
import { HistoOffre } from '@/models/HistoOffreModel'
import HistoOffreList from '@/entities/histoOffre/HistoOffreList.vue'


const route = useRoute()
const pathId = Number(route.params.id)
const { getOffreById, goToListView } = useOffres()
const entity = ref<Offre | null>(null)

const {
  ciblesOffres,
  getAllCiblesOffresByOffreId,
  message: ciblesOffreMessage,
  getPaginationData: ciblesOffreGetPaginationData,
} = useCiblesOffres()
const getCiblesOffresData = async (
  unpagined: boolean,
  filters: Record<string, unknown>,
  pagination: PaginationRequestParameter,
  sortFields: SortFieldParameter[],
) => {
  return await getAllCiblesOffresByOffreId(pathId, unpagined, pagination, sortFields)
}
const ciblesOffreSearchFields = ref<EntitySearchField[]>(CiblesOffre.getAllSearchFieldsMetadata())
const ciblesOffreEntity = ref<Record<string, unknown>>({ idoffreOffres: pathId })
const {
  histoOffres,
  getAllHistoOffresByOffreId,
  message: histoOffreMessage,
  getPaginationData: histoOffreGetPaginationData,
} = useHistoOffres()
const getHistoOffresData = async (
  unpagined: boolean,
  filters: Record<string, unknown>,
  pagination: PaginationRequestParameter,
  sortFields: SortFieldParameter[],
) => {
  return await getAllHistoOffresByOffreId(pathId, unpagined, pagination, sortFields)
}
const histoOffreSearchFields = ref<EntitySearchField[]>(HistoOffre.getAllSearchFieldsMetadata())
const histoOffreEntity = ref<Record<string, unknown>>({ idoffreOffres: pathId })

onMounted(async () => {
  const result = await getOffreById(pathId)
  entity.value = result.data || null
});

</script>
<style scoped></style>
