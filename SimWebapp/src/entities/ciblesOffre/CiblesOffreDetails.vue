<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="ciblesOffre">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="ciblesOffreId" class="block text-sm font-medium mb-1">Id</label>
            <div id="ciblesOffreId" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ ciblesOffre.id ? $n(ciblesOffre.id, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="ciblesOffreIdoffreOffres" class="block text-sm font-medium mb-1">Offre</label>
            <div id="ciblesOffreIdoffreOffres" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ ciblesOffre.idoffreOffres?.getKeyValue() ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="ciblesOffreIdmembreMembres" class="block text-sm font-medium mb-1">Membre</label>
            <div id="ciblesOffreIdmembreMembres" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ ciblesOffre.idmembreMembres?.getKeyValue() ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="ciblesOffreDateEnvoi" class="block text-sm font-medium mb-1">Date envoi</label>
            <div id="ciblesOffreDateEnvoi" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ ciblesOffre.dateEnvoi ?? '' }}</span>
              
            </div>
          </div>
        </div>
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton @click="goToUpdateFormView(ciblesOffre)" class="btn btn-outline btn-neutral">
            <EditIcon class="mr-2" />
            <span>{{ $t('button.update') }}</span>
          </GenesisButton>
          <GenesisButton 
            @click="openDeletePopup(ciblesOffre)" 
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
    :message="`Êtes-vous sûr de vouloir supprimer CiblesOffre ciblesOffre?.id ?`"
    subMessage="Cette action est irréversible."
    @confirm="confirmDelete"
    @cancel="closePopup"
  />
  
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { CiblesOffre } from '@/models/CiblesOffreModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { usePopup } from '@/composables/usePopup'
import { useCiblesOffres } from '@/composables/useCiblesOffres'
import DeleteConfirmationPopup from '@/components/popup/DeleteConfirmationPopup.vue'
import TrashIcon from '@/components/icons/TrashIcon.vue'
import EditIcon from '@/components/icons/EditIcon.vue'
import { useDateFormat } from '@/composables/useDateFormat'




const props = defineProps({
  ciblesOffre: {
    type: Object as PropType<CiblesOffre>,
    required: true,
  },
})
// Stores
const { closePopup, openPopup, visible: deletePopup } = usePopup(false)
const { deleteCiblesOffre, goToListView, goToUpdateFormView } = useCiblesOffres()

// Methods
const openDeletePopup = (entity: CiblesOffre) => {
  if (!entity) return
  openPopup()
}

const confirmDelete = async () => {
  const { success, error } = await deleteCiblesOffre(props.ciblesOffre)
  if (!success) console.error(error)
  closePopup()
  goToListView()
}

const { formatDate } = useDateFormat();



</script>
<style scoped></style>
