<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'VRendezVousRefuse' }) }}
        <span class="text-base-content/50 font-normal"> {{ $t('entity.list.nav') }}</span>
      </h3>
      <div class="flex items-center gap-3">
        <!-- Composant réutilisable pour le basculement de vue -->
        <LayoutSwitcher v-model="layoutMode" />

      </div>
    </div>

    <!-- Table -->
    <EntityTable
      :entity-model="{}"
      :entity-search-fields="searchFields"
      :searchFn="searchVRendezVousRefuses"
      :getPaginationData="getPaginationData"
      :listComponent="VRendezVousRefuseList"
      :entities="vRendezVousRefuses"
      :message="message"
      :visible-fields="visibleListFields"
      :layout-mode="layoutMode"
      @update:visible-fields="handleUpdateVisibleFields"
      :remove-action="false"
      :edit-action="false"
      @export:csv="onExport"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import type { EntitySearchField } from '@/models/EntityModel'
import { VRendezVousRefuse } from '@/models/VRendezVousRefuseModel'
import VRendezVousRefuseList from '@/entities/vRendezVousRefuse/VRendezVousRefuseList.vue'
import EntityTable from '@/components/table/EntityTable.vue'
import LayoutSwitcher from '@/components/button/LayoutSwitcher.vue'
import { useVRendezVousRefuses } from '@/composables/useVRendezVousRefuses'

const {
  vRendezVousRefuses,
  searchVRendezVousRefuses,
  message,
  getPaginationData,
  exportVRendezVousRefusesToCsv,
  visibleListFields,
  loadColumnConfig,
  saveColumnConfig,
  layoutMode
} = useVRendezVousRefuses()

const searchFields = ref<EntitySearchField[]>(VRendezVousRefuse.getAllSearchFieldsMetadata())

const handleUpdateVisibleFields = async (newFields: string[]) => {
  const result = await saveColumnConfig(newFields)
  if (!result.success) {
    console.error('Échec de la sauvegarde de la configuration des colonnes:', result.error)
  }
}

const onExport = async () => {
  await exportVRendezVousRefusesToCsv(vRendezVousRefuses.value)
}

onMounted(() => {
  loadColumnConfig()
})

</script>
<style scoped></style>
