<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'VRendezVousEnAttente' }) }}
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
      :searchFn="searchVRendezVousEnAttentes"
      :getPaginationData="getPaginationData"
      :listComponent="VRendezVousEnAttenteList"
      :entities="vRendezVousEnAttentes"
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
import { VRendezVousEnAttente } from '@/models/VRendezVousEnAttenteModel'
import VRendezVousEnAttenteList from '@/entities/vRendezVousEnAttente/VRendezVousEnAttenteList.vue'
import EntityTable from '@/components/table/EntityTable.vue'
import LayoutSwitcher from '@/components/button/LayoutSwitcher.vue'
import { useVRendezVousEnAttentes } from '@/composables/useVRendezVousEnAttentes'

const {
  vRendezVousEnAttentes,
  searchVRendezVousEnAttentes,
  message,
  getPaginationData,
  exportVRendezVousEnAttentesToCsv,
  visibleListFields,
  loadColumnConfig,
  saveColumnConfig,
  layoutMode
} = useVRendezVousEnAttentes()

const searchFields = ref<EntitySearchField[]>(VRendezVousEnAttente.getAllSearchFieldsMetadata())

const handleUpdateVisibleFields = async (newFields: string[]) => {
  const result = await saveColumnConfig(newFields)
  if (!result.success) {
    console.error('Échec de la sauvegarde de la configuration des colonnes:', result.error)
  }
}

const onExport = async () => {
  await exportVRendezVousEnAttentesToCsv(vRendezVousEnAttentes.value)
}

onMounted(() => {
  loadColumnConfig()
})

</script>
<style scoped></style>
