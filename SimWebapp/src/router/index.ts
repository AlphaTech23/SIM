import { createRouter, createWebHistory } from 'vue-router'
import routes from './routes'
import { useFreezeScreenStore } from '@/stores/useFreezeScreenStore'
import { useAuth } from '@/composables/useAuth.ts'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

router.beforeEach((to, from, next) => {
  const loading = useFreezeScreenStore()
  loading.freeze('Loading')
  const auth = useAuth()
  const isAuthenticated = auth.isLoggedIn.value // or check auth.user/token
  if (to.meta.requiresAuth && !isAuthenticated) {
    next({ name: 'login' })
  } else {
    next()
  }
})

router.afterEach(() => {
  const loading = useFreezeScreenStore()
  setTimeout(() => loading.unfreeze(), 150) // small delay to prevent flicker
})

export default router
