<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <form @submit.prevent="handleSubmit" class="grid grid-cols-1 w-7/12 gap-4 mx-auto">
      <!-- <form @submit.prevent="handleSubmit" class=""> -->
        <div class="grid grid-cols-1 gap-4">
          <!-- Libele -->
          <GenesisInput
            label="Libele"
            :violation="statutViolations ? statutViolations['libele'] : undefined"
            placeholder="Enter Libele"
            type="text"
            v-model="formModel.libele"
            :value="formModel.libele"
          />
          
        </div>
        <StatutRendezVouTableForm
          :initial-data="statutRendezVousData"
          :get-table-data-callback="setStatutRendezVous"
          :violations="statutRendezVouViolations"
          parentClassName="Statut"
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
import { Statut, StatutFormDTO } from '@/models/StatutModel.ts'
import GenesisButton from '@/components/button/GenesisButton.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import{ StatutRendezVouFormDTO, type StatutRendezVou } from '@/models/StatutRendezVouModel.ts'
import StatutRendezVouTableForm from '@/entities/statutRendezVou/StatutRendezVouTableForm.vue'



const props = defineProps<{
  statut?: Statut
  
  statutRendezVousData?: StatutRendezVou[]
  
  violations?:{
    statut: Record<string, string>
    statutRendezVous: Record<string, string>[]
    
  }
  
  submitLabel?: string
}>()

const emit = defineEmits<{
  (e: 'submit', payload: { statut: Partial<StatutFormDTO>; statutRendezVous: StatutRendezVouFormDTO[] }): void
  (e: 'cancel', payload: Partial<StatutFormDTO>): void
}>()

const statutViolations = computed(() => props.violations ? props.violations.statut : {})
const statutRendezVouViolations = computed(() => props.violations ? props.violations.statutRendezVous : [])



const formModel = ref<Partial<StatutFormDTO>>({ ...StatutFormDTO.parse(props.statut) })



const statutRendezVous =ref<StatutRendezVouFormDTO[]>([])
const setStatutRendezVous = (data: StatutRendezVouFormDTO[]) => {
  statutRendezVous.value = data
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
  
  emit('submit', { statut: data, statutRendezVous: statutRendezVous.value })
  
}

function cancelForm() {
  emit('cancel', formModel.value)
}
onMounted(async () => {
  if (Array.isArray(props.statutRendezVousData) && props.statutRendezVousData.length > 0) {
    setStatutRendezVous(StatutRendezVouFormDTO.parseList(props.statutRendezVousData))
  }
})

</script>
<style scoped></style>
