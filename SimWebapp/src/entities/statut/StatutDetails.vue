<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="statut">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="statutId" class="block text-sm font-medium mb-1">Id</label>
            <div id="statutId" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ statut.id ? $n(statut.id, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="statutLibele" class="block text-sm font-medium mb-1">Libele</label>
            <div id="statutLibele" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ statut.libele ?? '' }}</span>
              
            </div>
          </div>
        </div>
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton @click="goToUpdateFormView(statut)" class="btn btn-outline btn-neutral">
            <EditIcon class="mr-2" />
            <span>{{ $t('button.update') }}</span>
          </GenesisButton>
          <GenesisButton 
            @click="openDeletePopup(statut)" 
            class="btn-outline btn-error hover:text-white"
          >
            <TrashIcon class="mr-2" />
            <span>{{ $t('button.delete') }}</span>
          </GenesisButton>
        </div>
      </div>
    </div>
  </div>
  <DeleteConfirmationPopup
    :visible="deletePopup"
    :message="`Êtes-vous sûr de vouloir supprimer Statut statut?.id ?`"
    subMessage="Cette action est irréversible."
    @confirm="confirmDelete"
    @cancel="closePopup"
  />
  
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { Statut } from '@/models/StatutModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { usePopup } from '@/composables/usePopup'
import { useStatuts } from '@/composables/useStatuts'
import DeleteConfirmationPopup from '@/components/popup/DeleteConfirmationPopup.vue'
import TrashIcon from '@/components/icons/TrashIcon.vue'
import EditIcon from '@/components/icons/EditIcon.vue'
import { useDateFormat } from '@/composables/useDateFormat'




const props = defineProps({
  statut: {
    type: Object as PropType<Statut>,
    required: true,
  },
})
// Stores
const { closePopup, openPopup, visible: deletePopup } = usePopup(false)
const { deleteStatut, goToListView, goToUpdateFormView } = useStatuts()

// Methods
const openDeletePopup = (entity: Statut) => {
  if (!entity) return
  openPopup()
}

const confirmDelete = async () => {
  const { success, error } = await deleteStatut(props.statut)
  if (!success) console.error(error)
  closePopup()
  goToListView()
}

const { formatDate } = useDateFormat();



</script>
<style scoped></style>
