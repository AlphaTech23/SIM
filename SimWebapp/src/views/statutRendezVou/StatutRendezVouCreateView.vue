<template>
  <div class="w-full">
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'StatutRendezVou' }) }}
        <span class="text-base-content/50 font-normal">{{ $t('entity.create.nav') }}</span>
      </h3>
      <GenesisButton
        :title="$t('button.backToListDescription')"
        @click="goToListView"
        class="btn-secondary"
      >
        <LeftArrowIcon />
        {{ $t('button.backToList') }}
      </GenesisButton>
    </div>

    <statutRendezVou-form
      :submit-label="$t('entity.create.submitLabel', { entity: 'StatutRendezVou' })"
      :violations="violations"
      @submit="createHandler"
      @cancel="goToListView"
    />

    <AlertPopup
      :message="message ?? undefined"
      title="Error"
      :visible="alertPopup"
      @close="closePopup"
    />
  </div>
</template>

<script setup lang="ts">
import StatutRendezVouForm from '@/entities/statutRendezVou/StatutRendezVouForm.vue'
import AlertPopup from '@/components/popup/AlertPopup.vue'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import { StatutRendezVouFormDTO } from '@/models/StatutRendezVouModel'
import { useStatutRendezVous } from '@/composables/useStatutRendezVous'
import { usePopup } from '@/composables/usePopup'
import { useFreezeScreenStore } from '@/stores/useFreezeScreenStore'
import { ref } from 'vue'

const { createStatutRendezVou, goToListView, message, viewStatutRendezVou } = useStatutRendezVous()
const { openPopup, closePopup, visible: alertPopup } = usePopup()
const freezeStore = useFreezeScreenStore()
const violations = ref<Record<string, string> | null>(null)
const createHandler = async (formDTO: Partial<StatutRendezVouFormDTO>) => {
  freezeStore.freeze('Creating a new StatutRendezVou ...')
  try {
    const { data, errors } = await createStatutRendezVou(formDTO)
    if (errors) {
      violations.value = errors
    }
    if (data) {
      viewStatutRendezVou(data)
      return
    }
    throw new Error(
      message.value || 'Creation failed without an error message from the API.',
    )
  } catch (error: unknown) {
    console.error('Error creating statutRendezVou:',error)
    openPopup()
  } finally {
    freezeStore.unfreeze()
  }
}

</script>
<style scoped></style>
