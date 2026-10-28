<template>
  <div class="p-4">
    <div v-if="!loading && message" class="text-center py-10 text-gray-500">
      {{ message }}
    </div>
    <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
      <component
        v-for="secteur in data"
        :key="secteur.getKeyValue()"
        :is="SecteurCard"
        :secteur="secteur"
        v-bind="itemProps"
        @request-view="$emit('request-view', $event)"
        @request-delete="$emit('request-delete', $event)"
        @request-update="$emit('request-update', $event)"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import SecteurCard from './SecteurCard.vue'
import { Secteur } from '@/models/SecteurModel'
import { computed, type PropType } from 'vue'

const props = defineProps({
  data: { type: Array as PropType<Secteur[]>, required: true },
  message: String,
  loading: { type: Boolean, default: false },
  viewAction: { type: Boolean, default: true },
  editAction: { type: Boolean, default: true },
  removeAction: { type: Boolean, default: true },
  visibleFields: { type: Array as PropType<string[]>, default: () => [] },
})

defineEmits<{
  (e: 'request-view', secteur: Secteur): void
  (e: 'request-delete', secteur: Secteur): void
  (e: 'request-update', secteur: Secteur): void
}>()

const itemProps = computed(() => ({
  visibleFields: props.visibleFields,
  viewAction: props.viewAction,
  editAction: props.editAction,
  removeAction: props.removeAction,
}))

</script>
<style scoped></style>
