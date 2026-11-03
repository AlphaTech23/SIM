<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="choixHoraireDemandeur">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="choixHoraireDemandeurId" class="block text-sm font-medium mb-1">Id</label>
            <div id="choixHoraireDemandeurId" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ choixHoraireDemandeur.id ? $n(choixHoraireDemandeur.id, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="choixHoraireDemandeurIdrendezvousRendezVous" class="block text-sm font-medium mb-1">Rendez vou</label>
            <div id="choixHoraireDemandeurIdrendezvousRendezVous" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ choixHoraireDemandeur.idrendezvousRendezVous?.getKeyValue() ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="choixHoraireDemandeurDateChoix" class="block text-sm font-medium mb-1">Date choix</label>
            <div id="choixHoraireDemandeurDateChoix" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ choixHoraireDemandeur.dateChoix ?? '' }}</span>
              
            </div>
          </div>
        </div>
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton @click="goToUpdateFormView(choixHoraireDemandeur)" class="btn btn-outline btn-neutral">
            <EditIcon class="mr-2" />
            <span>{{ $t('button.update') }}</span>
          </GenesisButton>
          <GenesisButton 
            @click="openDeletePopup(choixHoraireDemandeur)" 
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
    :message="`Êtes-vous sûr de vouloir supprimer ChoixHoraireDemandeur choixHoraireDemandeur?.id ?`"
    subMessage="Cette action est irréversible."
    @confirm="confirmDelete"
    @cancel="closePopup"
  />
  
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { ChoixHoraireDemandeur } from '@/models/ChoixHoraireDemandeurModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { usePopup } from '@/composables/usePopup'
import { useChoixHoraireDemandeurs } from '@/composables/useChoixHoraireDemandeurs'
import DeleteConfirmationPopup from '@/components/popup/DeleteConfirmationPopup.vue'
import TrashIcon from '@/components/icons/TrashIcon.vue'
import EditIcon from '@/components/icons/EditIcon.vue'
import { useDateFormat } from '@/composables/useDateFormat'




const props = defineProps({
  choixHoraireDemandeur: {
    type: Object as PropType<ChoixHoraireDemandeur>,
    required: true,
  },
})
// Stores
const { closePopup, openPopup, visible: deletePopup } = usePopup(false)
const { deleteChoixHoraireDemandeur, goToListView, goToUpdateFormView } = useChoixHoraireDemandeurs()

// Methods
const openDeletePopup = (entity: ChoixHoraireDemandeur) => {
  if (!entity) return
  openPopup()
}

const confirmDelete = async () => {
  const { success, error } = await deleteChoixHoraireDemandeur(props.choixHoraireDemandeur)
  if (!success) console.error(error)
  closePopup()
  goToListView()
}

const { formatDate } = useDateFormat();



</script>
<style scoped></style>
