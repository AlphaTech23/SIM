<script setup lang="ts">
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import { ref, onMounted } from 'vue'
import { Membre, MembreFormDTO } from '@/models/MembreModel'
import TrashIcon from '@/components/icons/TrashIcon.vue'


const props = defineProps<{
  internalId: number
  membre: Membre
  violations?: Record<string, string> | null
  parentClassName?: string
}>()

const emit = defineEmits<{
  (e: 'request:remove'): void
  (e: 'udpate:model-value', value: MembreFormDTO): void
}>()

const formModel = ref<MembreFormDTO>(MembreFormDTO.parse(props.membre))


// Masque la FK correspondant au parent courant (résolu à l'exécution via parentClassName)
const isParentFK = (fkType: string) => !!props.parentClassName && fkType === props.parentClassName

const idsecteurSecteursSearchField = Membre.getSearchFieldByKey('idsecteurSecteurs')
// getKeyValue() = identifiant de la FK (attendu par le select), et non getReferenceValue() = libellé
const idsecteurSecteursDefaultValue = props.membre
  ? (props.membre?.idsecteurSecteurs?.getKeyValue?.() ?? undefined)
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
    <td v-if="!isParentFK('Secteur')">
      <GenesisSelectSearchCriteria
        v-if="idsecteurSecteursSearchField?.multicriteriaSelect"
        :violation="violations ? violations['idsecteurSecteurs'] : undefined"
        placeholder="Select Idsecteur secteurs"
        key="MembreIdsecteurSecteurs"
        :search-function="idsecteurSecteursSearchField?.multicriteriaSelect.searchFunction"
        :filters="idsecteurSecteursSearchField?.multicriteriaSelect.filters"
        :default-value="idsecteurSecteursDefaultValue"
        @option-selected="
          (selectedValue) => {
            formModel.idsecteurSecteurs = String(selectedValue)
            updateModel()
          }
        "
      />
    </td>
    
    <td>
      <GenesisInput
        :violation="violations ? violations['intitule'] : undefined"
        placeholder="Enter Intitule"
        type="text"
        v-model="formModel.intitule"
        :value="formModel.intitule"
        @update:model-value="(newVal) => updateModel()"
      />
    </td>
    
    <td>
      <GenesisInput
        :violation="violations ? violations['logo'] : undefined"
        placeholder="Enter Logo"
        type="text"
        v-model="formModel.logo"
        :value="formModel.logo"
        @update:model-value="(newVal) => updateModel()"
      />
    </td>
    
    <td>
      <GenesisInput
        :violation="violations ? violations['telephone'] : undefined"
        placeholder="Enter Telephone"
        type="text"
        v-model="formModel.telephone"
        :value="formModel.telephone"
        @update:model-value="(newVal) => updateModel()"
      />
    </td>
    
    <td>
      <GenesisInput
        :violation="violations ? violations['email'] : undefined"
        placeholder="Enter Email"
        type="text"
        v-model="formModel.email"
        :value="formModel.email"
        @update:model-value="(newVal) => updateModel()"
      />
    </td>
    
    <td>
      <GenesisInput
        :violation="violations ? violations['whatsapp'] : undefined"
        placeholder="Enter Whatsapp"
        type="text"
        v-model="formModel.whatsapp"
        :value="formModel.whatsapp"
        @update:model-value="(newVal) => updateModel()"
      />
    </td>
    
    <td>
      <GenesisInput
        :violation="violations ? violations['adresse'] : undefined"
        placeholder="Enter Adresse"
        type="text"
        v-model="formModel.adresse"
        :value="formModel.adresse"
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
