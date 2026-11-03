<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <form @submit.prevent="handleSubmit" class="grid grid-cols-1 w-7/12 gap-4 mx-auto">
      <!-- <form @submit.prevent="handleSubmit" class=""> -->
        <div class="grid grid-cols-1 gap-4">
          <!-- Idtypeoffre type offres FK -->
          <GenesisSelectSearchCriteria
            v-if="idtypeoffreTypeOffresSearchField?.multicriteriaSelect"
            label="Idtypeoffre type offres"
            key="offreIdtypeoffreTypeOffres"
            :search-function="idtypeoffreTypeOffresSearchField?.multicriteriaSelect.searchFunction"
            :filters="idtypeoffreTypeOffresSearchField?.multicriteriaSelect.filters"
            :default-value="idtypeoffreTypeOffresDefaultValue"
            :violation="offreViolations ? offreViolations['idtypeoffreTypeOffres'] : undefined"
            @option-selected="(selectedValue) => (formModel.idtypeoffreTypeOffres = String(selectedValue))"
          />
          
          <!-- Idmembre membres FK -->
          <GenesisSelectSearchCriteria
            v-if="idmembreMembresSearchField?.multicriteriaSelect"
            label="Idmembre membres"
            key="offreIdmembreMembres"
            :search-function="idmembreMembresSearchField?.multicriteriaSelect.searchFunction"
            :filters="idmembreMembresSearchField?.multicriteriaSelect.filters"
            :default-value="idmembreMembresDefaultValue"
            :violation="offreViolations ? offreViolations['idmembreMembres'] : undefined"
            @option-selected="(selectedValue) => (formModel.idmembreMembres = String(selectedValue))"
          />
          
          <!-- Titre offre -->
          <GenesisInput
            label="Titre offre"
            :violation="offreViolations ? offreViolations['titreOffre'] : undefined"
            placeholder="Enter Titre offre"
            type="text"
            v-model="formModel.titreOffre"
            :value="formModel.titreOffre"
          />
          
          <!-- Descriptions -->
          <GenesisInput
            label="Descriptions"
            :violation="offreViolations ? offreViolations['descriptions'] : undefined"
            placeholder="Enter Descriptions"
            type="text"
            v-model="formModel.descriptions"
            :value="formModel.descriptions"
          />
          
          <!-- Conditions -->
          <GenesisInput
            label="Conditions"
            :violation="offreViolations ? offreViolations['conditions'] : undefined"
            placeholder="Enter Conditions"
            type="text"
            v-model="formModel.conditions"
            :value="formModel.conditions"
          />
          
          <!-- Date creation -->
          <GenesisInput
            label="Date creation"
            :violation="offreViolations ? offreViolations['dateCreation'] : undefined"
            placeholder="Enter Date creation"
            type="datetime-local"
            v-model="formModel.dateCreation"
            :value="formModel.dateCreation"
          />
          
          <!-- Date fin validite -->
          <GenesisInput
            label="Date fin validite"
            :violation="offreViolations ? offreViolations['dateFinValidite'] : undefined"
            placeholder="Enter Date fin validite"
            type="date"
            v-model="formModel.dateFinValidite"
            :value="formModel.dateFinValidite"
          />
          
        </div>
        <CiblesOffreTableForm
          :initial-data="ciblesOffresData"
          :get-table-data-callback="setCiblesOffres"
          :violations="ciblesOffreViolations"
          class="w-full"
        />
        <HistoOffreTableForm
          :initial-data="histoOffresData"
          :get-table-data-callback="setHistoOffres"
          :violations="histoOffreViolations"
          class="w-full"
        />
        
        <!-- Action buttons -->
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton type="submit" class="btn btn-primary text-primary-content" :label="submitLabel" />
          <GenesisButton @click="cancelForm" class="btn btn-outline btn-error" :label="$t('button.cancel')" />
        </div>
      </form>
    </div>
  </div>
  
</template>

<script setup lang="ts">
 import { ref, computed, onMounted } from 'vue'
import { Offre, OffreFormDTO } from '@/models/OffreModel.ts'
import GenesisButton from '@/components/button/GenesisButton.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'
import{ CiblesOffreFormDTO, type CiblesOffre } from '@/models/CiblesOffreModel.ts'
import CiblesOffreTableForm from '@/entities/ciblesOffre/CiblesOffreTableForm.vue'
import{ HistoOffreFormDTO, type HistoOffre } from '@/models/HistoOffreModel.ts'
import HistoOffreTableForm from '@/entities/histoOffre/HistoOffreTableForm.vue'



const props = defineProps<{
  offre?: Offre
  
  ciblesOffresData?: CiblesOffre[]
  histoOffresData?: HistoOffre[]
  
  violations?:{
    offre: Record<string, string>
    ciblesOffres: Record<string, string>[]
    histoOffres: Record<string, string>[]
    
  }
  
  submitLabel?: string
}>()

const emit = defineEmits<{
  (e: 'submit', payload: { offre: Partial<OffreFormDTO>; ciblesOffres: CiblesOffreFormDTO[]; histoOffres: HistoOffreFormDTO[] }): void
  (e: 'cancel', payload: Partial<OffreFormDTO>): void
}>()

const offreViolations = computed(() => props.violations ? props.violations.offre : {})
const ciblesOffreViolations = computed(() => props.violations ? props.violations.ciblesOffres : [])
const histoOffreViolations = computed(() => props.violations ? props.violations.histoOffres : [])



const formModel = ref<Partial<OffreFormDTO>>({ ...OffreFormDTO.parse(props.offre) })

const idtypeoffreTypeOffresSearchField = Offre.getSearchFieldByKey('idtypeoffreTypeOffres')
const idtypeoffreTypeOffresDefaultValue = props.offre
? (props.offre?.idtypeoffreTypeOffres?.getKeyValue?.() ?? undefined)
: undefined
const idmembreMembresSearchField = Offre.getSearchFieldByKey('idmembreMembres')
const idmembreMembresDefaultValue = props.offre
? (props.offre?.idmembreMembres?.getKeyValue?.() ?? undefined)
: undefined


const ciblesOffres =ref<CiblesOffreFormDTO[]>([])
const setCiblesOffres = (data: CiblesOffreFormDTO[]) => {
  ciblesOffres.value = data
}
const histoOffres =ref<HistoOffreFormDTO[]>([])
const setHistoOffres = (data: HistoOffreFormDTO[]) => {
  histoOffres.value = data
}

async function handleSubmit() {
  const data: any = { ...formModel.value }
  const fileFieldNames = [
    
  ]
  Object.keys(data).forEach(key => {
    if (data[key] === ''
            || data[key] === undefined
            || (data[key] === null && !fileFieldNames.includes(key))) {
        delete data[key];
    }
  });
  
  emit('submit', { offre: data, ciblesOffres: ciblesOffres.value, histoOffres: histoOffres.value })
  
}

function cancelForm() {
  emit('cancel', formModel.value)
}
onMounted(async () => {
  if (Array.isArray(props.ciblesOffresData) && props.ciblesOffresData.length > 0) {
    setCiblesOffres(CiblesOffreFormDTO.parseList(props.ciblesOffresData))
  }if (Array.isArray(props.histoOffresData) && props.histoOffresData.length > 0) {
    setHistoOffres(HistoOffreFormDTO.parseList(props.histoOffresData))
  }
})

</script>
<style scoped></style>
