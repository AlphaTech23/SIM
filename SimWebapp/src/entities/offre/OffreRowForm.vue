<script setup lang="ts">
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import { ref, onMounted } from 'vue'
import { Offre, OffreFormDTO } from '@/models/OffreModel'
import TrashIcon from '@/components/icons/TrashIcon.vue'


const props = defineProps<{
  internalId: number
  offre: Offre
  violations?: Record<string, string> | null
}>()

const emit = defineEmits<{
  (e: 'request:remove'): void
  (e: 'udpate:model-value', value: OffreFormDTO): void
}>()

const formModel = ref<OffreFormDTO>(OffreFormDTO.parse(props.offre))

const idtypeoffreTypeOffresSearchField = Offre.getSearchFieldByKey('idtypeoffreTypeOffres')
const idtypeoffreTypeOffresDefaultValue = props.offre
? (props.offre?.idtypeoffreTypeOffres?.getReferenceValue?.() ?? undefined)
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
        v-if="idtypeoffreTypeOffresSearchField?.multicriteriaSelect"
        :violation="violations ? violations['idtypeoffreTypeOffres'] : undefined"
        placeholder="Select Idtypeoffre type offres"
        key="OffreIdtypeoffreTypeOffres"
        :search-function="idtypeoffreTypeOffresSearchField?.multicriteriaSelect.searchFunction"
        :filters="idtypeoffreTypeOffresSearchField?.multicriteriaSelect.filters"
        :default-value="idtypeoffreTypeOffresDefaultValue"
        @option-selected="
          (selectedValue) => {
            formModel.idtypeoffreTypeOffres = String(selectedValue)
            updateModel()
          }
        "
      />
    </td>
    
    <td>
      <GenesisInput
        :violation="violations ? violations['titreOffre'] : undefined"
        placeholder="Enter Titre offre"
        type="text"
        v-model="formModel.titreOffre"
        :value="formModel.titreOffre"
        @update:model-value="(newVal) => updateModel()"
      />
    </td>
    
    <td>
      <GenesisInput
        :violation="violations ? violations['descriptions'] : undefined"
        placeholder="Enter Descriptions"
        type="text"
        v-model="formModel.descriptions"
        :value="formModel.descriptions"
        @update:model-value="(newVal) => updateModel()"
      />
    </td>
    
    <td>
      <GenesisInput
        :violation="violations ? violations['conditions'] : undefined"
        placeholder="Enter Conditions"
        type="text"
        v-model="formModel.conditions"
        :value="formModel.conditions"
        @update:model-value="(newVal) => updateModel()"
      />
    </td>
    
    <td>
      <GenesisInput
        :violation="violations ? violations['dateCreation'] : undefined"
        placeholder="Enter Date creation"
        type="datetime-local"
        v-model="formModel.dateCreation"
        :value="formModel.dateCreation"
        @update:model-value="(newVal) => updateModel()"
      />
    </td>
    
    <td>
      <GenesisInput
        :violation="violations ? violations['dateFinValidite'] : undefined"
        placeholder="Enter Date fin validite"
        type="date"
        v-model="formModel.dateFinValidite"
        :value="formModel.dateFinValidite"
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
