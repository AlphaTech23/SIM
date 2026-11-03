<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <form @submit.prevent="handleSubmit" class="grid grid-cols-1 w-7/12 gap-4 mx-auto">
      <!-- <form @submit.prevent="handleSubmit" class=""> -->
        <div class="grid grid-cols-1 gap-4">
          <!-- Idoffre offres FK -->
          <GenesisSelectSearchCriteria
            v-if="idoffreOffresSearchField?.multicriteriaSelect"
            label="Idoffre offres"
            key="histoOffreIdoffreOffres"
            :search-function="idoffreOffresSearchField?.multicriteriaSelect.searchFunction"
            :filters="idoffreOffresSearchField?.multicriteriaSelect.filters"
            :default-value="idoffreOffresDefaultValue"
            :violation="violations ? violations['idoffreOffres'] : undefined"
            @option-selected="(selectedValue) => (formModel.idoffreOffres = String(selectedValue))"
          />
          
          <!-- Idstatutoffre statut offres FK -->
          <GenesisSelectSearchCriteria
            v-if="idstatutoffreStatutOffresSearchField?.multicriteriaSelect"
            label="Idstatutoffre statut offres"
            key="histoOffreIdstatutoffreStatutOffres"
            :search-function="idstatutoffreStatutOffresSearchField?.multicriteriaSelect.searchFunction"
            :filters="idstatutoffreStatutOffresSearchField?.multicriteriaSelect.filters"
            :default-value="idstatutoffreStatutOffresDefaultValue"
            :violation="violations ? violations['idstatutoffreStatutOffres'] : undefined"
            @option-selected="(selectedValue) => (formModel.idstatutoffreStatutOffres = String(selectedValue))"
          />
          
          <!-- Date -->
          <GenesisInput
            label="Date"
            :violation="violations ? violations['date'] : undefined"
            placeholder="Enter Date"
            type="datetime-local"
            v-model="formModel.date"
            :value="formModel.date"
          />
          
          <!-- Remarque -->
          <GenesisInput
            label="Remarque"
            :violation="violations ? violations['remarque'] : undefined"
            placeholder="Enter Remarque"
            type="text"
            v-model="formModel.remarque"
            :value="formModel.remarque"
          />
          
        </div>
        
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
import { ref } from 'vue'
import { HistoOffre, HistoOffreFormDTO } from '@/models/HistoOffreModel.ts'
import GenesisButton from '@/components/button/GenesisButton.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'


const props = defineProps<{
  histoOffre?: HistoOffre
  violations?: Record<string, string> | null
  submitLabel?: string
}>()

const emit = defineEmits<{
  (e: 'submit', payload: Partial<HistoOffreFormDTO>): void
  (e: 'cancel', payload: Partial<HistoOffreFormDTO>): void
}>()


const formModel = ref<Partial<HistoOffreFormDTO>>({ ...HistoOffreFormDTO.parse(props.histoOffre) })

const idoffreOffresSearchField = HistoOffre.getSearchFieldByKey('idoffreOffres')
const idoffreOffresDefaultValue = props.histoOffre
? (props.histoOffre?.idoffreOffres?.getKeyValue?.() ?? undefined)
: undefined
const idstatutoffreStatutOffresSearchField = HistoOffre.getSearchFieldByKey('idstatutoffreStatutOffres')
const idstatutoffreStatutOffresDefaultValue = props.histoOffre
? (props.histoOffre?.idstatutoffreStatutOffres?.getKeyValue?.() ?? undefined)
: undefined


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
  
  emit('submit', data)
}

function cancelForm() {
  emit('cancel', formModel.value)
}

</script>
<style scoped></style>
