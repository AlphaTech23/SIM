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
  parentClassName?: string
}>()

const emit = defineEmits<{
  (e: 'request:remove'): void
  (e: 'udpate:model-value', value: RendezVouFormDTO): void
}>()

const formModel = ref<RendezVouFormDTO>(RendezVouFormDTO.parse(props.rendezVou))


// Masque la FK correspondant au parent courant (résolu à l'exécution via parentClassName)
const isParentFK = (fkType: string) => !!props.parentClassName && fkType === props.parentClassName

const iddemandeurMembresSearchField = RendezVou.getSearchFieldByKey('iddemandeurMembres')
// getKeyValue() = identifiant de la FK (attendu par le select), et non getReferenceValue() = libellé
const iddemandeurMembresDefaultValue = props.rendezVou
  ? (props.rendezVou?.iddemandeurMembres?.getKeyValue?.() ?? undefined)
  : undefined
const idreceveurSujetsMembresSearchField = RendezVou.getSearchFieldByKey('idreceveurSujetsMembres')
// getKeyValue() = identifiant de la FK (attendu par le select), et non getReferenceValue() = libellé
const idreceveurSujetsMembresDefaultValue = props.rendezVou
  ? (props.rendezVou?.idreceveurSujetsMembres?.getKeyValue?.() ?? undefined)
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
    <td v-if="!isParentFK('Membre')">
      <GenesisSelectSearchCriteria
        v-if="iddemandeurMembresSearchField?.multicriteriaSelect"
        :violation="violations ? violations['iddemandeurMembres'] : undefined"
        placeholder="Select Iddemandeur membres"
        key="RendezVouIddemandeurMembres"
        :search-function="iddemandeurMembresSearchField?.multicriteriaSelect.searchFunction"
        :filters="iddemandeurMembresSearchField?.multicriteriaSelect.filters"
        :default-value="iddemandeurMembresDefaultValue"
        @option-selected="
          (selectedValue) => {
            formModel.iddemandeurMembres = String(selectedValue)
            updateModel()
          }
        "
      />
    </td>
    
    <td v-if="!isParentFK('SujetsMembre')">
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
