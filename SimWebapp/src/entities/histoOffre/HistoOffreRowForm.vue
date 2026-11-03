<script setup lang="ts">
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import { ref, onMounted } from 'vue'
import { HistoOffre, HistoOffreFormDTO } from '@/models/HistoOffreModel'
import TrashIcon from '@/components/icons/TrashIcon.vue'


const props = defineProps<{
  internalId: number
  histoOffre: HistoOffre
  violations?: Record<string, string> | null
}>()

const emit = defineEmits<{
  (e: 'request:remove'): void
  (e: 'udpate:model-value', value: HistoOffreFormDTO): void
}>()

const formModel = ref<HistoOffreFormDTO>(HistoOffreFormDTO.parse(props.histoOffre))

const idstatutoffreStatutOffresSearchField = HistoOffre.getSearchFieldByKey('idstatutoffreStatutOffres')
const idstatutoffreStatutOffresDefaultValue = props.histoOffre
? (props.histoOffre?.idstatutoffreStatutOffres?.getReferenceValue?.() ?? undefined)
: undefined      


function removeRow() {
  emit('request:remove')
}

function updateModel() {
  emit('udpate:model-value', formModel.value)
}



onMounted(() => {
  updateModel()
})
</script>

<template>
  <tr>
    <td>
      {{ internalId }}
    </td>
    <td>
      <GenesisSelectSearchCriteria
        v-if="idstatutoffreStatutOffresSearchField?.multicriteriaSelect"
        :violation="violations ? violations['idstatutoffreStatutOffres'] : undefined"
        placeholder="Select Idstatutoffre statut offres"
        key="HistoOffreIdstatutoffreStatutOffres"
        :search-function="idstatutoffreStatutOffresSearchField?.multicriteriaSelect.searchFunction"
        :filters="idstatutoffreStatutOffresSearchField?.multicriteriaSelect.filters"
        :default-value="idstatutoffreStatutOffresDefaultValue"
        @option-selected="
          (selectedValue) => {
            formModel.idstatutoffreStatutOffres = String(selectedValue)
            updateModel()
          }
        "
      />
    </td>
    
    <td>
      <GenesisInput
        :violation="violations ? violations['date'] : undefined"
        placeholder="Enter Date"
        type="datetime-local"
        v-model="formModel.date"
        :value="formModel.date"
        @update:model-value="(newVal) => updateModel()"
      />
    </td>
    
    <td>
      <GenesisInput
        :violation="violations ? violations['remarque'] : undefined"
        placeholder="Enter Remarque"
        type="text"
        v-model="formModel.remarque"
        :value="formModel.remarque"
        @update:model-value="(newVal) => updateModel()"
      />
    </td>
    
    
    <td class="text-center">
      <button type="button" title="delete row" @click="removeRow" class="btn btn-error btn-outline">
        <TrashIcon />
      </button>
    </td>
  </tr>
  
</template>

<style scoped></style>
