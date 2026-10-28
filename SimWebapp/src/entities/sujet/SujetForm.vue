<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <form @submit.prevent="handleSubmit" class="grid grid-cols-1 w-7/12 gap-4 mx-auto">
      <!-- <form @submit.prevent="handleSubmit" class=""> -->
        <div class="grid grid-cols-1 gap-4">
          <!-- Libele -->
          <GenesisInput
            label="Libele"
            :violation="sujetViolations ? sujetViolations['libele'] : undefined"
            placeholder="Enter Libele"
            type="text"
            v-model="formModel.libele"
            :value="formModel.libele"
          />
          
          <!-- Description -->
          <GenesisInput
            label="Description"
            :violation="sujetViolations ? sujetViolations['description'] : undefined"
            placeholder="Enter Description"
            type="text"
            v-model="formModel.description"
            :value="formModel.description"
          />
          
          <!-- Nom -->
          <GenesisInput
            label="Nom"
            :violation="sujetViolations ? sujetViolations['nom'] : undefined"
            placeholder="Enter Nom"
            type="text"
            v-model="formModel.nom"
            :value="formModel.nom"
          />
          
          <!-- Prenom -->
          <GenesisInput
            label="Prenom"
            :violation="sujetViolations ? sujetViolations['prenom'] : undefined"
            placeholder="Enter Prenom"
            type="text"
            v-model="formModel.prenom"
            :value="formModel.prenom"
          />
          
          <!-- Fonction -->
          <GenesisInput
            label="Fonction"
            :violation="sujetViolations ? sujetViolations['fonction'] : undefined"
            placeholder="Enter Fonction"
            type="text"
            v-model="formModel.fonction"
            :value="formModel.fonction"
          />
          
          <!-- Telephone -->
          <GenesisInput
            label="Telephone"
            :violation="sujetViolations ? sujetViolations['telephone'] : undefined"
            placeholder="Enter Telephone"
            type="text"
            v-model="formModel.telephone"
            :value="formModel.telephone"
          />
          
          <!-- Email -->
          <GenesisInput
            label="Email"
            :violation="sujetViolations ? sujetViolations['email'] : undefined"
            placeholder="Enter Email"
            type="text"
            v-model="formModel.email"
            :value="formModel.email"
          />
          
          <!-- Whatsapp -->
          <GenesisInput
            label="Whatsapp"
            :violation="sujetViolations ? sujetViolations['whatsapp'] : undefined"
            placeholder="Enter Whatsapp"
            type="text"
            v-model="formModel.whatsapp"
            :value="formModel.whatsapp"
          />
          
          <!-- Adresse -->
          <GenesisInput
            label="Adresse"
            :violation="sujetViolations ? sujetViolations['adresse'] : undefined"
            placeholder="Enter Adresse"
            type="text"
            v-model="formModel.adresse"
            :value="formModel.adresse"
          />
          
        </div>
        <SujetsMembreTableForm
          :initial-data="sujetsMembresData"
          :get-table-data-callback="setSujetsMembres"
          :violations="sujetsMembreViolations"
          parentClassName="Sujet"
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
import { Sujet, SujetFormDTO } from '@/models/SujetModel.ts'
import GenesisButton from '@/components/button/GenesisButton.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import{ SujetsMembreFormDTO, type SujetsMembre } from '@/models/SujetsMembreModel.ts'
import SujetsMembreTableForm from '@/entities/sujetsMembre/SujetsMembreTableForm.vue'



const props = defineProps<{
  sujet?: Sujet
  
  sujetsMembresData?: SujetsMembre[]
  
  violations?:{
    sujet: Record<string, string>
    sujetsMembres: Record<string, string>[]
    
  }
  
  submitLabel?: string
}>()

const emit = defineEmits<{
  (e: 'submit', payload: { sujet: Partial<SujetFormDTO>; sujetsMembres: SujetsMembreFormDTO[] }): void
  (e: 'cancel', payload: Partial<SujetFormDTO>): void
}>()

const sujetViolations = computed(() => props.violations ? props.violations.sujet : {})
const sujetsMembreViolations = computed(() => props.violations ? props.violations.sujetsMembres : [])



const formModel = ref<Partial<SujetFormDTO>>({ ...SujetFormDTO.parse(props.sujet) })



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
  
  emit('submit', { sujet: data, sujetsMembres: sujetsMembres.value })
  
}

function cancelForm() {
  emit('cancel', formModel.value)
}
onMounted(async () => {
  if (Array.isArray(props.sujetsMembresData) && props.sujetsMembresData.length > 0) {
    setSujetsMembres(SujetsMembreFormDTO.parseList(props.sujetsMembresData))
  }
})

</script>
<style scoped></style>
