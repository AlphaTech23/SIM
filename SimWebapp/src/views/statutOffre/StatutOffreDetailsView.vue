<template>
  <div class="w-full">
    <!-- Header -->
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'StatutOffre' }) }}
        <span class="text-base-content/50 font-normal">{{ $t('entity.details.nav') }}</span>
      </h3>

      <!-- Back button -->
      <GenesisButton
        :title="$t('button.backToListDescription')"
        @click="goToListView"
        class="btn-secondary"
      >
        <LeftArrowIcon />
        {{ $t('button.backToList') }}
      </GenesisButton>
    </div>
    <!-- Entity details -->
    <StatutOffreDetails v-if="entity" :statutOffre="entity" />
    
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import StatutOffreDetails from '@/entities/statutOffre/StatutOffreDetails.vue'
import { useStatutOffres } from '@/composables/useStatutOffres'
import { StatutOffre } from '@/models/StatutOffreModel'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'

const route = useRoute()
const pathId = Number(route.params.id)
const { getStatutOffreById, goToListView } = useStatutOffres()
const entity = ref<StatutOffre | null>(null)

onMounted(async () => {
  const result = await getStatutOffreById(pathId)
  entity.value = result.data || null
});

</script>
<style scoped></style>
