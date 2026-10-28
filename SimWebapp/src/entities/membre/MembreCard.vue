<template>
  <div class="card bg-base-100 shadow-md border border-base-300 hover:shadow-lg transition-shadow duration-200">
    <div class="card-body p-4">
      <!-- Titre de la carte -->
      <h2 class="card-title text-base font-bold text-primary mb-2 truncate">
        <span> #{{ membreData['id'] }} - Membre </span>
      </h2>

      <!-- Corps de la carte : affiche UNIQUEMENT les champs visibles -->
      <div class="space-y-1 text-sm flex-1">
        <template v-for="field in Membre.getAllSearchFieldsMetadata()" :key="field.key">
          <div
            v-if="field.showInTable && visibleFields.includes(field.key)"
            class="flex justify-between gap-2 border-b border-base-200 py-1 last:border-0"
          >
            <span class="font-semibold text-base-content/70 text-xs uppercase tracking-wide">
              {{ field.label }}
            </span>
            <span class="text-right text-base-content truncate max-w-[60%]">
              <template v-if="field.type === 'date'">
                {{ formatDate(membreData[field.key]) }}
              </template>
              <template v-else-if="field.type === 'number'">
                {{ membreData[field.key] != null ? $n(membreData[field.key], 'decimal') : '--' }}
              </template>
              <template v-else-if="field.type === 'select'">
                {{ membreData[field.key] != null ? membreData[field.key].getKeyValue() : '--' }}
              </template>
              <template v-else-if="field.type === 'file'">
                <div v-if="membreData[field.key]" class="flex w-full min-w-0 items-center gap-2 text-left">
                  <button v-if="isImageContent(membreData[field.key])" type="button" class="shrink-0 overflow-hidden rounded-md focus-visible:outline focus-visible:outline-2 focus-visible:outline-primary" title="Prévisualiser l'image" @click="openImagePreview(buildFileSource(membreData[field.key]), field.label)"><img :src="buildFileSource(membreData[field.key])" :alt="field.label" class="h-10 w-10 object-cover" /></button>
                  <span v-else class="grid h-9 w-9 shrink-0 place-items-center rounded-md bg-base-200 text-[9px] font-bold text-base-content/70" aria-hidden="true">{{ getFileTypeLabel(membreData[field.key], getGeneratedFileName(membreData[field.key], field.key)) }}</span>
                  <span class="min-w-0 flex-1 text-[10px] text-base-content/60">{{ getFileSize(membreData[field.key]) }}</span>
                  <button type="button" class="btn btn-ghost btn-xs shrink-0" title="Télécharger le fichier" aria-label="Télécharger le fichier" @click.stop="downloadAsset(membreData[field.key], getGeneratedFileName(membreData[field.key], field.key))">{{ downloadedFile === getGeneratedFileName(membreData[field.key], field.key) ? 'Downloading...' : 'Download'  }}</button>
                </div>
                <span v-else>--</span>
              </template>
              <template v-else>
                {{ membreData[field.key] ?? '--' }}
              </template>
            </span>
          </div>
        </template>
      </div>

      <!-- Actions en bas de carte -->
      <div class="card-actions justify-end mt-3 pt-2 border-t border-base-200">
        <EntityRowActions
          :view="viewAction"
          :remove="removeAction"
          :edit="editAction"
          @delete-row="deleteRow"
          @view-row="viewRow"
          @edit-row="updateRow"
        />
      </div>
    </div>
  </div>
  
</template>

<script setup lang="ts">
import { computed, type PropType } from 'vue'
import { Membre } from '@/models/MembreModel'
import EntityRowActions from '@/components/table/EntityRowActions.vue'
import { useDateFormat } from '@/composables/useDateFormat'


const props = defineProps({
  membre: {
    type: Object as PropType<Membre>,
    required: true,
  },
  viewAction: {
    type: Boolean,
    default: true,
  },
  editAction: {
    type: Boolean,
    default: true,
  },
  removeAction: {
    type: Boolean,
    default: true,
  },
  visibleFields: {
    type: Array as PropType<string[]>,
    default: () => []
  }
})

const membreData = computed(() => {
  return props.membre as Record<string, any>
})

const emit = defineEmits<{
  (e: 'request-delete', membre: Membre): void
  (e: 'request-view', membre: Membre): void
  (e: 'request-update', membre: Membre): void
}>()

const deleteRow = () => emit('request-delete', props.membre)
const viewRow = () => emit('request-view', props.membre)
const updateRow = () => emit('request-update', props.membre)
const { formatDate } = useDateFormat()


</script>
<style scoped></style>
