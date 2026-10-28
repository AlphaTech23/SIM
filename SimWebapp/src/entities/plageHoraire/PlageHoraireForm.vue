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
            key="plageHoraireIdrendezvousRendezVous"
            :search-function="idrendezvousRendezVousSearchField?.multicriteriaSelect.searchFunction"
            :filters="idrendezvousRendezVousSearchField?.multicriteriaSelect.filters"
            :default-value="idrendezvousRendezVousDefaultValue"
            :violation="violations ? violations['idrendezvousRendezVous'] : undefined"
            @option-selected="(selectedValue) => (formModel.idrendezvousRendezVous = String(selectedValue))"
          />
          
          <!-- Jour debut -->
          <GenesisInput
            label="Jour debut"
            :violation="violations ? violations['jourDebut'] : undefined"
            placeholder="Enter Jour debut"
            type="datetime-local"
            v-model="formModel.jourDebut"
            :value="formModel.jourDebut"
          />
          
          <!-- Jour fin -->
          <GenesisInput
            label="Jour fin"
            :violation="violations ? violations['jourFin'] : undefined"
            placeholder="Enter Jour fin"
            type="datetime-local"
            v-model="formModel.jourFin"
            :value="formModel.jourFin"
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
import { PlageHoraire, PlageHoraireFormDTO } from '@/models/PlageHoraireModel.ts'
import GenesisButton from '@/components/button/GenesisButton.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'


const props = defineProps<{
  plageHoraire?: PlageHoraire
  violations?: Record<string, string> | null
  submitLabel?: string
}>()

const emit = defineEmits<{
  (e: 'submit', payload: Partial<PlageHoraireFormDTO>): void
  (e: 'cancel', payload: Partial<PlageHoraireFormDTO>): void
}>()


const formModel = ref<Partial<PlageHoraireFormDTO>>({ ...PlageHoraireFormDTO.parse(props.plageHoraire) })

const idrendezvousRendezVousSearchField = PlageHoraire.getSearchFieldByKey('idrendezvousRendezVous')
const idrendezvousRendezVousDefaultValue = props.plageHoraire
? (props.plageHoraire?.idrendezvousRendezVous?.getKeyValue?.() ?? undefined)
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
