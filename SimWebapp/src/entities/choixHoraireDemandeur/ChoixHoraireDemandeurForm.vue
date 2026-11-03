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
            key="choixHoraireDemandeurIdrendezvousRendezVous"
            :search-function="idrendezvousRendezVousSearchField?.multicriteriaSelect.searchFunction"
            :filters="idrendezvousRendezVousSearchField?.multicriteriaSelect.filters"
            :default-value="idrendezvousRendezVousDefaultValue"
            :violation="violations ? violations['idrendezvousRendezVous'] : undefined"
            @option-selected="(selectedValue) => (formModel.idrendezvousRendezVous = String(selectedValue))"
          />
          
          <!-- Date choix -->
          <GenesisInput
            label="Date choix"
            :violation="violations ? violations['dateChoix'] : undefined"
            placeholder="Enter Date choix"
            type="datetime-local"
            v-model="formModel.dateChoix"
            :value="formModel.dateChoix"
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
import { ChoixHoraireDemandeur, ChoixHoraireDemandeurFormDTO } from '@/models/ChoixHoraireDemandeurModel.ts'
import GenesisButton from '@/components/button/GenesisButton.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'


const props = defineProps<{
  choixHoraireDemandeur?: ChoixHoraireDemandeur
  violations?: Record<string, string> | null
  submitLabel?: string
}>()

const emit = defineEmits<{
  (e: 'submit', payload: Partial<ChoixHoraireDemandeurFormDTO>): void
  (e: 'cancel', payload: Partial<ChoixHoraireDemandeurFormDTO>): void
}>()


const formModel = ref<Partial<ChoixHoraireDemandeurFormDTO>>({ ...ChoixHoraireDemandeurFormDTO.parse(props.choixHoraireDemandeur) })

const idrendezvousRendezVousSearchField = ChoixHoraireDemandeur.getSearchFieldByKey('idrendezvousRendezVous')
const idrendezvousRendezVousDefaultValue = props.choixHoraireDemandeur
? (props.choixHoraireDemandeur?.idrendezvousRendezVous?.getKeyValue?.() ?? undefined)
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
