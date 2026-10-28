<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <form @submit.prevent="handleSubmit" class="grid grid-cols-1 w-7/12 gap-4 mx-auto">
      <!-- <form @submit.prevent="handleSubmit" class=""> -->
        <div class="grid grid-cols-1 gap-4">
          <!-- Libele -->
          <GenesisInput
            label="Libele"
            :violation="secteurViolations ? secteurViolations['libele'] : undefined"
            placeholder="Enter Libele"
            type="text"
            v-model="formModel.libele"
            :value="formModel.libele"
          />
          
          <!-- Description -->
          <GenesisInput
            label="Description"
            :violation="secteurViolations ? secteurViolations['description'] : undefined"
            placeholder="Enter Description"
            type="text"
            v-model="formModel.description"
            :value="formModel.description"
          />
          
        </div>
        <MembreTableForm
          :initial-data="membresData"
          :get-table-data-callback="setMembres"
          :violations="membreViolations"
          parentClassName="Secteur"
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
import { Secteur, SecteurFormDTO } from '@/models/SecteurModel.ts'
import GenesisButton from '@/components/button/GenesisButton.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import{ MembreFormDTO, type Membre } from '@/models/MembreModel.ts'
import MembreTableForm from '@/entities/membre/MembreTableForm.vue'



const props = defineProps<{
  secteur?: Secteur
  
  membresData?: Membre[]
  
  violations?:{
    secteur: Record<string, string>
    membres: Record<string, string>[]
    
  }
  
  submitLabel?: string
}>()

const emit = defineEmits<{
  (e: 'submit', payload: { secteur: Partial<SecteurFormDTO>; membres: MembreFormDTO[] }): void
  (e: 'cancel', payload: Partial<SecteurFormDTO>): void
}>()

const secteurViolations = computed(() => props.violations ? props.violations.secteur : {})
const membreViolations = computed(() => props.violations ? props.violations.membres : [])



const formModel = ref<Partial<SecteurFormDTO>>({ ...SecteurFormDTO.parse(props.secteur) })



const membres =ref<MembreFormDTO[]>([])
const setMembres = (data: MembreFormDTO[]) => {
  membres.value = data
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
  
  emit('submit', { secteur: data, membres: membres.value })
  
}

function cancelForm() {
  emit('cancel', formModel.value)
}
onMounted(async () => {
  if (Array.isArray(props.membresData) && props.membresData.length > 0) {
    setMembres(MembreFormDTO.parseList(props.membresData))
  }
})

</script>
<style scoped></style>
