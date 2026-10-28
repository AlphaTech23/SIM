<template>
  <tr class="hover:bg-base-200">
    <template v-for="field in SujetsMembre.getAllSearchFieldsMetadata()" :key="field.key">
      <td
        v-show="field.showInTable && visibleFields.includes(field.key)"
        class="px-3 py-2"
        :class="{
          'sticky left-0 bg-base-200 font-semibold': field.identifier,
        }"
      >
        <template v-if="field.type === 'date'">
          {{ formatDate(sujetsMembreData[field.key]) }}
        </template>

        <template v-else-if="field.type === 'number'">
          {{ sujetsMembreData[field.key] != null ? $n(sujetsMembreData[field.key], 'decimal') : '--' }}
        </template>

        <template v-else-if="field.type === 'select'">
          {{ sujetsMembreData[field.key] != null ? sujetsMembreData[field.key].getKeyValue() : '--' }}
        </template>

        <template v-else-if="field.type === 'file'">
          <div v-if="sujetsMembreData[field.key]" class="flex max-w-64 items-center gap-2">
            <button v-if="isImageContent(sujetsMembreData[field.key])" type="button" class="shrink-0 overflow-hidden rounded-md focus-visible:outline focus-visible:outline-2 focus-visible:outline-primary" title="Prévisualiser l'image" @click="openImagePreview(buildFileSource(sujetsMembreData[field.key]), field.label)"><img :src="buildFileSource(sujetsMembreData[field.key])" :alt="field.label" class="h-10 w-10 object-cover" /></button>
            <span v-else class="grid h-9 w-9 shrink-0 place-items-center rounded-md bg-base-200 text-[9px] font-bold text-base-content/70" aria-hidden="true">{{ getFileTypeLabel(sujetsMembreData[field.key], getGeneratedFileName(sujetsMembreData[field.key], field.key)) }}</span>
            <span class="min-w-0 flex-1 text-[10px] text-base-content/60">{{ getFileSize(sujetsMembreData[field.key]) }}</span>
            <button type="button" class="btn btn-ghost btn-xs shrink-0" title="Télécharger le fichier" aria-label="Télécharger le fichier" @click.stop="downloadAsset(sujetsMembreData[field.key], getGeneratedFileName(sujetsMembreData[field.key], field.key))">{{ downloadedFile === getGeneratedFileName(sujetsMembreData[field.key], field.key) ? 'Downloading...' : 'Download'  }}</button>
          </div>
          <span v-else>--</span>
        </template>

        <template v-else>
          {{ sujetsMembreData[field.key] ?? '--' }}
        </template>
      </td>
    </template>

    <!-- Actions -->
    <td class="px-3 py-2 bg-base-200 sticky right-0 text-end">
      <div class="rounded-md inline-flex">
        <EntityRowActions
          :view="viewAction"
          :remove="removeAction"
          :edit="editAction"
          @delete-row="deleteRow" 
          @view-row="viewRow" 
          @edit-row="updateRow" />
      </div>
    </td>
  </tr>
  
</template>

<script setup lang="ts">
import { computed, type PropType } from 'vue'
import { SujetsMembre } from '@/models/SujetsMembreModel'
import EntityRowActions from '@/components/table/EntityRowActions.vue'
import { useDateFormat } from '@/composables/useDateFormat'


const props = defineProps({
  sujetsMembre: {
    type: Object as PropType<SujetsMembre>,
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

const sujetsMembreData = computed(() => {
  return props.sujetsMembre as Record<string, any>
})

const emit = defineEmits<{
  (e: 'request-delete', sujetsMembre: SujetsMembre): void
  (e: 'request-view', sujetsMembre: SujetsMembre): void
  (e: 'request-update', sujetsMembre: SujetsMembre): void
}>()

const deleteRow = () => emit('request-delete', props.sujetsMembre)
const viewRow = () => emit('request-view', props.sujetsMembre)
const updateRow = () => emit('request-update', props.sujetsMembre)
const { formatDate } = useDateFormat()


</script>
<style scoped></style>
