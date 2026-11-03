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
}>()

const emit = defineEmits<{
  (e: 'request:remove'): void
  (e: 'udpate:model-value', value: StatutRendezVouFormDTO): void
}>()

const formModel = ref<StatutRendezVouFormDTO>(StatutRendezVouFormDTO.parse(props.statutRendezVou))

const idrendezvousRendezVousSearchField = StatutRendezVou.getSearchFieldByKey('idrendezvousRendezVous')
const idrendezvousRendezVousDefaultValue = props.statutRendezVou
? (props.statutRendezVou?.idrendezvousRendezVous?.getReferenceValue?.() ?? undefined)
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
