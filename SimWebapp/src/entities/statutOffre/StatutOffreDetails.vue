<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="statutOffre">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="statutOffreId" class="block text-sm font-medium mb-1">Id</label>
            <div id="statutOffreId" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ statutOffre.id ? $n(statutOffre.id, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="statutOffreLibele" class="block text-sm font-medium mb-1">Libele</label>
            <div id="statutOffreLibele" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ statutOffre.libele ?? '' }}</span>
              
            </div>
          </div>
        </div>
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton @click="goToUpdateFormView(statutOffre)" class="btn btn-outline btn-neutral">
            <EditIcon class="mr-2" />
            <span>{{ $t('button.update') }}</span>
          </GenesisButton>
          <GenesisButton 
            @click="openDeletePopup(statutOffre)" 
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
    :message="`Êtes-vous sûr de vouloir supprimer StatutOffre statutOffre?.id ?`"
    subMessage="Cette action est irréversible."
    @confirm="confirmDelete"
    @cancel="closePopup"
  />
  
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { StatutOffre } from '@/models/StatutOffreModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { usePopup } from '@/composables/usePopup'
import { useStatutOffres } from '@/composables/useStatutOffres'
import DeleteConfirmationPopup from '@/components/popup/DeleteConfirmationPopup.vue'
import TrashIcon from '@/components/icons/TrashIcon.vue'
import EditIcon from '@/components/icons/EditIcon.vue'
import { useDateFormat } from '@/composables/useDateFormat'




const props = defineProps({
  statutOffre: {
    type: Object as PropType<StatutOffre>,
    required: true,
  },
})
// Stores
const { closePopup, openPopup, visible: deletePopup } = usePopup(false)
const { deleteStatutOffre, goToListView, goToUpdateFormView } = useStatutOffres()

// Methods
const openDeletePopup = (entity: StatutOffre) => {
  if (!entity) return
  openPopup()
}

const confirmDelete = async () => {
  const { success, error } = await deleteStatutOffre(props.statutOffre)
  if (!success) console.error(error)
  closePopup()
  goToListView()
}

const { formatDate } = useDateFormat();



</script>
<style scoped></style>
