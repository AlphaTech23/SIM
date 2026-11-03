<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="offre">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="offreId" class="block text-sm font-medium mb-1">Id</label>
            <div id="offreId" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ offre.id ? $n(offre.id, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="offreIdtypeoffreTypeOffres" class="block text-sm font-medium mb-1">Type offre</label>
            <div id="offreIdtypeoffreTypeOffres" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ offre.idtypeoffreTypeOffres?.getKeyValue() ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="offreIdmembreMembres" class="block text-sm font-medium mb-1">Membre</label>
            <div id="offreIdmembreMembres" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ offre.idmembreMembres?.getKeyValue() ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="offreTitreOffre" class="block text-sm font-medium mb-1">Titre offre</label>
            <div id="offreTitreOffre" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ offre.titreOffre ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="offreDescriptions" class="block text-sm font-medium mb-1">Descriptions</label>
            <div id="offreDescriptions" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ offre.descriptions ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="offreConditions" class="block text-sm font-medium mb-1">Conditions</label>
            <div id="offreConditions" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ offre.conditions ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="offreDateCreation" class="block text-sm font-medium mb-1">Date creation</label>
            <div id="offreDateCreation" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ offre.dateCreation ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="offreDateFinValidite" class="block text-sm font-medium mb-1">Date fin validite</label>
            <div id="offreDateFinValidite" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ offre.dateFinValidite ? $d(offre.dateFinValidite, 'long') : '--/--/--' }}</span>
              
            </div>
          </div>
        </div>
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton @click="goToUpdateFormView(offre)" class="btn btn-outline btn-neutral">
            <EditIcon class="mr-2" />
            <span>{{ $t('button.update') }}</span>
          </GenesisButton>
          <GenesisButton 
            @click="openDeletePopup(offre)" 
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
    :message="`Êtes-vous sûr de vouloir supprimer Offre offre?.id ?`"
    subMessage="Cette action est irréversible."
    @confirm="confirmDelete"
    @cancel="closePopup"
  />
  
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { Offre } from '@/models/OffreModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { usePopup } from '@/composables/usePopup'
import { useOffres } from '@/composables/useOffres'
import DeleteConfirmationPopup from '@/components/popup/DeleteConfirmationPopup.vue'
import TrashIcon from '@/components/icons/TrashIcon.vue'
import EditIcon from '@/components/icons/EditIcon.vue'
import { useDateFormat } from '@/composables/useDateFormat'




const props = defineProps({
  offre: {
    type: Object as PropType<Offre>,
    required: true,
  },
})
// Stores
const { closePopup, openPopup, visible: deletePopup } = usePopup(false)
const { deleteOffre, goToListView, goToUpdateFormView } = useOffres()

// Methods
const openDeletePopup = (entity: Offre) => {
  if (!entity) return
  openPopup()
}

const confirmDelete = async () => {
  const { success, error } = await deleteOffre(props.offre)
  if (!success) console.error(error)
  closePopup()
  goToListView()
}

const { formatDate } = useDateFormat();



</script>
<style scoped></style>
