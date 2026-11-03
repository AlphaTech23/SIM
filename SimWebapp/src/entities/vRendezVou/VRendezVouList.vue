<template>
  <!-- View Switcher : Affiche dynamiquement le composant selon le mode -->
  <component
    :is="currentLayoutComponent"
    :data="data"
    :message="message"
    :loading="loading"
    :visible-fields="visibleFields"
    :view-action="viewAction"
    :edit-action="editAction"
    :remove-action="removeAction"
    @request:refresh="$emit('request:refresh')"
    @sortby="$emit('sortby', $event)"
    @request-view="viewVRendezVou"
  />

  <!-- Delete confirmation popup (Partagé) -->
</template>

<script setup lang="ts">
import VRendezVouTable from './VRendezVouTable.vue'
import VRendezVouGrid from './VRendezVouGrid.vue'
import { useVRendezVous } from '@/composables/useVRendezVous'
import { VRendezVou } from '@/models/VRendezVouModel'
import { ref, computed, type PropType, type Component } from 'vue'
import { SortFieldParameter } from '@/models/api/RequestModel'

const props = defineProps({
  data: { type: Array as PropType<VRendezVou[]>, required: true },
  message: String,
  loading: { type: Boolean, default: false },
  viewAction: { type: Boolean, default: true },
  editAction: { type: Boolean, default: true },
  removeAction: { type: Boolean, default: true },
  visibleFields: { type: Array as PropType<string[]>, default: () => [] },
  layoutMode: { type: String as PropType<'list' | 'card'>, default: 'list' },
})

const emit = defineEmits<{
  (e: 'request:refresh'): void
  (e: 'sortby', sortData: SortFieldParameter): void
}>()

// Ajouter un nouveau mode = ajouter une seule ligne ici
const layoutComponents: Record<string, Component> = {
  list: VRendezVouTable,
  card: VRendezVouGrid,
  // Exemples futurs :
  // kanban: VRendezVouKanban,
  // timeline: VRendezVouTimeline,
}

// Fallback sur VRendezVouTable si le mode n'existe pas (sécurité)
const currentLayoutComponent = computed<Component>(
  () => layoutComponents[props.layoutMode] ?? VRendezVouTable,
)


const { viewVRendezVou } = useVRendezVous()


</script>
<style scoped></style>
