<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="plageHoraire">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="plageHoraireId" class="block text-sm font-medium mb-1">Id</label>
            <div id="plageHoraireId" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ plageHoraire.id ? $n(plageHoraire.id, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="plageHoraireIdrendezvousRendezVous" class="block text-sm font-medium mb-1">Rendez vou</label>
            <div id="plageHoraireIdrendezvousRendezVous" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ plageHoraire.idrendezvousRendezVous?.getKeyValue() ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="plageHoraireJourDebut" class="block text-sm font-medium mb-1">Jour debut</label>
            <div id="plageHoraireJourDebut" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ plageHoraire.jourDebut ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="plageHoraireJourFin" class="block text-sm font-medium mb-1">Jour fin</label>
            <div id="plageHoraireJourFin" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ plageHoraire.jourFin ?? '' }}</span>
              
            </div>
          </div>
        </div>
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton @click="goToUpdateFormView(plageHoraire)" class="btn btn-outline btn-neutral">
            <EditIcon class="mr-2" />
            <span>{{ $t('button.update') }}</span>
          </GenesisButton>
          <GenesisButton 
            @click="openDeletePopup(plageHoraire)" 
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
    :message="`Êtes-vous sûr de vouloir supprimer PlageHoraire plageHoraire?.id ?`"
    subMessage="Cette action est irréversible."
    @confirm="confirmDelete"
    @cancel="closePopup"
  />
  
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { PlageHoraire } from '@/models/PlageHoraireModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { usePopup } from '@/composables/usePopup'
import { usePlageHoraires } from '@/composables/usePlageHoraires'
import DeleteConfirmationPopup from '@/components/popup/DeleteConfirmationPopup.vue'
import TrashIcon from '@/components/icons/TrashIcon.vue'
import EditIcon from '@/components/icons/EditIcon.vue'
import { useDateFormat } from '@/composables/useDateFormat'




const props = defineProps({
  plageHoraire: {
    type: Object as PropType<PlageHoraire>,
    required: true,
  },
})
// Stores
const { closePopup, openPopup, visible: deletePopup } = usePopup(false)
const { deletePlageHoraire, goToListView, goToUpdateFormView } = usePlageHoraires()

// Methods
const openDeletePopup = (entity: PlageHoraire) => {
  if (!entity) return
  openPopup()
}

const confirmDelete = async () => {
  const { success, error } = await deletePlageHoraire(props.plageHoraire)
  if (!success) console.error(error)
  closePopup()
  goToListView()
}

const { formatDate } = useDateFormat();



</script>
<style scoped></style>
