<script setup lang="ts">
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import { ref, onMounted } from 'vue'
import { StatutRendezVou, StatutRendezVouFormDTO } from '@/models/StatutRendezVouModel'
import TrashIcon from '@/components/icons/TrashIcon.vue'


const props = defineProps<{
  internalId: number
  statutRendezVou: StatutRendezVou
  violations?: Record<string, string> | null
  parentClassName?: string
}>()

const emit = defineEmits<{
  (e: 'request:remove'): void
  (e: 'udpate:model-value', value: StatutRendezVouFormDTO): void
}>()

const formModel = ref<StatutRendezVouFormDTO>(StatutRendezVouFormDTO.parse(props.statutRendezVou))


// Masque la FK correspondant au parent courant (résolu à l'exécution via parentClassName)
const isParentFK = (fkType: string) => !!props.parentClassName && fkType === props.parentClassName

const idrendezvousRendezVousSearchField = StatutRendezVou.getSearchFieldByKey('idrendezvousRendezVous')
// getKeyValue() = identifiant de la FK (attendu par le select), et non getReferenceValue() = libellé
const idrendezvousRendezVousDefaultValue = props.statutRendezVou
  ? (props.statutRendezVou?.idrendezvousRendezVous?.getKeyValue?.() ?? undefined)
  : undefined
const idstatutStatutSearchField = StatutRendezVou.getSearchFieldByKey('idstatutStatut')
// getKeyValue() = identifiant de la FK (attendu par le select), et non getReferenceValue() = libellé
const idstatutStatutDefaultValue = props.statutRendezVou
  ? (props.statutRendezVou?.idstatutStatut?.getKeyValue?.() ?? undefined)
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
    <td v-if="!isParentFK('RendezVou')">
      <GenesisSelectSearchCriteria
        v-if="idrendezvousRendezVousSearchField?.multicriteriaSelect"
        :violation="violations ? violations['idrendezvousRendezVous'] : undefined"
        placeholder="Select Idrendezvous rendez vous"
        key="StatutRendezVouIdrendezvousRendezVous"
        :search-function="idrendezvousRendezVousSearchField?.multicriteriaSelect.searchFunction"
        :filters="idrendezvousRendezVousSearchField?.multicriteriaSelect.filters"
        :default-value="idrendezvousRendezVousDefaultValue"
        @option-selected="
          (selectedValue) => {
            formModel.idrendezvousRendezVous = String(selectedValue)
            updateModel()
          }
        "
      />
    </td>
    
    <td v-if="!isParentFK('Statut')">
      <GenesisSelectSearchCriteria
        v-if="idstatutStatutSearchField?.multicriteriaSelect"
        :violation="violations ? violations['idstatutStatut'] : undefined"
        placeholder="Select Idstatut statut"
        key="StatutRendezVouIdstatutStatut"
        :search-function="idstatutStatutSearchField?.multicriteriaSelect.searchFunction"
        :filters="idstatutStatutSearchField?.multicriteriaSelect.filters"
        :default-value="idstatutStatutDefaultValue"
        @option-selected="
          (selectedValue) => {
            formModel.idstatutStatut = String(selectedValue)
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
