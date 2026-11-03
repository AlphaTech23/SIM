<template>
  <div class="w-full">
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Profile' }) }}
        <span class="text-base-content/50 font-normal">{{ $t('profile.edit') }}</span>
      </h3>
      <GenesisButton
        :title="$t('button.backTo', { name: 'Profile' })"
        @click="() => auth.goToProfileView()"
        class="btn-primary"
      >
        <LeftArrowIcon />
        {{ $t('button.backTo', { name: 'Profile' }) }}
      </GenesisButton>
    </div>
    <UpdateProfileForm :initial-data="initialFormData" />
  </div>
</template>

<script setup lang="ts">
import { useAuth } from '@/composables/useAuth'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import UpdateProfileForm from '@/components/auth/UpdateProfileForm.vue'
import type { UpdateProfileDTO } from '@/models/auth/UpdateProfileDTO'

const auth = useAuth()
const currentUser = auth.getCurrentUser()
console.log('Current User:', currentUser)

const initialFormData: UpdateProfileDTO = {
  name: currentUser.username ?? '',
  email: currentUser.email ?? '',
}
</script>
