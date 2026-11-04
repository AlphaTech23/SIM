import type { RouteRecordRaw } from 'vue-router'

const authRoutes: Array<RouteRecordRaw> = [
  {
    path: 'login',
    name: 'login',
    component: () => import('@/views/auth/LoginView.vue'),
    meta: { requiresAuth: false },
  },
  // Inscription retiree de l'interface : la creation de compte se fait
  // uniquement via Swagger ou Postman, par un administrateur deja connecte.
  // Les composants RegisterView/RegisterForm sont conserves si besoin de retour.
]

export default authRoutes
