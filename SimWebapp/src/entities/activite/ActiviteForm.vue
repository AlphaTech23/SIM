<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <form @submit.prevent="handleSubmit" class="grid grid-cols-1 w-7/12 gap-4 mx-auto">
      <!-- <form @submit.prevent="handleSubmit" class=""> -->
        <div class="grid grid-cols-1 gap-4">
          <!-- Libele -->
          <GenesisInput
            label="Libele"
            :violation="activiteViolations ? activiteViolations['libele'] : undefined"
            placeholder="Enter Libele"
            type="text"
            v-model="formModel.libele"
            :value="formModel.libele"
          />
          
          <!-- Description -->
          <GenesisInput
            label="Description"
            :violation="activiteViolations ? activiteViolations['description'] : undefined"
            placeholder="Enter Description"
            type="text"
            v-model="formModel.description"
            :value="formModel.description"
          />
          
        </div>
        <ActivitesMembreTableForm
          :initial-data="activitesMembresData"
          :get-table-data-callback="setActivitesMembres"
          :violations="activitesMembreViolations"
          parentClassName="Activite"
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
import { Activite, ActiviteFormDTO } from '@/models/ActiviteModel.ts'
import GenesisButton from '@/components/button/GenesisButton.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import{ ActivitesMembreFormDTO, type ActivitesMembre } from '@/models/ActivitesMembreModel.ts'
import ActivitesMembreTableForm from '@/entities/activitesMembre/ActivitesMembreTableForm.vue'



const props = defineProps<{
  activite?: Activite
  
  activitesMembresData?: ActivitesMembre[]
  
  violations?:{
    activite: Record<string, string>
    activitesMembres: Record<string, string>[]
    
  }
  
  submitLabel?: string
}>()

const emit = defineEmits<{
  (e: 'submit', payload: { activite: Partial<ActiviteFormDTO>; activitesMembres: ActivitesMembreFormDTO[] }): void
  (e: 'cancel', payload: Partial<ActiviteFormDTO>): void
}>()

const activiteViolations = computed(() => props.violations ? props.violations.activite : {})
const activitesMembreViolations = computed(() => props.violations ? props.violations.activitesMembres : [])



const formModel = ref<Partial<ActiviteFormDTO>>({ ...ActiviteFormDTO.parse(props.activite) })



const activitesMembres =ref<ActivitesMembreFormDTO[]>([])
const setActivitesMembres = (data: ActivitesMembreFormDTO[]) => {
  activitesMembres.value = data
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
  
  emit('submit', { activite: data, activitesMembres: activitesMembres.value })
  
}

function cancelForm() {
  emit('cancel', formModel.value)
}
onMounted(async () => {
  if (Array.isArray(props.activitesMembresData) && props.activitesMembresData.length > 0) {
    setActivitesMembres(ActivitesMembreFormDTO.parseList(props.activitesMembresData))
  }
})

</script>
<style scoped></style>
