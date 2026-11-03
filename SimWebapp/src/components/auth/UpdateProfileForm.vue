<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <form @submit.prevent="handleSubmit" class="grid grid-cols-1 w-5/12 gap-4 mx-auto">
        <div class="text-center">
          <ErrorMessage :message="error" />
        </div>

        <div>
          <GenesisInput
            v-model="form.name"
            :value="form.name"
            :violation="nameError"
            :label="$t('authentication.username')"
            :mandatory="true"
            :placeholder="$t('authentication.username')"
          />
        </div>

        <div>
          <GenesisInput
            v-model="form.email"
            :value="form.email"
            :violation="emailError"
            type="email"
            :label="$t('authentication.email')"
            :mandatory="true"
            :placeholder="$t('authentication.email')"
          />
        </div>

        <!-- Action buttons -->
        <div class="flex justify-end gap-3 mt-6">
          <GenesisButton
            type="submit"
            class="btn btn-primary text-primary-content"
            :label="$t('profile.edit')"
            :is-loading="isLoading"
          />
          <GenesisButton
            type="button"
            @click="auth.goToProfileView()"
            class="btn btn-outline btn-error"
            :label="$t('button.cancel')"
          />
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import GenesisInput from '@/components/form/GenesisInput.vue'
import ErrorMessage from '@/components/common/ErrorMessage.vue'
import type { UpdateProfileDTO } from '@/models/auth/UpdateProfileDTO'
import GenesisButton from '@/components/button/GenesisButton.vue'
import { useAuth } from '@/composables/useAuth'

const props = defineProps<{
  initialData?: UpdateProfileDTO
}>()

console.log('Initial Data:', props.initialData)

const form = ref<UpdateProfileDTO>({
  name: props.initialData?.name ?? '',
  email: props.initialData?.email ?? '',
})

const nameError = ref<string | undefined>(undefined)
const emailError = ref<string | undefined>(undefined)
const error = ref<string | undefined>(undefined)
const isLoading = ref(false)

const auth = useAuth()

const handleSubmit = async () => {
  // Clear previous errors
  error.value = undefined
  nameError.value = undefined
  emailError.value = undefined
  isLoading.value = true

  try {
    const response = await auth.updateProfile(form.value)
    if (!response.success) {
      error.value = response.error
      return
    }
    auth.logout()
  } catch (e: unknown) {
    error.value = 'Une erreur inattendue est survenue.'
  } finally {
    isLoading.value = false
  }
}
</script>

<style scoped></style>
