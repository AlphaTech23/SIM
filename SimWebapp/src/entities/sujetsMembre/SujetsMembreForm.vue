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
            key="sujetsMembreIdmembreMembres"
            :search-function="idmembreMembresSearchField?.multicriteriaSelect.searchFunction"
            :filters="idmembreMembresSearchField?.multicriteriaSelect.filters"
            :default-value="idmembreMembresDefaultValue"
            :violation="violations ? violations['idmembreMembres'] : undefined"
            @option-selected="(selectedValue) => (formModel.idmembreMembres = String(selectedValue))"
          />
          
          <!-- Idsujet sujets FK -->
          <GenesisSelectSearchCriteria
            v-if="idsujetSujetsSearchField?.multicriteriaSelect"
            label="Idsujet sujets"
            key="sujetsMembreIdsujetSujets"
            :search-function="idsujetSujetsSearchField?.multicriteriaSelect.searchFunction"
            :filters="idsujetSujetsSearchField?.multicriteriaSelect.filters"
            :default-value="idsujetSujetsDefaultValue"
            :violation="violations ? violations['idsujetSujets'] : undefined"
            @option-selected="(selectedValue) => (formModel.idsujetSujets = String(selectedValue))"
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
import { SujetsMembre, SujetsMembreFormDTO } from '@/models/SujetsMembreModel.ts'
import GenesisButton from '@/components/button/GenesisButton.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'


const props = defineProps<{
  sujetsMembre?: SujetsMembre
  violations?: Record<string, string> | null
  submitLabel?: string
}>()

const emit = defineEmits<{
  (e: 'submit', payload: Partial<SujetsMembreFormDTO>): void
  (e: 'cancel', payload: Partial<SujetsMembreFormDTO>): void
}>()


const formModel = ref<Partial<SujetsMembreFormDTO>>({ ...SujetsMembreFormDTO.parse(props.sujetsMembre) })

const idmembreMembresSearchField = SujetsMembre.getSearchFieldByKey('idmembreMembres')
const idmembreMembresDefaultValue = props.sujetsMembre
? (props.sujetsMembre?.idmembreMembres?.getKeyValue?.() ?? undefined)
: undefined
const idsujetSujetsSearchField = SujetsMembre.getSearchFieldByKey('idsujetSujets')
const idsujetSujetsDefaultValue = props.sujetsMembre
? (props.sujetsMembre?.idsujetSujets?.getKeyValue?.() ?? undefined)
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
