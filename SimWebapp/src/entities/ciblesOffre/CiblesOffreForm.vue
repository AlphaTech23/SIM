<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <form @submit.prevent="handleSubmit" class="grid grid-cols-1 w-7/12 gap-4 mx-auto">
      <!-- <form @submit.prevent="handleSubmit" class=""> -->
        <div class="grid grid-cols-1 gap-4">
          <!-- Idoffre offres FK -->
          <GenesisSelectSearchCriteria
            v-if="idoffreOffresSearchField?.multicriteriaSelect"
            label="Idoffre offres"
            key="ciblesOffreIdoffreOffres"
            :search-function="idoffreOffresSearchField?.multicriteriaSelect.searchFunction"
            :filters="idoffreOffresSearchField?.multicriteriaSelect.filters"
            :default-value="idoffreOffresDefaultValue"
            :violation="violations ? violations['idoffreOffres'] : undefined"
            @option-selected="(selectedValue) => (formModel.idoffreOffres = String(selectedValue))"
          />
          
          <!-- Idmembre membres FK -->
          <GenesisSelectSearchCriteria
            v-if="idmembreMembresSearchField?.multicriteriaSelect"
            label="Idmembre membres"
            key="ciblesOffreIdmembreMembres"
            :search-function="idmembreMembresSearchField?.multicriteriaSelect.searchFunction"
            :filters="idmembreMembresSearchField?.multicriteriaSelect.filters"
            :default-value="idmembreMembresDefaultValue"
            :violation="violations ? violations['idmembreMembres'] : undefined"
            @option-selected="(selectedValue) => (formModel.idmembreMembres = String(selectedValue))"
          />
          
          <!-- Date envoi -->
          <GenesisInput
            label="Date envoi"
            :violation="violations ? violations['dateEnvoi'] : undefined"
            placeholder="Enter Date envoi"
            type="datetime-local"
            v-model="formModel.dateEnvoi"
            :value="formModel.dateEnvoi"
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
import { CiblesOffre, CiblesOffreFormDTO } from '@/models/CiblesOffreModel.ts'
import GenesisButton from '@/components/button/GenesisButton.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'


const props = defineProps<{
  ciblesOffre?: CiblesOffre
  violations?: Record<string, string> | null
  submitLabel?: string
}>()

const emit = defineEmits<{
  (e: 'submit', payload: Partial<CiblesOffreFormDTO>): void
  (e: 'cancel', payload: Partial<CiblesOffreFormDTO>): void
}>()


const formModel = ref<Partial<CiblesOffreFormDTO>>({ ...CiblesOffreFormDTO.parse(props.ciblesOffre) })

const idoffreOffresSearchField = CiblesOffre.getSearchFieldByKey('idoffreOffres')
const idoffreOffresDefaultValue = props.ciblesOffre
? (props.ciblesOffre?.idoffreOffres?.getKeyValue?.() ?? undefined)
: undefined
const idmembreMembresSearchField = CiblesOffre.getSearchFieldByKey('idmembreMembres')
const idmembreMembresDefaultValue = props.ciblesOffre
? (props.ciblesOffre?.idmembreMembres?.getKeyValue?.() ?? undefined)
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
