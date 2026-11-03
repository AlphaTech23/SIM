<script setup lang="ts">
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import { ref, onMounted } from 'vue'
import { RendezVou, RendezVouFormDTO } from '@/models/RendezVouModel'
import TrashIcon from '@/components/icons/TrashIcon.vue'


const props = defineProps<{
  internalId: number
  rendezVou: RendezVou
  violations?: Record<string, string> | null
}>()

const emit = defineEmits<{
  (e: 'request:remove'): void
  (e: 'udpate:model-value', value: RendezVouFormDTO): void
}>()

const formModel = ref<RendezVouFormDTO>(RendezVouFormDTO.parse(props.rendezVou))

const idreceveurSujetsMembresSearchField = RendezVou.getSearchFieldByKey('idreceveurSujetsMembres')
const idreceveurSujetsMembresDefaultValue = props.rendezVou
? (props.rendezVou?.idreceveurSujetsMembres?.getReferenceValue?.() ?? undefined)
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
        v-if="idreceveurSujetsMembresSearchField?.multicriteriaSelect"
        :violation="violations ? violations['idreceveurSujetsMembres'] : undefined"
        placeholder="Select Idreceveur sujets membres"
        key="RendezVouIdreceveurSujetsMembres"
        :search-function="idreceveurSujetsMembresSearchField?.multicriteriaSelect.searchFunction"
        :filters="idreceveurSujetsMembresSearchField?.multicriteriaSelect.filters"
        :default-value="idreceveurSujetsMembresDefaultValue"
        @option-selected="
          (selectedValue) => {
            formModel.idreceveurSujetsMembres = String(selectedValue)
            updateModel()
          }
        "
      />
    </td>
    
    <td>
      <GenesisInput
        :violation="violations ? violations['contexte'] : undefined"
        placeholder="Enter Contexte"
        type="text"
        v-model="formModel.contexte"
        :value="formModel.contexte"
        @update:model-value="(newVal) => updateModel()"
      />
    </td>
    
    <td>
      <GenesisInput
        :violation="violations ? violations['dateRdv'] : undefined"
        placeholder="Enter Date rdv"
        type="datetime-local"
        v-model="formModel.dateRdv"
        :value="formModel.dateRdv"
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
