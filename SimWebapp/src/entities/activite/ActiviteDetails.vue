<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="activite">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="activiteId" class="block text-sm font-medium mb-1">Id</label>
            <div id="activiteId" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ activite.id ? $n(activite.id, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="activiteLibele" class="block text-sm font-medium mb-1">Libele</label>
            <div id="activiteLibele" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ activite.libele ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="activiteDescription" class="block text-sm font-medium mb-1">Description</label>
            <div id="activiteDescription" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ activite.description ?? '' }}</span>
              
            </div>
          </div>
        </div>
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton @click="goToUpdateFormView(activite)" class="btn btn-outline btn-neutral">
            <EditIcon class="mr-2" />
            <span>{{ $t('button.update') }}</span>
          </GenesisButton>
          <GenesisButton 
            @click="openDeletePopup(activite)" 
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
    :message="`Êtes-vous sûr de vouloir supprimer Activite activite?.id ?`"
    subMessage="Cette action est irréversible."
    @confirm="confirmDelete"
    @cancel="closePopup"
  />
  
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { Activite } from '@/models/ActiviteModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { usePopup } from '@/composables/usePopup'
import { useActivites } from '@/composables/useActivites'
import DeleteConfirmationPopup from '@/components/popup/DeleteConfirmationPopup.vue'
import TrashIcon from '@/components/icons/TrashIcon.vue'
import EditIcon from '@/components/icons/EditIcon.vue'
import { useDateFormat } from '@/composables/useDateFormat'




const props = defineProps({
  activite: {
    type: Object as PropType<Activite>,
    required: true,
  },
})
// Stores
const { closePopup, openPopup, visible: deletePopup } = usePopup(false)
const { deleteActivite, goToListView, goToUpdateFormView } = useActivites()

// Methods
const openDeletePopup = (entity: Activite) => {
  if (!entity) return
  openPopup()
}

const confirmDelete = async () => {
  const { success, error } = await deleteActivite(props.activite)
  if (!success) console.error(error)
  closePopup()
  goToListView()
}

const { formatDate } = useDateFormat();



</script>
<style scoped></style>
