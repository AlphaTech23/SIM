<template>
  <form class="space-y-4 text-base-content" @submit.prevent="handleLogin">
    <div v-if="isBadCredentials" class="text-center">
      <ErrorMessage :message="$t('authentication.invalid')" />
    </div>
    <div>
      <GenesisInput
        v-model="login"
        required
        :mandatory="true"
        :violation="usernameError"
        :label="$t('authentication.username')"
        placeholder="ex: JohnDoe"
        type="text"
      />
    </div>
    <div>
      <PasswordInput
        v-model="password"
        :violation="passwordError"
        :label="$t('authentication.password')"
      />
    </div>
    <button
      type="submit"
      class="mt-3 btn btn-primary text-primary-content w-full"
      :disabled="isLoading"
    >
      <span v-if="isLoading" class="loading loading-spinner"></span>
      <span v-else>{{ $t('authentication.submit') }}</span>
    </button>
  </form>
</template>

<script setup lang="ts">
import GenesisInput from '@/components/form/GenesisInput.vue'
import { ref } from 'vue'
import * as authService from '@/services/AuthService.ts'
import { LoginValidator } from '@/models/validators/LoginValidator.ts'
import { validators } from '@/utils/validators.ts'
import ErrorMessage from '@/components/common/ErrorMessage.vue'
import type { DataResponse } from '@/models/api/DataResponseModel.ts'
import { useRouter } from 'vue-router'
import { useAuth } from '@/composables/useAuth.ts'
import PasswordInput from '@/components/form/PasswordInput.vue'

const authentication = useAuth()
const router = useRouter()
const login = ref('')
const password = ref('')
const usernameError = ref<string | undefined>('')
const passwordError = ref<string | undefined>('')
const isLoading = ref(false)
const isBadCredentials = ref(false)

const handleLogin = async () => {
  usernameError.value = undefined
  passwordError.value = undefined
  let valid = true
  usernameError.value = validators.checkRules(login.value, LoginValidator.usernameRules)
  if (usernameError.value) valid = false

  passwordError.value = validators.checkRules(password.value, LoginValidator.passwordRules)
  if (passwordError.value) valid = false

  if (!valid) return

  isLoading.value = true

  try {
    const loginData: DataResponse<string> = await authService.login(login.value, password.value)
    if (loginData.success) {
      await authentication.loginUser(loginData.data as string)
      router.push({ name: 'home' })
    } else {
      throw new Error(loginData.error)
    }
  } catch (e: unknown) {
    isBadCredentials.value = true
  } finally {
    isLoading.value = false
  }
}
</script>
