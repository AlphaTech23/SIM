<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Statut' }) }}
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
    <StatutDetails
      v-if="entity"
      :statut="entity"
    />

    
    <!-- Child tables tabs -->
    <div class="tabs tabs-border mt-6 w-full">
      <input
        type="radio"
        name="statut_details_tab"
        class="tab"
        :aria-label="$t('entities.statutRendezVou.nav.title')"
        checked
      />

      <div class="tab-content border-base-300 bg-base-100 p-4">
        <EntityTable
          :entity-model="statutRendezVouEntity"
          :entity-search-fields="statutRendezVouSearchFields"
          :default-active-filters="['idstatutStatut']"
          :searchFn="getStatutRendezVousData"
          :getPaginationData="statutRendezVouGetPaginationData"
          :listComponent="StatutRendezVouList"
          :entities="statutRendezVous"
          :message="statutRendezVouMessage"
          :visible-fields="statutRendezVouVisibleListFields"
          :show-filters="false"
          :edit-action="false"
          :remove-action="false"
          @update:visible-fields="handleUpdateStatutRendezVouVisibleFields"
        />
      </div>
      
    </div>
    
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import StatutDetails from '@/entities/statut/StatutDetails.vue'
import { useStatuts } from '@/composables/useStatuts'
import { Statut } from '@/models/StatutModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import EntityTable from '@/components/table/EntityTable.vue'
import type { EntitySearchField } from '@/models/EntityModel'
import type { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { useStatutRendezVous } from '@/composables/useStatutRendezVous'
import { StatutRendezVou } from '@/models/StatutRendezVouModel'
import StatutRendezVouList from '@/entities/statutRendezVou/StatutRendezVouList.vue'


const route = useRoute()
const pathId = Number(route.params.id)

const { getStatutById, goToListView } = useStatuts()
const entity = ref<Statut | null>(null)


const {
  statutRendezVous,
  getAllStatutRendezVousByStatutId,
  message: statutRendezVouMessage,
  getPaginationData: statutRendezVouGetPaginationData,
  visibleListFields: statutRendezVouVisibleListFields,
  loadColumnConfig: loadStatutRendezVouColumnConfig,
  saveColumnConfig: saveStatutRendezVouColumnConfig,
} = useStatutRendezVous()

const getStatutRendezVousData = async (
  unpagined: boolean,
  filters: Record<string, unknown>,
  pagination: PaginationRequestParameter,
  sortFields: SortFieldParameter[],
) => {
  return await getAllStatutRendezVousByStatutId(
    pathId,
    unpagined,
    pagination,
    sortFields
  )
}

const statutRendezVouSearchFields = ref<EntitySearchField[]>(
  StatutRendezVou.getAllSearchFieldsMetadata()
)

const statutRendezVouEntity = ref<Record<string, unknown>>({
  idstatutStatut: pathId
})

const handleUpdateStatutRendezVouVisibleFields = async (
  newFields: string[]
) => {
  const result = await saveStatutRendezVouColumnConfig(newFields)

  if (!result.success) {
    console.error(
      'Échec de la sauvegarde de la configuration des colonnes StatutRendezVou:',
      result.error
    )
  }
}



onMounted(async () => {
  const result = await getStatutById(pathId)
  entity.value = result.data || null

  
  await Promise.all([
    loadStatutRendezVouColumnConfig(),
    
  ])
  
})

</script>
<style scoped></style>
