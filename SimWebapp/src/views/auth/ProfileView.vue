<script setup lang="ts">
import { useAuth } from '@/composables/useAuth.ts'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const auth = useAuth()
const user = auth.getCurrentUser()

const goToUpdateProfile = () => {
  router.push('/auth/update-profile')
}
</script>

<template>
  <div class="flex items-center justify-between mb-6">
    <h2 class="text-xl font-semibold text-base-content">{{ $t('profile.myProfile') }}</h2>
  </div>
  <div class="grid grid-cols-2 gap-4">
    <div class="">
      <label for="userName" class="block text-sm font-medium mb-1">{{ $t('authentication.username') }}</label>
      <div id="userName" class="input w-full bg-base-100 cursor-default">
        <span>{{ user.username ?? '' }}</span>
      </div>
    </div>

    <div class="">
      <label for="userEmail" class="block text-sm font-medium mb-1">{{ $t('authentication.email')}}</label>
      <div id="userEmail" class="input w-full bg-base-100 cursor-default">
        <span>{{ user.email ?? '' }}</span>
      </div>
    </div>
  </div>

  <div class="flex gap-4 mt-4">
    <router-link to="/auth/change-password">
      <GenesisButton :label="$t('profile.changePassword')" />
    </router-link>
    <GenesisButton :label="$t('profile.edit')" @click="goToUpdateProfile" />
  </div>

  <div class="mt-4">
    <div>
      <h3 class="font-semibold">Roles</h3>
    </div>
    <table class="table">
      <thead class="table-header">
        <tr>
          <th>Authority</th>
          <th>Authorizations</th>
          <th>Restrictions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="a in user.authorities" :key="'role-' + a.authority">
          <td>{{ a.authority }}</td>
          <td>*</td>
          <td>None</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped></style>
