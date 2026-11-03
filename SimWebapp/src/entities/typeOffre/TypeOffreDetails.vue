<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="typeOffre">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="typeOffreId" class="block text-sm font-medium mb-1">Id</label>
            <div id="typeOffreId" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ typeOffre.id ? $n(typeOffre.id, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="typeOffreLibele" class="block text-sm font-medium mb-1">Libele</label>
            <div id="typeOffreLibele" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ typeOffre.libele ?? '' }}</span>
              
            </div>
          </div>
        </div>
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton @click="goToUpdateFormView(typeOffre)" class="btn btn-outline btn-neutral">
            <EditIcon class="mr-2" />
            <span>{{ $t('button.update') }}</span>
          </GenesisButton>
          <GenesisButton 
            @click="openDeletePopup(typeOffre)" 
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
    :message="`Êtes-vous sûr de vouloir supprimer TypeOffre typeOffre?.id ?`"
    subMessage="Cette action est irréversible."
    @confirm="confirmDelete"
    @cancel="closePopup"
  />
  
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { TypeOffre } from '@/models/TypeOffreModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { usePopup } from '@/composables/usePopup'
import { useTypeOffres } from '@/composables/useTypeOffres'
import DeleteConfirmationPopup from '@/components/popup/DeleteConfirmationPopup.vue'
import TrashIcon from '@/components/icons/TrashIcon.vue'
import EditIcon from '@/components/icons/EditIcon.vue'
import { useDateFormat } from '@/composables/useDateFormat'




const props = defineProps({
  typeOffre: {
    type: Object as PropType<TypeOffre>,
    required: true,
  },
})
// Stores
const { closePopup, openPopup, visible: deletePopup } = usePopup(false)
const { deleteTypeOffre, goToListView, goToUpdateFormView } = useTypeOffres()

// Methods
const openDeletePopup = (entity: TypeOffre) => {
  if (!entity) return
  openPopup()
}

const confirmDelete = async () => {
  const { success, error } = await deleteTypeOffre(props.typeOffre)
  if (!success) console.error(error)
  closePopup()
  goToListView()
}

const { formatDate } = useDateFormat();



</script>
<style scoped></style>
