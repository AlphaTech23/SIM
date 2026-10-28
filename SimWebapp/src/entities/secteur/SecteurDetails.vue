<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="secteur">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="secteurId" class="block text-sm font-medium mb-1">Id</label>
            <div id="secteurId" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ secteur.id ? $n(secteur.id, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="secteurLibele" class="block text-sm font-medium mb-1">Libele</label>
            <div id="secteurLibele" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ secteur.libele ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="secteurDescription" class="block text-sm font-medium mb-1">Description</label>
            <div id="secteurDescription" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ secteur.description ?? '' }}</span>
              
            </div>
          </div>
        </div>
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton @click="goToUpdateFormView(secteur)" class="btn btn-outline btn-neutral">
            <EditIcon class="mr-2" />
            <span>{{ $t('button.update') }}</span>
          </GenesisButton>
          <GenesisButton 
            @click="openDeletePopup(secteur)" 
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
    :message="`Êtes-vous sûr de vouloir supprimer Secteur secteur?.id ?`"
    subMessage="Cette action est irréversible."
    @confirm="confirmDelete"
    @cancel="closePopup"
  />
  
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { Secteur } from '@/models/SecteurModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { usePopup } from '@/composables/usePopup'
import { useSecteurs } from '@/composables/useSecteurs'
import DeleteConfirmationPopup from '@/components/popup/DeleteConfirmationPopup.vue'
import TrashIcon from '@/components/icons/TrashIcon.vue'
import EditIcon from '@/components/icons/EditIcon.vue'
import { useDateFormat } from '@/composables/useDateFormat'




const props = defineProps({
  secteur: {
    type: Object as PropType<Secteur>,
    required: true,
  },
})
// Stores
const { closePopup, openPopup, visible: deletePopup } = usePopup(false)
const { deleteSecteur, goToListView, goToUpdateFormView } = useSecteurs()

// Methods
const openDeletePopup = (entity: Secteur) => {
  if (!entity) return
  openPopup()
}

const confirmDelete = async () => {
  const { success, error } = await deleteSecteur(props.secteur)
  if (!success) console.error(error)
  closePopup()
  goToListView()
}

const { formatDate } = useDateFormat();



</script>
<style scoped></style>
