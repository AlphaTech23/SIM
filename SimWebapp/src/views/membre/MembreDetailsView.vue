<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Membre' }) }}
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
    <MembreDetails
      v-if="entity"
      :membre="entity"
    />

    
    <!-- Child tables tabs -->
    <div class="tabs tabs-border mt-6 w-full">
      <input
        type="radio"
        name="membre_details_tab"
        class="tab"
        :aria-label="$t('entities.activitesMembre.nav.title')"
        checked
      />

      <div class="tab-content border-base-300 bg-base-100 p-4">
        <EntityTable
          :entity-model="activitesMembreEntity"
          :entity-search-fields="activitesMembreSearchFields"
          :default-active-filters="['idmembreMembres']"
          :searchFn="getActivitesMembresData"
          :getPaginationData="activitesMembreGetPaginationData"
          :listComponent="ActivitesMembreList"
          :entities="activitesMembres"
          :message="activitesMembreMessage"
          :visible-fields="activitesMembreVisibleListFields"
          :show-filters="false"
          :edit-action="false"
          :remove-action="false"
          @update:visible-fields="handleUpdateActivitesMembreVisibleFields"
        />
      </div>
      <input
        type="radio"
        name="membre_details_tab"
        class="tab"
        :aria-label="$t('entities.rendezVou.nav.title')"
        
      />

      <div class="tab-content border-base-300 bg-base-100 p-4">
        <EntityTable
          :entity-model="rendezVouEntity"
          :entity-search-fields="rendezVouSearchFields"
          :default-active-filters="['iddemandeurMembres']"
          :searchFn="getRendezVousData"
          :getPaginationData="rendezVouGetPaginationData"
          :listComponent="RendezVouList"
          :entities="rendezVous"
          :message="rendezVouMessage"
          :visible-fields="rendezVouVisibleListFields"
          :show-filters="false"
          :edit-action="false"
          :remove-action="false"
          @update:visible-fields="handleUpdateRendezVouVisibleFields"
        />
      </div>
      <input
        type="radio"
        name="membre_details_tab"
        class="tab"
        :aria-label="$t('entities.sujetsMembre.nav.title')"
        
      />

      <div class="tab-content border-base-300 bg-base-100 p-4">
        <EntityTable
          :entity-model="sujetsMembreEntity"
          :entity-search-fields="sujetsMembreSearchFields"
          :default-active-filters="['idmembreMembres']"
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
import MembreDetails from '@/entities/membre/MembreDetails.vue'
import { useMembres } from '@/composables/useMembres'
import { Membre } from '@/models/MembreModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import EntityTable from '@/components/table/EntityTable.vue'
import type { EntitySearchField } from '@/models/EntityModel'
import type { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { useActivitesMembres } from '@/composables/useActivitesMembres'
import { ActivitesMembre } from '@/models/ActivitesMembreModel'
import ActivitesMembreList from '@/entities/activitesMembre/ActivitesMembreList.vue'
import { useRendezVous } from '@/composables/useRendezVous'
import { RendezVou } from '@/models/RendezVouModel'
import RendezVouList from '@/entities/rendezVou/RendezVouList.vue'
import { useSujetsMembres } from '@/composables/useSujetsMembres'
import { SujetsMembre } from '@/models/SujetsMembreModel'
import SujetsMembreList from '@/entities/sujetsMembre/SujetsMembreList.vue'


const route = useRoute()
const pathId = Number(route.params.id)

const { getMembreById, goToListView } = useMembres()
const entity = ref<Membre | null>(null)


const {
  activitesMembres,
  getAllActivitesMembresByMembreId,
  message: activitesMembreMessage,
  getPaginationData: activitesMembreGetPaginationData,
  visibleListFields: activitesMembreVisibleListFields,
  loadColumnConfig: loadActivitesMembreColumnConfig,
  saveColumnConfig: saveActivitesMembreColumnConfig,
} = useActivitesMembres()

const getActivitesMembresData = async (
  unpagined: boolean,
  filters: Record<string, unknown>,
  pagination: PaginationRequestParameter,
  sortFields: SortFieldParameter[],
) => {
  return await getAllActivitesMembresByMembreId(
    pathId,
    unpagined,
    pagination,
    sortFields
  )
}

const activitesMembreSearchFields = ref<EntitySearchField[]>(
  ActivitesMembre.getAllSearchFieldsMetadata()
)

const activitesMembreEntity = ref<Record<string, unknown>>({
  idmembreMembres: pathId
})

const handleUpdateActivitesMembreVisibleFields = async (
  newFields: string[]
) => {
  const result = await saveActivitesMembreColumnConfig(newFields)

  if (!result.success) {
    console.error(
      'Échec de la sauvegarde de la configuration des colonnes ActivitesMembre:',
      result.error
    )
  }
}
const {
  rendezVous,
  getAllRendezVousByMembreId,
  message: rendezVouMessage,
  getPaginationData: rendezVouGetPaginationData,
  visibleListFields: rendezVouVisibleListFields,
  loadColumnConfig: loadRendezVouColumnConfig,
  saveColumnConfig: saveRendezVouColumnConfig,
} = useRendezVous()

const getRendezVousData = async (
  unpagined: boolean,
  filters: Record<string, unknown>,
  pagination: PaginationRequestParameter,
  sortFields: SortFieldParameter[],
) => {
  return await getAllRendezVousByMembreId(
    pathId,
    unpagined,
    pagination,
    sortFields
  )
}

const rendezVouSearchFields = ref<EntitySearchField[]>(
  RendezVou.getAllSearchFieldsMetadata()
)

const rendezVouEntity = ref<Record<string, unknown>>({
  iddemandeurMembres: pathId
})

const handleUpdateRendezVouVisibleFields = async (
  newFields: string[]
) => {
  const result = await saveRendezVouColumnConfig(newFields)

  if (!result.success) {
    console.error(
      'Échec de la sauvegarde de la configuration des colonnes RendezVou:',
      result.error
    )
  }
}
const {
  sujetsMembres,
  getAllSujetsMembresByMembreId,
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
  return await getAllSujetsMembresByMembreId(
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
  idmembreMembres: pathId
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
  const result = await getMembreById(pathId)
  entity.value = result.data || null

  
  await Promise.all([
    loadActivitesMembreColumnConfig(),
    loadRendezVouColumnConfig(),
    loadSujetsMembreColumnConfig(),
    
  ])
  
})

</script>
<style scoped></style>
