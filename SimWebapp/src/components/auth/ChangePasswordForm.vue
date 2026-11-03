<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <form @submit.prevent="handleSubmit" class="grid grid-cols-1 w-5/12 gap-4 mx-auto">
        <div class="text-center">
          <ErrorMessage :message="error" />
        </div>

        <div>
          <PasswordInput
            v-model="form.oldPassword"
            :violation="oldPasswordError"
            :label="$t('profile.oldPassword')"
            :mandatory="true"
          />
        </div>

        <div>
          <PasswordInput
            v-model="form.newPassword"
            :violation="newPasswordError"
            :label="$t('profile.newPassword')"
            :mandatory="true"
          />
        </div>

        <div>
          <PasswordInput
            v-model="form.confirmPassword"
            :violation="confirmPasswordError"
            :label="$t('profile.confirmPassword')"
            :mandatory="true"
            placeholder="••••••••"
          />
        </div>

        <!-- Action buttons -->
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton
            type="submit"
            class="btn btn-primary text-primary-content"
            :label="$t('profile.changePassword')"
            :is-loading="isLoading"
          />
          <GenesisButton
            @click="auth.goToProfileView()"
            class="btn btn-outline btn-error"
            :label="$t('button.cancel')"
          />
        </div>
      </form>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import type { ChangePasswordDTO } from '@/models/auth/ChangePasswordDTO'
import { useAuth } from '@/composables/useAuth.ts'
import { validators } from '@/utils/validators.ts'
import { ChangePasswordValidator } from '@/models/validators/ChangePasswordValidator.ts'
import ErrorMessage from '@/components/common/ErrorMessage.vue'
import PasswordInput from '@/components/form/PasswordInput.vue'
import GenesisButton from '@/components/button/GenesisButton.vue'

defineEmits<{
  (e: 'cancel'): void
}>()
const form = ref<ChangePasswordDTO>({
  oldPassword: '',
  newPassword: '',
  confirmPassword: '',
})

const oldPasswordError = ref<string | undefined>(undefined)
const newPasswordError = ref<string | undefined>(undefined)
const confirmPasswordError = ref<string | undefined>(undefined)
const error = ref<string | undefined>(undefined)
const isLoading = ref(false)

const auth = useAuth()

async function handleSubmit() {
  // Clear previous errors
  error.value = undefined
  oldPasswordError.value = undefined
  newPasswordError.value = undefined
  confirmPasswordError.value = undefined

  let isValid = true

  // 1. Check validation rules
  oldPasswordError.value = validators.checkRules(
    form.value.oldPassword,
    ChangePasswordValidator.oldPasswordRule,
  )
  if (oldPasswordError.value) isValid = false

  newPasswordError.value = validators.checkRules(
    form.value.newPassword,
    ChangePasswordValidator.newPasswordRule,
  )
  if (newPasswordError.value) isValid = false

  // 2. Check confirmation
  if (form.value.newPassword !== form.value.confirmPassword) {
    confirmPasswordError.value =
      'Le mot de passe saisi ne correspondent pas au nouveau mot de passe'
    isValid = false
  } else {
    // Check confirmation rules if they pass the match
    confirmPasswordError.value = validators.checkRules(
      form.value.confirmPassword,
      ChangePasswordValidator.confirmPasswordRules,
    )
    if (confirmPasswordError.value) isValid = false
  }

  if (!isValid) return

  isLoading.value = true

  try {
    const response = await auth.changePassword(form.value)
    if (!response.success) {
      error.value = response.error
      return
    }
  } catch (e: unknown) {
    error.value = 'Une erreur inattendue est survenue.'
  } finally {
    isLoading.value = false
  }
}
</script>

<style scoped></style>
