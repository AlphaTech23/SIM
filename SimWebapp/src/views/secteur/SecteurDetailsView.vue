<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Secteur' }) }}
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
    <SecteurDetails
      v-if="entity"
      :secteur="entity"
    />

    
    <!-- Child tables tabs -->
    <div class="tabs tabs-border mt-6 w-full">
      <input
        type="radio"
        name="secteur_details_tab"
        class="tab"
        :aria-label="$t('entities.membre.nav.title')"
        checked
      />

      <div class="tab-content border-base-300 bg-base-100 p-4">
        <EntityTable
          :entity-model="membreEntity"
          :entity-search-fields="membreSearchFields"
          :default-active-filters="['idsecteurSecteurs']"
          :searchFn="getMembresData"
          :getPaginationData="membreGetPaginationData"
          :listComponent="MembreList"
          :entities="membres"
          :message="membreMessage"
          :visible-fields="membreVisibleListFields"
          :show-filters="false"
          :edit-action="false"
          :remove-action="false"
          @update:visible-fields="handleUpdateMembreVisibleFields"
        />
      </div>
      
    </div>
    
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import SecteurDetails from '@/entities/secteur/SecteurDetails.vue'
import { useSecteurs } from '@/composables/useSecteurs'
import { Secteur } from '@/models/SecteurModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import EntityTable from '@/components/table/EntityTable.vue'
import type { EntitySearchField } from '@/models/EntityModel'
import type { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { useMembres } from '@/composables/useMembres'
import { Membre } from '@/models/MembreModel'
import MembreList from '@/entities/membre/MembreList.vue'


const route = useRoute()
const pathId = Number(route.params.id)

const { getSecteurById, goToListView } = useSecteurs()
const entity = ref<Secteur | null>(null)


const {
  membres,
  getAllMembresBySecteurId,
  message: membreMessage,
  getPaginationData: membreGetPaginationData,
  visibleListFields: membreVisibleListFields,
  loadColumnConfig: loadMembreColumnConfig,
  saveColumnConfig: saveMembreColumnConfig,
} = useMembres()

const getMembresData = async (
  unpagined: boolean,
  filters: Record<string, unknown>,
  pagination: PaginationRequestParameter,
  sortFields: SortFieldParameter[],
) => {
  return await getAllMembresBySecteurId(
    pathId,
    unpagined,
    pagination,
    sortFields
  )
}

const membreSearchFields = ref<EntitySearchField[]>(
  Membre.getAllSearchFieldsMetadata()
)

const membreEntity = ref<Record<string, unknown>>({
  idsecteurSecteurs: pathId
})

const handleUpdateMembreVisibleFields = async (
  newFields: string[]
) => {
  const result = await saveMembreColumnConfig(newFields)

  if (!result.success) {
    console.error(
      'Échec de la sauvegarde de la configuration des colonnes Membre:',
      result.error
    )
  }
}



onMounted(async () => {
  const result = await getSecteurById(pathId)
  entity.value = result.data || null

  
  await Promise.all([
    loadMembreColumnConfig(),
    
  ])
  
})

</script>
<style scoped></style>
