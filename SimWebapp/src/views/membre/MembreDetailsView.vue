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
    <MembreDetails v-if="entity" :membre="entity" />
    
    <div class="tabs tabs-border">
      <input type="radio" name="details_tab" class="tab" :aria-label="$t('entities.activitesMembre.nav.title')" checked />
      <div class="tab-content card bg-base-100 border border-base-300">
        <div class="card-body">
          <div class="mt-4">
            <EntityTable
              :entity-model="activitesMembreEntity"
              :entity-search-fields="activitesMembreSearchFields"
              :default-active-filters="['idmembreMembres']"
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
    <div class="tabs tabs-border">
      <input type="radio" name="details_tab" class="tab" :aria-label="$t('entities.ciblesOffre.nav.title')" checked />
      <div class="tab-content card bg-base-100 border border-base-300">
        <div class="card-body">
          <div class="mt-4">
            <EntityTable
              :entity-model="ciblesOffreEntity"
              :entity-search-fields="ciblesOffreSearchFields"
              :default-active-filters="['idmembreMembres']"
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
      <input type="radio" name="details_tab" class="tab" :aria-label="$t('entities.offre.nav.title')" checked />
      <div class="tab-content card bg-base-100 border border-base-300">
        <div class="card-body">
          <div class="mt-4">
            <EntityTable
              :entity-model="offreEntity"
              :entity-search-fields="offreSearchFields"
              :default-active-filters="['idmembreMembres']"
              :searchFn="getOffresData"
              :getPaginationData="offreGetPaginationData"
              :listComponent="OffreList"
              :entities="offres"
              :message="offreMessage"
              :show-filters="false"
              :edit-action="false"
              :remove-action="false"
            />
          </div>
        </div>
      </div>            
    </div>
    <div class="tabs tabs-border">
      <input type="radio" name="details_tab" class="tab" :aria-label="$t('entities.rendezVou.nav.title')" checked />
      <div class="tab-content card bg-base-100 border border-base-300">
        <div class="card-body">
          <div class="mt-4">
            <EntityTable
              :entity-model="rendezVouEntity"
              :entity-search-fields="rendezVouSearchFields"
              :default-active-filters="['iddemandeurMembres']"
              :searchFn="getRendezVousData"
              :getPaginationData="rendezVouGetPaginationData"
              :listComponent="RendezVouList"
              :entities="rendezVous"
              :message="rendezVouMessage"
              :show-filters="false"
              :edit-action="false"
              :remove-action="false"
            />
          </div>
        </div>
      </div>            
    </div>
    <div class="tabs tabs-border">
      <input type="radio" name="details_tab" class="tab" :aria-label="$t('entities.sujetsMembre.nav.title')" checked />
      <div class="tab-content card bg-base-100 border border-base-300">
        <div class="card-body">
          <div class="mt-4">
            <EntityTable
              :entity-model="sujetsMembreEntity"
              :entity-search-fields="sujetsMembreSearchFields"
              :default-active-filters="['idmembreMembres']"
              :searchFn="getSujetsMembresData"
              :getPaginationData="sujetsMembreGetPaginationData"
              :listComponent="SujetsMembreList"
              :entities="sujetsMembres"
              :message="sujetsMembreMessage"
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
import { useCiblesOffres } from '@/composables/useCiblesOffres'
import { CiblesOffre } from '@/models/CiblesOffreModel'
import CiblesOffreList from '@/entities/ciblesOffre/CiblesOffreList.vue'
import { useOffres } from '@/composables/useOffres'
import { Offre } from '@/models/OffreModel'
import OffreList from '@/entities/offre/OffreList.vue'
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
} = useActivitesMembres()
const getActivitesMembresData = async (
  unpagined: boolean,
  filters: Record<string, unknown>,
  pagination: PaginationRequestParameter,
  sortFields: SortFieldParameter[],
) => {
  return await getAllActivitesMembresByMembreId(pathId, unpagined, pagination, sortFields)
}
const activitesMembreSearchFields = ref<EntitySearchField[]>(ActivitesMembre.getAllSearchFieldsMetadata())
const activitesMembreEntity = ref<Record<string, unknown>>({ idmembreMembres: pathId })
const {
  ciblesOffres,
  getAllCiblesOffresByMembreId,
  message: ciblesOffreMessage,
  getPaginationData: ciblesOffreGetPaginationData,
} = useCiblesOffres()
const getCiblesOffresData = async (
  unpagined: boolean,
  filters: Record<string, unknown>,
  pagination: PaginationRequestParameter,
  sortFields: SortFieldParameter[],
) => {
  return await getAllCiblesOffresByMembreId(pathId, unpagined, pagination, sortFields)
}
const ciblesOffreSearchFields = ref<EntitySearchField[]>(CiblesOffre.getAllSearchFieldsMetadata())
const ciblesOffreEntity = ref<Record<string, unknown>>({ idmembreMembres: pathId })
const {
  offres,
  getAllOffresByMembreId,
  message: offreMessage,
  getPaginationData: offreGetPaginationData,
} = useOffres()
const getOffresData = async (
  unpagined: boolean,
  filters: Record<string, unknown>,
  pagination: PaginationRequestParameter,
  sortFields: SortFieldParameter[],
) => {
  return await getAllOffresByMembreId(pathId, unpagined, pagination, sortFields)
}
const offreSearchFields = ref<EntitySearchField[]>(Offre.getAllSearchFieldsMetadata())
const offreEntity = ref<Record<string, unknown>>({ idmembreMembres: pathId })
const {
  rendezVous,
  getAllRendezVousByMembreId,
  message: rendezVouMessage,
  getPaginationData: rendezVouGetPaginationData,
} = useRendezVous()
const getRendezVousData = async (
  unpagined: boolean,
  filters: Record<string, unknown>,
  pagination: PaginationRequestParameter,
  sortFields: SortFieldParameter[],
) => {
  return await getAllRendezVousByMembreId(pathId, unpagined, pagination, sortFields)
}
const rendezVouSearchFields = ref<EntitySearchField[]>(RendezVou.getAllSearchFieldsMetadata())
const rendezVouEntity = ref<Record<string, unknown>>({ iddemandeurMembres: pathId })
const {
  sujetsMembres,
  getAllSujetsMembresByMembreId,
  message: sujetsMembreMessage,
  getPaginationData: sujetsMembreGetPaginationData,
} = useSujetsMembres()
const getSujetsMembresData = async (
  unpagined: boolean,
  filters: Record<string, unknown>,
  pagination: PaginationRequestParameter,
  sortFields: SortFieldParameter[],
) => {
  return await getAllSujetsMembresByMembreId(pathId, unpagined, pagination, sortFields)
}
const sujetsMembreSearchFields = ref<EntitySearchField[]>(SujetsMembre.getAllSearchFieldsMetadata())
const sujetsMembreEntity = ref<Record<string, unknown>>({ idmembreMembres: pathId })

onMounted(async () => {
  const result = await getMembreById(pathId)
  entity.value = result.data || null
});

</script>
<style scoped></style>
