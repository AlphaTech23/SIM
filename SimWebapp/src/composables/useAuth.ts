import { useRouter } from 'vue-router'
import * as authService from '@/services/AuthService.ts'
import { useAuthenticationStore } from '@/stores/useAuthenticationStore.ts'
import { computed } from 'vue'
import type { ChangePasswordDTO } from '@/models/auth/ChangePasswordDTO.ts'
import type { UpdateProfileDTO } from '@/models/auth/UpdateProfileDTO.ts'

export function useAuth() {
  const router = useRouter()
  const goToLoginView = () => {
    router.push({ path: '/auth/login' })
  }

  const goToProfileView = () => {
      router.push({ path: '/profile' })
  }

  const loginUser = async (token: string) => {
    useAuthenticationStore().setToken(token)
    const responseData = await authService.currentUser()
    if (responseData.success && responseData.data) {
      useAuthenticationStore().setCurrentUser(responseData.data)
    } else {
      throw new Error('Authentcation succed but the token is invalid')
    }
  }

  const isLoggedIn = computed(() => {
    const token = useAuthenticationStore().getToken()
    return token != null && token.length > 0
  })

  const getAuthorizationHeader = () => {
    if (
      useAuthenticationStore().getToken() == null ||
      useAuthenticationStore().getToken().length == 0
    )
      return {}
    return {
      Authorization: 'Bearer ' + useAuthenticationStore().getToken(),
    }
  }

  const getCurrentUser = () => {
    const user = useAuthenticationStore().getCurrentUser()
    return user
  }

  const logout = async () => {
    const responseData = await authService.logout()
    if (!responseData.success) {
      console.error('Logout failed:', responseData.error)
    }
    useAuthenticationStore().clear()
    goToLoginView()
  }

  const changePassword = async (data: ChangePasswordDTO) => {
    const responseData = await authService.changePassword(data)
    return responseData
  }

  const updateProfile = async (data: UpdateProfileDTO) => {
    const responseData = await authService.updateProfile(data)
    if (responseData.success && responseData.data) {
    useAuthenticationStore().setCurrentUser(responseData.data)
    }
    return responseData
  }

  return {
    goToLoginView,
    goToProfileView,
    loginUser,
    getAuthorizationHeader,
    getCurrentUser,
    logout,
    isLoggedIn,
    changePassword,
    updateProfile,
  }
}
