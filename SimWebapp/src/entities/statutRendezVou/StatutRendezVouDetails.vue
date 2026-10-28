<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="statutRendezVou">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="statutRendezVouId" class="block text-sm font-medium mb-1">Id</label>
            <div id="statutRendezVouId" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ statutRendezVou.id ? $n(statutRendezVou.id, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="statutRendezVouIdrendezvousRendezVous" class="block text-sm font-medium mb-1">Rendez vou</label>
            <div id="statutRendezVouIdrendezvousRendezVous" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ statutRendezVou.idrendezvousRendezVous?.getKeyValue() ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="statutRendezVouIdstatutStatut" class="block text-sm font-medium mb-1">Statut</label>
            <div id="statutRendezVouIdstatutStatut" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ statutRendezVou.idstatutStatut?.getKeyValue() ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="statutRendezVouDate" class="block text-sm font-medium mb-1">Date</label>
            <div id="statutRendezVouDate" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ statutRendezVou.date ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="statutRendezVouRemarque" class="block text-sm font-medium mb-1">Remarque</label>
            <div id="statutRendezVouRemarque" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ statutRendezVou.remarque ?? '' }}</span>
              
            </div>
          </div>
        </div>
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton @click="goToUpdateFormView(statutRendezVou)" class="btn btn-outline btn-neutral">
            <EditIcon class="mr-2" />
            <span>{{ $t('button.update') }}</span>
          </GenesisButton>
          <GenesisButton 
            @click="openDeletePopup(statutRendezVou)" 
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
    :message="`Êtes-vous sûr de vouloir supprimer StatutRendezVou statutRendezVou?.id ?`"
    subMessage="Cette action est irréversible."
    @confirm="confirmDelete"
    @cancel="closePopup"
  />
  
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { StatutRendezVou } from '@/models/StatutRendezVouModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { usePopup } from '@/composables/usePopup'
import { useStatutRendezVous } from '@/composables/useStatutRendezVous'
import DeleteConfirmationPopup from '@/components/popup/DeleteConfirmationPopup.vue'
import TrashIcon from '@/components/icons/TrashIcon.vue'
import EditIcon from '@/components/icons/EditIcon.vue'
import { useDateFormat } from '@/composables/useDateFormat'




const props = defineProps({
  statutRendezVou: {
    type: Object as PropType<StatutRendezVou>,
    required: true,
  },
})
// Stores
const { closePopup, openPopup, visible: deletePopup } = usePopup(false)
const { deleteStatutRendezVou, goToListView, goToUpdateFormView } = useStatutRendezVous()

// Methods
const openDeletePopup = (entity: StatutRendezVou) => {
  if (!entity) return
  openPopup()
}

const confirmDelete = async () => {
  const { success, error } = await deleteStatutRendezVou(props.statutRendezVou)
  if (!success) console.error(error)
  closePopup()
  goToListView()
}

const { formatDate } = useDateFormat();



</script>
<style scoped></style>
