<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="activitesMembre">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="activitesMembreId" class="block text-sm font-medium mb-1">Id</label>
            <div id="activitesMembreId" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ activitesMembre.id ? $n(activitesMembre.id, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="activitesMembreIdmembreMembres" class="block text-sm font-medium mb-1">Membre</label>
            <div id="activitesMembreIdmembreMembres" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ activitesMembre.idmembreMembres?.getKeyValue() ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="activitesMembreIdactiviteActivites" class="block text-sm font-medium mb-1">Activite</label>
            <div id="activitesMembreIdactiviteActivites" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ activitesMembre.idactiviteActivites?.getKeyValue() ?? '' }}</span>
              
            </div>
          </div>
        </div>
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton @click="goToUpdateFormView(activitesMembre)" class="btn btn-outline btn-neutral">
            <EditIcon class="mr-2" />
            <span>{{ $t('button.update') }}</span>
          </GenesisButton>
          <GenesisButton 
            @click="openDeletePopup(activitesMembre)" 
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
    :message="`Êtes-vous sûr de vouloir supprimer ActivitesMembre activitesMembre?.id ?`"
    subMessage="Cette action est irréversible."
    @confirm="confirmDelete"
    @cancel="closePopup"
  />
  
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { ActivitesMembre } from '@/models/ActivitesMembreModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { usePopup } from '@/composables/usePopup'
import { useActivitesMembres } from '@/composables/useActivitesMembres'
import DeleteConfirmationPopup from '@/components/popup/DeleteConfirmationPopup.vue'
import TrashIcon from '@/components/icons/TrashIcon.vue'
import EditIcon from '@/components/icons/EditIcon.vue'
import { useDateFormat } from '@/composables/useDateFormat'




const props = defineProps({
  activitesMembre: {
    type: Object as PropType<ActivitesMembre>,
    required: true,
  },
})
// Stores
const { closePopup, openPopup, visible: deletePopup } = usePopup(false)
const { deleteActivitesMembre, goToListView, goToUpdateFormView } = useActivitesMembres()

// Methods
const openDeletePopup = (entity: ActivitesMembre) => {
  if (!entity) return
  openPopup()
}

const confirmDelete = async () => {
  const { success, error } = await deleteActivitesMembre(props.activitesMembre)
  if (!success) console.error(error)
  closePopup()
  goToListView()
}

const { formatDate } = useDateFormat();



</script>
<style scoped></style>
