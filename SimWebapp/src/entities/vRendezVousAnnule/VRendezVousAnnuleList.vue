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
    @request-view="viewVRendezVousAnnule"
  />

  <!-- Delete confirmation popup (Partagé) -->
</template>

<script setup lang="ts">
import VRendezVousAnnuleTable from './VRendezVousAnnuleTable.vue'
import VRendezVousAnnuleGrid from './VRendezVousAnnuleGrid.vue'
import { useVRendezVousAnnules } from '@/composables/useVRendezVousAnnules'
import { VRendezVousAnnule } from '@/models/VRendezVousAnnuleModel'
import { ref, computed, type PropType, type Component } from 'vue'
import { SortFieldParameter } from '@/models/api/RequestModel'

const props = defineProps({
  data: { type: Array as PropType<VRendezVousAnnule[]>, required: true },
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
  list: VRendezVousAnnuleTable,
  card: VRendezVousAnnuleGrid,
  // Exemples futurs :
  // kanban: VRendezVousAnnuleKanban,
  // timeline: VRendezVousAnnuleTimeline,
}

// Fallback sur VRendezVousAnnuleTable si le mode n'existe pas (sécurité)
const currentLayoutComponent = computed<Component>(
  () => layoutComponents[props.layoutMode] ?? VRendezVousAnnuleTable,
)


const { viewVRendezVousAnnule } = useVRendezVousAnnules()


</script>
<style scoped></style>
