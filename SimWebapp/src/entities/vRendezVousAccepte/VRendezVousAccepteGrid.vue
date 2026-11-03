<template>
  <div class="p-4">
    <div v-if="!loading && message" class="text-center py-10 text-gray-500">
      {{ message }}
    </div>
    <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
      <component
        v-for="vRendezVousAccepte in data"
        :key="vRendezVousAccepte.getKeyValue()"
        :is="VRendezVousAccepteCard"
        :vRendezVousAccepte="vRendezVousAccepte"
        v-bind="itemProps"
        @request-view="$emit('request-view', $event)"
        @request-delete="$emit('request-delete', $event)"
        @request-update="$emit('request-update', $event)"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import VRendezVousAccepteCard from './VRendezVousAccepteCard.vue'
import { VRendezVousAccepte } from '@/models/VRendezVousAccepteModel'
import { computed, type PropType } from 'vue'

const props = defineProps({
  data: { type: Array as PropType<VRendezVousAccepte[]>, required: true },
  message: String,
  loading: { type: Boolean, default: false },
  viewAction: { type: Boolean, default: true },
  editAction: { type: Boolean, default: true },
  removeAction: { type: Boolean, default: true },
  visibleFields: { type: Array as PropType<string[]>, default: () => [] },
})

defineEmits<{
  (e: 'request-view', vRendezVousAccepte: VRendezVousAccepte): void
  (e: 'request-delete', vRendezVousAccepte: VRendezVousAccepte): void
  (e: 'request-update', vRendezVousAccepte: VRendezVousAccepte): void
}>()

const itemProps = computed(() => ({
  visibleFields: props.visibleFields,
  viewAction: props.viewAction,
  editAction: props.editAction,
  removeAction: props.removeAction,
}))

</script>
<style scoped></style>
