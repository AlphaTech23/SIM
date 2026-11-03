<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'VRendezVousAccepte' }) }}
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
      :searchFn="searchVRendezVousAcceptes"
      :getPaginationData="getPaginationData"
      :listComponent="VRendezVousAccepteList"
      :entities="vRendezVousAcceptes"
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
import { VRendezVousAccepte } from '@/models/VRendezVousAccepteModel'
import VRendezVousAccepteList from '@/entities/vRendezVousAccepte/VRendezVousAccepteList.vue'
import EntityTable from '@/components/table/EntityTable.vue'
import LayoutSwitcher from '@/components/button/LayoutSwitcher.vue'
import { useVRendezVousAcceptes } from '@/composables/useVRendezVousAcceptes'

const {
  vRendezVousAcceptes,
  searchVRendezVousAcceptes,
  message,
  getPaginationData,
  exportVRendezVousAcceptesToCsv,
  visibleListFields,
  loadColumnConfig,
  saveColumnConfig,
  layoutMode
} = useVRendezVousAcceptes()

const searchFields = ref<EntitySearchField[]>(VRendezVousAccepte.getAllSearchFieldsMetadata())

const handleUpdateVisibleFields = async (newFields: string[]) => {
  const result = await saveColumnConfig(newFields)
  if (!result.success) {
    console.error('Échec de la sauvegarde de la configuration des colonnes:', result.error)
  }
}

const onExport = async () => {
  await exportVRendezVousAcceptesToCsv(vRendezVousAcceptes.value)
}

onMounted(() => {
  loadColumnConfig()
})

</script>
<style scoped></style>
