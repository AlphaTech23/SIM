<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="rendezVou">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="rendezVouId" class="block text-sm font-medium mb-1">Id</label>
            <div id="rendezVouId" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ rendezVou.id ? $n(rendezVou.id, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="rendezVouIddemandeurMembres" class="block text-sm font-medium mb-1">Membre</label>
            <div id="rendezVouIddemandeurMembres" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ rendezVou.iddemandeurMembres?.getKeyValue() ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="rendezVouIdreceveurSujetsMembres" class="block text-sm font-medium mb-1">Sujets membre</label>
            <div id="rendezVouIdreceveurSujetsMembres" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ rendezVou.idreceveurSujetsMembres?.getKeyValue() ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="rendezVouContexte" class="block text-sm font-medium mb-1">Contexte</label>
            <div id="rendezVouContexte" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ rendezVou.contexte ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="rendezVouDateRdv" class="block text-sm font-medium mb-1">Date rdv</label>
            <div id="rendezVouDateRdv" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ rendezVou.dateRdv ?? '' }}</span>
              
            </div>
          </div>
        </div>
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton @click="goToUpdateFormView(rendezVou)" class="btn btn-outline btn-neutral">
            <EditIcon class="mr-2" />
            <span>{{ $t('button.update') }}</span>
          </GenesisButton>
          <GenesisButton 
            @click="openDeletePopup(rendezVou)" 
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
    :message="`Êtes-vous sûr de vouloir supprimer RendezVou rendezVou?.id ?`"
    subMessage="Cette action est irréversible."
    @confirm="confirmDelete"
    @cancel="closePopup"
  />
  
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { RendezVou } from '@/models/RendezVouModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { usePopup } from '@/composables/usePopup'
import { useRendezVous } from '@/composables/useRendezVous'
import DeleteConfirmationPopup from '@/components/popup/DeleteConfirmationPopup.vue'
import TrashIcon from '@/components/icons/TrashIcon.vue'
import EditIcon from '@/components/icons/EditIcon.vue'
import { useDateFormat } from '@/composables/useDateFormat'




const props = defineProps({
  rendezVou: {
    type: Object as PropType<RendezVou>,
    required: true,
  },
})
// Stores
const { closePopup, openPopup, visible: deletePopup } = usePopup(false)
const { deleteRendezVou, goToListView, goToUpdateFormView } = useRendezVous()

// Methods
const openDeletePopup = (entity: RendezVou) => {
  if (!entity) return
  openPopup()
}

const confirmDelete = async () => {
  const { success, error } = await deleteRendezVou(props.rendezVou)
  if (!success) console.error(error)
  closePopup()
  goToListView()
}

const { formatDate } = useDateFormat();



</script>
<style scoped></style>
