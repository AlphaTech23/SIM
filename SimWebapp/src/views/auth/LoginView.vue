<template>
  <div class="h-screen flex items-center justify-center bg-base-100">
    <div class="w-full max-w-md p-8 bg-base-100 rounded-2xl shadow-xl">
      <div class="text-center mb-6">
        <AppLogo size="150" />
      </div>

      <LoginForm />
    </div>
  </div>
</template>

<script setup lang="ts">
import LoginForm from '@/components/auth/LoginForm.vue'
import AppLogo from '@/components/AppLogo.vue'
</script>
