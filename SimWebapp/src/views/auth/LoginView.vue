<template>
  <div class="h-screen flex items-center justify-center bg-base-100">
    <div class="w-full max-w-md p-8 bg-base-100 rounded-2xl shadow-xl">
      <div class="text-center mb-6">
        <AppLogo size="150" />
      </div>

      <LoginForm />

      <div class="mt-6 text-center">
        <hr class="my-4 border-base-200" />
        <p class="text-sm text-base-content">
          {{ $t('authentication.dontHaveAccount') }}
          <RouterLink to="/auth/register" class="text-primary font-medium hover:underline ml-1">
            {{ $t('authentication.createAccount') }}
          </RouterLink>
        </p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import LoginForm from '@/components/auth/LoginForm.vue'
import AppLogo from '@/components/AppLogo.vue'
</script>
