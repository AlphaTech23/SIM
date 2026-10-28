<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Sujet' }) }}
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
    <SujetDetails
      v-if="entity"
      :sujet="entity"
    />

    
    <!-- Child tables tabs -->
    <div class="tabs tabs-border mt-6 w-full">
      <input
        type="radio"
        name="sujet_details_tab"
        class="tab"
        :aria-label="$t('entities.sujetsMembre.nav.title')"
        checked
      />

      <div class="tab-content border-base-300 bg-base-100 p-4">
        <EntityTable
          :entity-model="sujetsMembreEntity"
          :entity-search-fields="sujetsMembreSearchFields"
          :default-active-filters="['idsujetSujets']"
          :searchFn="getSujetsMembresData"
          :getPaginationData="sujetsMembreGetPaginationData"
          :listComponent="SujetsMembreList"
          :entities="sujetsMembres"
          :message="sujetsMembreMessage"
          :visible-fields="sujetsMembreVisibleListFields"
          :show-filters="false"
          :edit-action="false"
          :remove-action="false"
          @update:visible-fields="handleUpdateSujetsMembreVisibleFields"
        />
      </div>
      
    </div>
    
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import SujetDetails from '@/entities/sujet/SujetDetails.vue'
import { useSujets } from '@/composables/useSujets'
import { Sujet } from '@/models/SujetModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import EntityTable from '@/components/table/EntityTable.vue'
import type { EntitySearchField } from '@/models/EntityModel'
import type { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { useSujetsMembres } from '@/composables/useSujetsMembres'
import { SujetsMembre } from '@/models/SujetsMembreModel'
import SujetsMembreList from '@/entities/sujetsMembre/SujetsMembreList.vue'


const route = useRoute()
const pathId = Number(route.params.id)

const { getSujetById, goToListView } = useSujets()
const entity = ref<Sujet | null>(null)


const {
  sujetsMembres,
  getAllSujetsMembresBySujetId,
  message: sujetsMembreMessage,
  getPaginationData: sujetsMembreGetPaginationData,
  visibleListFields: sujetsMembreVisibleListFields,
  loadColumnConfig: loadSujetsMembreColumnConfig,
  saveColumnConfig: saveSujetsMembreColumnConfig,
} = useSujetsMembres()

const getSujetsMembresData = async (
  unpagined: boolean,
  filters: Record<string, unknown>,
  pagination: PaginationRequestParameter,
  sortFields: SortFieldParameter[],
) => {
  return await getAllSujetsMembresBySujetId(
    pathId,
    unpagined,
    pagination,
    sortFields
  )
}

const sujetsMembreSearchFields = ref<EntitySearchField[]>(
  SujetsMembre.getAllSearchFieldsMetadata()
)

const sujetsMembreEntity = ref<Record<string, unknown>>({
  idsujetSujets: pathId
})

const handleUpdateSujetsMembreVisibleFields = async (
  newFields: string[]
) => {
  const result = await saveSujetsMembreColumnConfig(newFields)

  if (!result.success) {
    console.error(
      'Échec de la sauvegarde de la configuration des colonnes SujetsMembre:',
      result.error
    )
  }
}



onMounted(async () => {
  const result = await getSujetById(pathId)
  entity.value = result.data || null

  
  await Promise.all([
    loadSujetsMembreColumnConfig(),
    
  ])
  
})

</script>
<style scoped></style>
