<template>
  <div class="p-4">
    <div v-if="!loading && message" class="text-center py-10 text-gray-500">
      {{ message }}
    </div>
    <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
      <component
        v-for="vRendezVousEnAttente in data"
        :key="vRendezVousEnAttente.getKeyValue()"
        :is="VRendezVousEnAttenteCard"
        :vRendezVousEnAttente="vRendezVousEnAttente"
        v-bind="itemProps"
        @request-view="$emit('request-view', $event)"
        @request-delete="$emit('request-delete', $event)"
        @request-update="$emit('request-update', $event)"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import VRendezVousEnAttenteCard from './VRendezVousEnAttenteCard.vue'
import { VRendezVousEnAttente } from '@/models/VRendezVousEnAttenteModel'
import { computed, type PropType } from 'vue'

const props = defineProps({
  data: { type: Array as PropType<VRendezVousEnAttente[]>, required: true },
  message: String,
  loading: { type: Boolean, default: false },
  viewAction: { type: Boolean, default: true },
  editAction: { type: Boolean, default: true },
  removeAction: { type: Boolean, default: true },
  visibleFields: { type: Array as PropType<string[]>, default: () => [] },
})

defineEmits<{
  (e: 'request-view', vRendezVousEnAttente: VRendezVousEnAttente): void
  (e: 'request-delete', vRendezVousEnAttente: VRendezVousEnAttente): void
  (e: 'request-update', vRendezVousEnAttente: VRendezVousEnAttente): void
}>()

const itemProps = computed(() => ({
  visibleFields: props.visibleFields,
  viewAction: props.viewAction,
  editAction: props.editAction,
  removeAction: props.removeAction,
}))

</script>
<style scoped></style>
