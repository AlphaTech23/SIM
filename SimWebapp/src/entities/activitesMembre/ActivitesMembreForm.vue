<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <form @submit.prevent="handleSubmit" class="grid grid-cols-1 w-7/12 gap-4 mx-auto">
      <!-- <form @submit.prevent="handleSubmit" class=""> -->
        <div class="grid grid-cols-1 gap-4">
          <!-- Idmembre membres FK -->
          <GenesisSelectSearchCriteria
            v-if="idmembreMembresSearchField?.multicriteriaSelect"
            label="Idmembre membres"
            key="activitesMembreIdmembreMembres"
            :search-function="idmembreMembresSearchField?.multicriteriaSelect.searchFunction"
            :filters="idmembreMembresSearchField?.multicriteriaSelect.filters"
            :default-value="idmembreMembresDefaultValue"
            :violation="violations ? violations['idmembreMembres'] : undefined"
            @option-selected="(selectedValue) => (formModel.idmembreMembres = String(selectedValue))"
          />
          
          <!-- Idactivite activites FK -->
          <GenesisSelectSearchCriteria
            v-if="idactiviteActivitesSearchField?.multicriteriaSelect"
            label="Idactivite activites"
            key="activitesMembreIdactiviteActivites"
            :search-function="idactiviteActivitesSearchField?.multicriteriaSelect.searchFunction"
            :filters="idactiviteActivitesSearchField?.multicriteriaSelect.filters"
            :default-value="idactiviteActivitesDefaultValue"
            :violation="violations ? violations['idactiviteActivites'] : undefined"
            @option-selected="(selectedValue) => (formModel.idactiviteActivites = String(selectedValue))"
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
import { ActivitesMembre, ActivitesMembreFormDTO } from '@/models/ActivitesMembreModel.ts'
import GenesisButton from '@/components/button/GenesisButton.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'


const props = defineProps<{
  activitesMembre?: ActivitesMembre
  violations?: Record<string, string> | null
  submitLabel?: string
}>()

const emit = defineEmits<{
  (e: 'submit', payload: Partial<ActivitesMembreFormDTO>): void
  (e: 'cancel', payload: Partial<ActivitesMembreFormDTO>): void
}>()


const formModel = ref<Partial<ActivitesMembreFormDTO>>({ ...ActivitesMembreFormDTO.parse(props.activitesMembre) })

const idmembreMembresSearchField = ActivitesMembre.getSearchFieldByKey('idmembreMembres')
const idmembreMembresDefaultValue = props.activitesMembre
? (props.activitesMembre?.idmembreMembres?.getKeyValue?.() ?? undefined)
: undefined
const idactiviteActivitesSearchField = ActivitesMembre.getSearchFieldByKey('idactiviteActivites')
const idactiviteActivitesDefaultValue = props.activitesMembre
? (props.activitesMembre?.idactiviteActivites?.getKeyValue?.() ?? undefined)
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
