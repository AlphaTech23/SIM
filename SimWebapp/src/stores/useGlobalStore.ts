import { defineStore } from 'pinia'
import { useLanguageStore } from '@/stores/useLanguageStore.ts'
import { useThemeStore } from '@/stores/useThemeStore.ts'
import { useLayoutStore } from '@/stores/useLayoutStore.ts'
import { onMounted } from 'vue'
import { useAuthenticationStore } from '@/stores/useAuthenticationStore.ts'
import { useAuth } from '@/composables/useAuth.ts'

export const useGlobalStore = defineStore('global', () => {
  useLanguageStore()
  useThemeStore()
  useLayoutStore()
  const authStore = useAuthenticationStore()
  onMounted(async () => {
    const auth = useAuth()
    try {
      if (auth.isLoggedIn.value) {
        const token = authStore.getToken()
        await auth.loginUser(token)
      }
    } catch (e: unknown) {
      await auth.logout()
    }
  })
  return {}
})
