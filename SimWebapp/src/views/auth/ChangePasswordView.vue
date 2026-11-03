<template>
  <div class="w-full">
    <div class="flex items-center justify-between mb-6">
      <h3 class="text-xl font-semibold text-base-content">
        {{ $t('entity.nav', { entity: 'Profile' }) }}
        <span class="text-base-content/50 font-normal">{{ $t('profile.changePassword') }}</span>
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
    <ChangePasswordForm @cancel="auth.goToProfileView()" />
  </div>
</template>

<script lang="ts" setup>
import ChangePasswordForm from '@/components/auth/ChangePasswordForm.vue'
import GenesisButton from '@/components/button/GenesisButton.vue'
import LeftArrowIcon from '@/components/icons/LeftArrowIcon.vue'
import { useAuth } from '@/composables/useAuth.ts'

const auth = useAuth()
</script>
