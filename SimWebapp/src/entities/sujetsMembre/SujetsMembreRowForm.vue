<script setup lang="ts">
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import { ref, onMounted } from 'vue'
import { SujetsMembre, SujetsMembreFormDTO } from '@/models/SujetsMembreModel'
import TrashIcon from '@/components/icons/TrashIcon.vue'


const props = defineProps<{
  internalId: number
  sujetsMembre: SujetsMembre
  violations?: Record<string, string> | null
  parentClassName?: string
}>()

const emit = defineEmits<{
  (e: 'request:remove'): void
  (e: 'udpate:model-value', value: SujetsMembreFormDTO): void
}>()

const formModel = ref<SujetsMembreFormDTO>(SujetsMembreFormDTO.parse(props.sujetsMembre))


// Masque la FK correspondant au parent courant (résolu à l'exécution via parentClassName)
const isParentFK = (fkType: string) => !!props.parentClassName && fkType === props.parentClassName

const idmembreMembresSearchField = SujetsMembre.getSearchFieldByKey('idmembreMembres')
// getKeyValue() = identifiant de la FK (attendu par le select), et non getReferenceValue() = libellé
const idmembreMembresDefaultValue = props.sujetsMembre
  ? (props.sujetsMembre?.idmembreMembres?.getKeyValue?.() ?? undefined)
  : undefined
const idsujetSujetsSearchField = SujetsMembre.getSearchFieldByKey('idsujetSujets')
// getKeyValue() = identifiant de la FK (attendu par le select), et non getReferenceValue() = libellé
const idsujetSujetsDefaultValue = props.sujetsMembre
  ? (props.sujetsMembre?.idsujetSujets?.getKeyValue?.() ?? undefined)
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
        v-if="idmembreMembresSearchField?.multicriteriaSelect"
        :violation="violations ? violations['idmembreMembres'] : undefined"
        placeholder="Select Idmembre membres"
        key="SujetsMembreIdmembreMembres"
        :search-function="idmembreMembresSearchField?.multicriteriaSelect.searchFunction"
        :filters="idmembreMembresSearchField?.multicriteriaSelect.filters"
        :default-value="idmembreMembresDefaultValue"
        @option-selected="
          (selectedValue) => {
            formModel.idmembreMembres = String(selectedValue)
            updateModel()
          }
        "
      />
    </td>
    
    <td v-if="!isParentFK('Sujet')">
      <GenesisSelectSearchCriteria
        v-if="idsujetSujetsSearchField?.multicriteriaSelect"
        :violation="violations ? violations['idsujetSujets'] : undefined"
        placeholder="Select Idsujet sujets"
        key="SujetsMembreIdsujetSujets"
        :search-function="idsujetSujetsSearchField?.multicriteriaSelect.searchFunction"
        :filters="idsujetSujetsSearchField?.multicriteriaSelect.filters"
        :default-value="idsujetSujetsDefaultValue"
        @option-selected="
          (selectedValue) => {
            formModel.idsujetSujets = String(selectedValue)
            updateModel()
          }
        "
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
