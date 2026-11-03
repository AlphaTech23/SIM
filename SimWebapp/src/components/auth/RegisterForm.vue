<template>
  <form class="space-y-4" @submit.prevent="handleRegister">
    <div>
      <ErrorMessage :message="emailError" />
      <GenesisInput
        v-model="email"
        required
        :mandatory="true"
        type="email"
        :label="$t('authentication.email')"
        :placeholder="$t('authentication.email')"
      />
    </div>

    <div>
      <ErrorMessage :message="usernameError" />
      <GenesisInput
        v-model="username"
        required
        :mandatory="true"
        :label="$t('authentication.username')"
        :placeholder="$t('authentication.username')"
        type="text"
      />
    </div>

    <div>
      <ErrorMessage :message="passwordError" />
      <PasswordInput v-model="password" :label="$t('authentication.password')" />
    </div>

    <div>
      <ErrorMessage :message="confirmPasswordError" />
      <PasswordInput v-model="confirmPassword" :label="$t('authentication.confirmPassword')" />
    </div>

    <button type="submit" class="mt-3 btn btn-primary w-full">
      {{ $t('register.submit') }}
    </button>
  </form>
</template>

<script setup lang="ts">
import GenesisInput from '@/components/form/GenesisInput.vue'
import ErrorMessage from '@/components/common/ErrorMessage.vue'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import * as authService from '@/services/AuthService.ts'
import { validators } from '@/utils/validators.ts'
import { RegisterValidator } from '@/models/validators/RegisterValidator.ts'
import type { DataResponse } from '@/models/api/DataResponseModel.ts'
import { RegisterDTO } from '@/models/auth/RegisterDTO.ts'
import PasswordInput from '@/components/form/PasswordInput.vue'

const router = useRouter()

const username = ref('')
const email = ref('')
const password = ref('')
const confirmPassword = ref('')

const usernameError = ref<string | null>(null)
const emailError = ref<string | null>(null)
const passwordError = ref<string | null>(null)
const confirmPasswordError = ref<string | null>(null)

const handleRegister = async () => {
  let valid = true
  usernameError.value = validators.checkRules(username.value, RegisterValidator.usernameRules)
  if (usernameError.value) valid = false

  emailError.value = validators.checkRules(email.value, RegisterValidator.emailRules)
  if (emailError.value) valid = false

  passwordError.value = validators.checkRules(password.value, RegisterValidator.passwordRules)
  if (passwordError.value) valid = false

  if (password.value !== confirmPassword.value) {
    confirmPasswordError.value = 'Passwords do not match'
    valid = false
  } else {
    confirmPasswordError.value = null
  }

  if (!valid) return
  try {
    const registerData: DataResponse<string> = await authService.register(
      new RegisterDTO(username.value, email.value, password.value, 1),
    )
    if (registerData.success) {
      router.push({ name: 'login' })
    } else {
      throw new Error(registerData.error)
    }
  } catch (e: unknown) {
    usernameError.value = String((e as Error).message)
  }
}
</script>
