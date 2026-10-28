<template>
  <div class="w-full bg-base-100 border rounded border-base-300 p-4">
    <h3 class="text-lg font-semibold mb-4 text-gray-700">Activites membre</h3>

    <div class="">
      <table class="table w-full">
        <thead>
          <tr class="text-primary-focus">
            <th class="">#</th>
            <th v-if="!isParentFK('Membre')" class="">Idmembre membres</th>
            <th v-if="!isParentFK('Activite')" class="">Idactivite activites</th>
            <th class=""></th>
          </tr>
        </thead>
        <tbody>
          <template v-for="(row, index) in rows" :key="row._internalId">
            <ActivitesMembreRowForm
              :internalId="index + 1"
              :activitesMembre="row.data"
              :violations="violations ? violations[index] : undefined"
              :parent-class-name="parentClassName"
              @request:remove="removeRowHandler(row._internalId)"
              @udpate:model-value="(rowData) => (row.rowValue = rowData)"
            />
          </template>
        </tbody>
        <tfoot>
          <tr></tr>
        </tfoot>
      </table>
    </div>
    <div class="mt-4 gap-2 flex justify-end">
      <div class="w-25">
        <GenesisInput min="1" type="number" v-model="rowToAdd" :value="rowToAdd" />
      </div>
      <GenesisAddButton type="button" @click="addRowHandler"><PlusIcon /></GenesisAddButton>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from 'vue'
import { useTableForm } from '@/composables/useTableForm'
import { ActivitesMembre, ActivitesMembreFormDTO } from '@/models/ActivitesMembreModel'
import GenesisAddButton from '@/components/button/GenesisAddButton.vue'
import ActivitesMembreRowForm from '@/entities/activitesMembre/ActivitesMembreRowForm.vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import PlusIcon from '@/components/icons/PlusIcon.vue'

const rowToAdd = ref(10)
const props = defineProps<{
  getTableDataCallback: (data: ActivitesMembreFormDTO[]) => void
  initialData?: ActivitesMembre[]
  violations?: Record<string, string>[]
  parentClassName?: string
}>()

// Masque la FK correspondant au parent courant (résolu à l'exécution via parentClassName)
const isParentFK = (fkType: string) => !!props.parentClassName && fkType === props.parentClassName

const { rows, addRow, removeRow, getTableData } = useTableForm<ActivitesMembre, ActivitesMembreFormDTO>(
  props.initialData,
)

const addRowHandler = () => {
  addRow(rowToAdd.value)
  props.getTableDataCallback(getTableData())
}

const removeRowHandler = (internalId: number) => {
  removeRow(internalId)
  props.getTableDataCallback(getTableData())
}

onMounted(() => {
  props.getTableDataCallback(getTableData())
})
watch(
  () => rows.value,
  () => {
    props.getTableDataCallback(getTableData())
  },
  { deep: true },
)
</script>

<style scoped>
.table :where(th, td) {
  padding: 0.5rem 0.5rem;
}
</style>
