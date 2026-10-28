<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <form @submit.prevent="handleSubmit" class="grid grid-cols-1 w-7/12 gap-4 mx-auto">
      <!-- <form @submit.prevent="handleSubmit" class=""> -->
        <div class="grid grid-cols-1 gap-4">
          <!-- Idrendezvous rendez vous FK -->
          <GenesisSelectSearchCriteria
            v-if="idrendezvousRendezVousSearchField?.multicriteriaSelect"
            label="Idrendezvous rendez vous"
            key="statutRendezVouIdrendezvousRendezVous"
            :search-function="idrendezvousRendezVousSearchField?.multicriteriaSelect.searchFunction"
            :filters="idrendezvousRendezVousSearchField?.multicriteriaSelect.filters"
            :default-value="idrendezvousRendezVousDefaultValue"
            :violation="violations ? violations['idrendezvousRendezVous'] : undefined"
            @option-selected="(selectedValue) => (formModel.idrendezvousRendezVous = String(selectedValue))"
          />
          
          <!-- Idstatut statut FK -->
          <GenesisSelectSearchCriteria
            v-if="idstatutStatutSearchField?.multicriteriaSelect"
            label="Idstatut statut"
            key="statutRendezVouIdstatutStatut"
            :search-function="idstatutStatutSearchField?.multicriteriaSelect.searchFunction"
            :filters="idstatutStatutSearchField?.multicriteriaSelect.filters"
            :default-value="idstatutStatutDefaultValue"
            :violation="violations ? violations['idstatutStatut'] : undefined"
            @option-selected="(selectedValue) => (formModel.idstatutStatut = String(selectedValue))"
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
import { StatutRendezVou, StatutRendezVouFormDTO } from '@/models/StatutRendezVouModel.ts'
import GenesisButton from '@/components/button/GenesisButton.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'


const props = defineProps<{
  statutRendezVou?: StatutRendezVou
  violations?: Record<string, string> | null
  submitLabel?: string
}>()

const emit = defineEmits<{
  (e: 'submit', payload: Partial<StatutRendezVouFormDTO>): void
  (e: 'cancel', payload: Partial<StatutRendezVouFormDTO>): void
}>()


const formModel = ref<Partial<StatutRendezVouFormDTO>>({ ...StatutRendezVouFormDTO.parse(props.statutRendezVou) })

const idrendezvousRendezVousSearchField = StatutRendezVou.getSearchFieldByKey('idrendezvousRendezVous')
const idrendezvousRendezVousDefaultValue = props.statutRendezVou
? (props.statutRendezVou?.idrendezvousRendezVous?.getKeyValue?.() ?? undefined)
: undefined
const idstatutStatutSearchField = StatutRendezVou.getSearchFieldByKey('idstatutStatut')
const idstatutStatutDefaultValue = props.statutRendezVou
? (props.statutRendezVou?.idstatutStatut?.getKeyValue?.() ?? undefined)
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
