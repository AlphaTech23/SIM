<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <form @submit.prevent="handleSubmit" class="grid grid-cols-1 w-7/12 gap-4 mx-auto">
      <!-- <form @submit.prevent="handleSubmit" class=""> -->
        <div class="grid grid-cols-1 gap-4">
          <!-- Iddemandeur membres FK -->
          <GenesisSelectSearchCriteria
            v-if="iddemandeurMembresSearchField?.multicriteriaSelect"
            label="Iddemandeur membres"
            key="rendezVouIddemandeurMembres"
            :search-function="iddemandeurMembresSearchField?.multicriteriaSelect.searchFunction"
            :filters="iddemandeurMembresSearchField?.multicriteriaSelect.filters"
            :default-value="iddemandeurMembresDefaultValue"
            :violation="violations ? violations['iddemandeurMembres'] : undefined"
            @option-selected="(selectedValue) => (formModel.iddemandeurMembres = String(selectedValue))"
          />
          
          <!-- Idreceveur sujets membres FK -->
          <GenesisSelectSearchCriteria
            v-if="idreceveurSujetsMembresSearchField?.multicriteriaSelect"
            label="Idreceveur sujets membres"
            key="rendezVouIdreceveurSujetsMembres"
            :search-function="idreceveurSujetsMembresSearchField?.multicriteriaSelect.searchFunction"
            :filters="idreceveurSujetsMembresSearchField?.multicriteriaSelect.filters"
            :default-value="idreceveurSujetsMembresDefaultValue"
            :violation="violations ? violations['idreceveurSujetsMembres'] : undefined"
            @option-selected="(selectedValue) => (formModel.idreceveurSujetsMembres = String(selectedValue))"
          />
          
          <!-- Contexte -->
          <GenesisInput
            label="Contexte"
            :violation="violations ? violations['contexte'] : undefined"
            placeholder="Enter Contexte"
            type="text"
            v-model="formModel.contexte"
            :value="formModel.contexte"
          />
          
          <!-- Date rdv -->
          <GenesisInput
            label="Date rdv"
            :violation="violations ? violations['dateRdv'] : undefined"
            placeholder="Enter Date rdv"
            type="datetime-local"
            v-model="formModel.dateRdv"
            :value="formModel.dateRdv"
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
import { RendezVou, RendezVouFormDTO } from '@/models/RendezVouModel.ts'
import GenesisButton from '@/components/button/GenesisButton.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'


const props = defineProps<{
  rendezVou?: RendezVou
  violations?: Record<string, string> | null
  submitLabel?: string
}>()

const emit = defineEmits<{
  (e: 'submit', payload: Partial<RendezVouFormDTO>): void
  (e: 'cancel', payload: Partial<RendezVouFormDTO>): void
}>()


const formModel = ref<Partial<RendezVouFormDTO>>({ ...RendezVouFormDTO.parse(props.rendezVou) })

const iddemandeurMembresSearchField = RendezVou.getSearchFieldByKey('iddemandeurMembres')
const iddemandeurMembresDefaultValue = props.rendezVou
? (props.rendezVou?.iddemandeurMembres?.getKeyValue?.() ?? undefined)
: undefined
const idreceveurSujetsMembresSearchField = RendezVou.getSearchFieldByKey('idreceveurSujetsMembres')
const idreceveurSujetsMembresDefaultValue = props.rendezVou
? (props.rendezVou?.idreceveurSujetsMembres?.getKeyValue?.() ?? undefined)
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
