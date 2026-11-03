<template>
  <GenesisButton class="btn-ghost border-0" @click="logout">
    <LogoutIcon />
  </GenesisButton>
</template>
<script setup lang="ts">
import LogoutIcon from '@/components/icons/LogoutIcon.vue'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { useAuth } from '@/composables/useAuth.ts'

const auth = useAuth()
const logout = () => {
  auth.logout()
}
</script>
