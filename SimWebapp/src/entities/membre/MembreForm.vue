<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <form @submit.prevent="handleSubmit" class="grid grid-cols-1 w-7/12 gap-4 mx-auto">
      <!-- <form @submit.prevent="handleSubmit" class=""> -->
        <div class="grid grid-cols-1 gap-4">
          <!-- Idsecteur secteurs FK -->
          <GenesisSelectSearchCriteria
            v-if="idsecteurSecteursSearchField?.multicriteriaSelect"
            label="Idsecteur secteurs"
            key="membreIdsecteurSecteurs"
            :search-function="idsecteurSecteursSearchField?.multicriteriaSelect.searchFunction"
            :filters="idsecteurSecteursSearchField?.multicriteriaSelect.filters"
            :default-value="idsecteurSecteursDefaultValue"
            :violation="membreViolations ? membreViolations['idsecteurSecteurs'] : undefined"
            @option-selected="(selectedValue) => (formModel.idsecteurSecteurs = String(selectedValue))"
          />
          
          <!-- Intitule -->
          <GenesisInput
            label="Intitule"
            :violation="membreViolations ? membreViolations['intitule'] : undefined"
            placeholder="Enter Intitule"
            type="text"
            v-model="formModel.intitule"
            :value="formModel.intitule"
          />
          
          <!-- Logo -->
          <GenesisInput
            label="Logo"
            :violation="membreViolations ? membreViolations['logo'] : undefined"
            placeholder="Enter Logo"
            type="text"
            v-model="formModel.logo"
            :value="formModel.logo"
          />
          
          <!-- Telephone -->
          <GenesisInput
            label="Telephone"
            :violation="membreViolations ? membreViolations['telephone'] : undefined"
            placeholder="Enter Telephone"
            type="text"
            v-model="formModel.telephone"
            :value="formModel.telephone"
          />
          
          <!-- Email -->
          <GenesisInput
            label="Email"
            :violation="membreViolations ? membreViolations['email'] : undefined"
            placeholder="Enter Email"
            type="text"
            v-model="formModel.email"
            :value="formModel.email"
          />
          
          <!-- Whatsapp -->
          <GenesisInput
            label="Whatsapp"
            :violation="membreViolations ? membreViolations['whatsapp'] : undefined"
            placeholder="Enter Whatsapp"
            type="text"
            v-model="formModel.whatsapp"
            :value="formModel.whatsapp"
          />
          
          <!-- Adresse -->
          <GenesisInput
            label="Adresse"
            :violation="membreViolations ? membreViolations['adresse'] : undefined"
            placeholder="Enter Adresse"
            type="text"
            v-model="formModel.adresse"
            :value="formModel.adresse"
          />
          
        </div>
        <ActivitesMembreTableForm
          :initial-data="activitesMembresData"
          :get-table-data-callback="setActivitesMembres"
          :violations="activitesMembreViolations"
          parentClassName="Membre"
          class="w-full"
        />
        <RendezVouTableForm
          :initial-data="rendezVousData"
          :get-table-data-callback="setRendezVous"
          :violations="rendezVouViolations"
          parentClassName="Membre"
          class="w-full"
        />
        <SujetsMembreTableForm
          :initial-data="sujetsMembresData"
          :get-table-data-callback="setSujetsMembres"
          :violations="sujetsMembreViolations"
          parentClassName="Membre"
          class="w-full"
        />
        
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
 import { ref, computed, onMounted } from 'vue'
import { Membre, MembreFormDTO } from '@/models/MembreModel.ts'
import GenesisButton from '@/components/button/GenesisButton.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import GenesisSelectSearchCriteria from '@/components/form/GenesisSelectSearchCriteria.vue'
import{ ActivitesMembreFormDTO, type ActivitesMembre } from '@/models/ActivitesMembreModel.ts'
import ActivitesMembreTableForm from '@/entities/activitesMembre/ActivitesMembreTableForm.vue'
import{ RendezVouFormDTO, type RendezVou } from '@/models/RendezVouModel.ts'
import RendezVouTableForm from '@/entities/rendezVou/RendezVouTableForm.vue'
import{ SujetsMembreFormDTO, type SujetsMembre } from '@/models/SujetsMembreModel.ts'
import SujetsMembreTableForm from '@/entities/sujetsMembre/SujetsMembreTableForm.vue'



const props = defineProps<{
  membre?: Membre
  
  activitesMembresData?: ActivitesMembre[]
  rendezVousData?: RendezVou[]
  sujetsMembresData?: SujetsMembre[]
  
  violations?:{
    membre: Record<string, string>
    activitesMembres: Record<string, string>[]
    rendezVous: Record<string, string>[]
    sujetsMembres: Record<string, string>[]
    
  }
  
  submitLabel?: string
}>()

const emit = defineEmits<{
  (e: 'submit', payload: { membre: Partial<MembreFormDTO>; activitesMembres: ActivitesMembreFormDTO[]; rendezVous: RendezVouFormDTO[]; sujetsMembres: SujetsMembreFormDTO[] }): void
  (e: 'cancel', payload: Partial<MembreFormDTO>): void
}>()

const membreViolations = computed(() => props.violations ? props.violations.membre : {})
const activitesMembreViolations = computed(() => props.violations ? props.violations.activitesMembres : [])
const rendezVouViolations = computed(() => props.violations ? props.violations.rendezVous : [])
const sujetsMembreViolations = computed(() => props.violations ? props.violations.sujetsMembres : [])



const formModel = ref<Partial<MembreFormDTO>>({ ...MembreFormDTO.parse(props.membre) })

const idsecteurSecteursSearchField = Membre.getSearchFieldByKey('idsecteurSecteurs')
const idsecteurSecteursDefaultValue = props.membre
? (props.membre?.idsecteurSecteurs?.getKeyValue?.() ?? undefined)
: undefined


const activitesMembres =ref<ActivitesMembreFormDTO[]>([])
const setActivitesMembres = (data: ActivitesMembreFormDTO[]) => {
  activitesMembres.value = data
}
const rendezVous =ref<RendezVouFormDTO[]>([])
const setRendezVous = (data: RendezVouFormDTO[]) => {
  rendezVous.value = data
}
const sujetsMembres =ref<SujetsMembreFormDTO[]>([])
const setSujetsMembres = (data: SujetsMembreFormDTO[]) => {
  sujetsMembres.value = data
}

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
  
  emit('submit', { membre: data, activitesMembres: activitesMembres.value, rendezVous: rendezVous.value, sujetsMembres: sujetsMembres.value })
  
}

function cancelForm() {
  emit('cancel', formModel.value)
}
onMounted(async () => {
  if (Array.isArray(props.activitesMembresData) && props.activitesMembresData.length > 0) {
    setActivitesMembres(ActivitesMembreFormDTO.parseList(props.activitesMembresData))
  }if (Array.isArray(props.rendezVousData) && props.rendezVousData.length > 0) {
    setRendezVous(RendezVouFormDTO.parseList(props.rendezVousData))
  }if (Array.isArray(props.sujetsMembresData) && props.sujetsMembresData.length > 0) {
    setSujetsMembres(SujetsMembreFormDTO.parseList(props.sujetsMembresData))
  }
})

</script>
<style scoped></style>
