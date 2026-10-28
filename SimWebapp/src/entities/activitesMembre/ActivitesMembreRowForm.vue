<script setup lang="ts">
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import { ref, onMounted } from 'vue'
import { ActivitesMembre, ActivitesMembreFormDTO } from '@/models/ActivitesMembreModel'
import TrashIcon from '@/components/icons/TrashIcon.vue'


const props = defineProps<{
  internalId: number
  activitesMembre: ActivitesMembre
  violations?: Record<string, string> | null
  parentClassName?: string
}>()

const emit = defineEmits<{
  (e: 'request:remove'): void
  (e: 'udpate:model-value', value: ActivitesMembreFormDTO): void
}>()

const formModel = ref<ActivitesMembreFormDTO>(ActivitesMembreFormDTO.parse(props.activitesMembre))


// Masque la FK correspondant au parent courant (résolu à l'exécution via parentClassName)
const isParentFK = (fkType: string) => !!props.parentClassName && fkType === props.parentClassName

const idmembreMembresSearchField = ActivitesMembre.getSearchFieldByKey('idmembreMembres')
// getKeyValue() = identifiant de la FK (attendu par le select), et non getReferenceValue() = libellé
const idmembreMembresDefaultValue = props.activitesMembre
  ? (props.activitesMembre?.idmembreMembres?.getKeyValue?.() ?? undefined)
  : undefined
const idactiviteActivitesSearchField = ActivitesMembre.getSearchFieldByKey('idactiviteActivites')
// getKeyValue() = identifiant de la FK (attendu par le select), et non getReferenceValue() = libellé
const idactiviteActivitesDefaultValue = props.activitesMembre
  ? (props.activitesMembre?.idactiviteActivites?.getKeyValue?.() ?? undefined)
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
        key="ActivitesMembreIdmembreMembres"
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
    
    <td v-if="!isParentFK('Activite')">
      <GenesisSelectSearchCriteria
        v-if="idactiviteActivitesSearchField?.multicriteriaSelect"
        :violation="violations ? violations['idactiviteActivites'] : undefined"
        placeholder="Select Idactivite activites"
        key="ActivitesMembreIdactiviteActivites"
        :search-function="idactiviteActivitesSearchField?.multicriteriaSelect.searchFunction"
        :filters="idactiviteActivitesSearchField?.multicriteriaSelect.filters"
        :default-value="idactiviteActivitesDefaultValue"
        @option-selected="
          (selectedValue) => {
            formModel.idactiviteActivites = String(selectedValue)
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
