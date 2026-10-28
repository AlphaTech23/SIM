<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="membre">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="membreId" class="block text-sm font-medium mb-1">Id</label>
            <div id="membreId" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ membre.id ? $n(membre.id, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="membreIdsecteurSecteurs" class="block text-sm font-medium mb-1">Secteur</label>
            <div id="membreIdsecteurSecteurs" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ membre.idsecteurSecteurs?.getKeyValue() ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="membreIntitule" class="block text-sm font-medium mb-1">Intitule</label>
            <div id="membreIntitule" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ membre.intitule ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="membreLogo" class="block text-sm font-medium mb-1">Logo</label>
            <div id="membreLogo" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ membre.logo ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="membreTelephone" class="block text-sm font-medium mb-1">Telephone</label>
            <div id="membreTelephone" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ membre.telephone ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="membreEmail" class="block text-sm font-medium mb-1">Email</label>
            <div id="membreEmail" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ membre.email ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="membreWhatsapp" class="block text-sm font-medium mb-1">Whatsapp</label>
            <div id="membreWhatsapp" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ membre.whatsapp ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="membreAdresse" class="block text-sm font-medium mb-1">Adresse</label>
            <div id="membreAdresse" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ membre.adresse ?? '' }}</span>
              
            </div>
          </div>
        </div>
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton @click="goToUpdateFormView(membre)" class="btn btn-outline btn-neutral">
            <EditIcon class="mr-2" />
            <span>{{ $t('button.update') }}</span>
          </GenesisButton>
          <GenesisButton 
            @click="openDeletePopup(membre)" 
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
    :message="`Êtes-vous sûr de vouloir supprimer Membre membre?.id ?`"
    subMessage="Cette action est irréversible."
    @confirm="confirmDelete"
    @cancel="closePopup"
  />
  
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { Membre } from '@/models/MembreModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { usePopup } from '@/composables/usePopup'
import { useMembres } from '@/composables/useMembres'
import DeleteConfirmationPopup from '@/components/popup/DeleteConfirmationPopup.vue'
import TrashIcon from '@/components/icons/TrashIcon.vue'
import EditIcon from '@/components/icons/EditIcon.vue'
import { useDateFormat } from '@/composables/useDateFormat'




const props = defineProps({
  membre: {
    type: Object as PropType<Membre>,
    required: true,
  },
})
// Stores
const { closePopup, openPopup, visible: deletePopup } = usePopup(false)
const { deleteMembre, goToListView, goToUpdateFormView } = useMembres()

// Methods
const openDeletePopup = (entity: Membre) => {
  if (!entity) return
  openPopup()
}

const confirmDelete = async () => {
  const { success, error } = await deleteMembre(props.membre)
  if (!success) console.error(error)
  closePopup()
  goToListView()
}

const { formatDate } = useDateFormat();



</script>
<style scoped></style>
