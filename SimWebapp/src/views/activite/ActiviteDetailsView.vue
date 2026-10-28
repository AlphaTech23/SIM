<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Activite' }) }}
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
    <ActiviteDetails
      v-if="entity"
      :activite="entity"
    />

    
    <!-- Child tables tabs -->
    <div class="tabs tabs-border mt-6 w-full">
      <input
        type="radio"
        name="activite_details_tab"
        class="tab"
        :aria-label="$t('entities.activitesMembre.nav.title')"
        checked
      />

      <div class="tab-content border-base-300 bg-base-100 p-4">
        <EntityTable
          :entity-model="activitesMembreEntity"
          :entity-search-fields="activitesMembreSearchFields"
          :default-active-filters="['idactiviteActivites']"
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
      
    </div>
    
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import ActiviteDetails from '@/entities/activite/ActiviteDetails.vue'
import { useActivites } from '@/composables/useActivites'
import { Activite } from '@/models/ActiviteModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import EntityTable from '@/components/table/EntityTable.vue'
import type { EntitySearchField } from '@/models/EntityModel'
import type { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { useActivitesMembres } from '@/composables/useActivitesMembres'
import { ActivitesMembre } from '@/models/ActivitesMembreModel'
import ActivitesMembreList from '@/entities/activitesMembre/ActivitesMembreList.vue'


const route = useRoute()
const pathId = Number(route.params.id)

const { getActiviteById, goToListView } = useActivites()
const entity = ref<Activite | null>(null)


const {
  activitesMembres,
  getAllActivitesMembresByActiviteId,
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
  return await getAllActivitesMembresByActiviteId(
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
  idactiviteActivites: pathId
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



onMounted(async () => {
  const result = await getActiviteById(pathId)
  entity.value = result.data || null

  
  await Promise.all([
    loadActivitesMembreColumnConfig(),
    
  ])
  
})

</script>
<style scoped></style>
