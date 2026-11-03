<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="histoOffre">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="histoOffreId" class="block text-sm font-medium mb-1">Id</label>
            <div id="histoOffreId" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ histoOffre.id ? $n(histoOffre.id, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="histoOffreIdoffreOffres" class="block text-sm font-medium mb-1">Offre</label>
            <div id="histoOffreIdoffreOffres" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ histoOffre.idoffreOffres?.getKeyValue() ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="histoOffreIdstatutoffreStatutOffres" class="block text-sm font-medium mb-1">Statut offre</label>
            <div id="histoOffreIdstatutoffreStatutOffres" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ histoOffre.idstatutoffreStatutOffres?.getKeyValue() ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="histoOffreDate" class="block text-sm font-medium mb-1">Date</label>
            <div id="histoOffreDate" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ histoOffre.date ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="histoOffreRemarque" class="block text-sm font-medium mb-1">Remarque</label>
            <div id="histoOffreRemarque" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ histoOffre.remarque ?? '' }}</span>
              
            </div>
          </div>
        </div>
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton @click="goToUpdateFormView(histoOffre)" class="btn btn-outline btn-neutral">
            <EditIcon class="mr-2" />
            <span>{{ $t('button.update') }}</span>
          </GenesisButton>
          <GenesisButton 
            @click="openDeletePopup(histoOffre)" 
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
    :message="`Êtes-vous sûr de vouloir supprimer HistoOffre histoOffre?.id ?`"
    subMessage="Cette action est irréversible."
    @confirm="confirmDelete"
    @cancel="closePopup"
  />
  
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { HistoOffre } from '@/models/HistoOffreModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { usePopup } from '@/composables/usePopup'
import { useHistoOffres } from '@/composables/useHistoOffres'
import DeleteConfirmationPopup from '@/components/popup/DeleteConfirmationPopup.vue'
import TrashIcon from '@/components/icons/TrashIcon.vue'
import EditIcon from '@/components/icons/EditIcon.vue'
import { useDateFormat } from '@/composables/useDateFormat'




const props = defineProps({
  histoOffre: {
    type: Object as PropType<HistoOffre>,
    required: true,
  },
})
// Stores
const { closePopup, openPopup, visible: deletePopup } = usePopup(false)
const { deleteHistoOffre, goToListView, goToUpdateFormView } = useHistoOffres()

// Methods
const openDeletePopup = (entity: HistoOffre) => {
  if (!entity) return
  openPopup()
}

const confirmDelete = async () => {
  const { success, error } = await deleteHistoOffre(props.histoOffre)
  if (!success) console.error(error)
  closePopup()
  goToListView()
}

const { formatDate } = useDateFormat();



</script>
<style scoped></style>
