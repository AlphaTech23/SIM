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
    <ActiviteDetails v-if="entity" :activite="entity" />
    
    <div class="tabs tabs-border">
      <input type="radio" name="details_tab" class="tab" :aria-label="$t('entities.activitesMembre.nav.title')" checked />
      <div class="tab-content card bg-base-100 border border-base-300">
        <div class="card-body">
          <div class="mt-4">
            <EntityTable
              :entity-model="activitesMembreEntity"
              :entity-search-fields="activitesMembreSearchFields"
              :default-active-filters="['idactiviteActivites']"
              :searchFn="getActivitesMembresData"
              :getPaginationData="activitesMembreGetPaginationData"
              :listComponent="ActivitesMembreList"
              :entities="activitesMembres"
              :message="activitesMembreMessage"
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
} = useActivitesMembres()
const getActivitesMembresData = async (
  unpagined: boolean,
  filters: Record<string, unknown>,
  pagination: PaginationRequestParameter,
  sortFields: SortFieldParameter[],
) => {
  return await getAllActivitesMembresByActiviteId(pathId, unpagined, pagination, sortFields)
}
const activitesMembreSearchFields = ref<EntitySearchField[]>(ActivitesMembre.getAllSearchFieldsMetadata())
const activitesMembreEntity = ref<Record<string, unknown>>({ idactiviteActivites: pathId })

onMounted(async () => {
  const result = await getActiviteById(pathId)
  entity.value = result.data || null
});

</script>
<style scoped></style>
