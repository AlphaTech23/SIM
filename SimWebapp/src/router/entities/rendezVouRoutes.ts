import type { RouteRecordRaw } from 'vue-router'

const rendezVouRoutes: Array<RouteRecordRaw> = [
  {
    path: 'rendezVous',
    name: 'rendezVoulistview',
    component: () => import('@/views/rendezVou/RendezVouListView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'rendezVous/:id',
    name: 'rendezVoudetailsview',
    component: () => import('@/views/rendezVou/RendezVouDetailsView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'rendezVous/update/:id',
    name: 'rendezVouupdateview',
    component: () => import('@/views/rendezVou/RendezVouUpdateView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'rendezVous/create',
    name: 'rendezVoucreateview',
    component: () => import('@/views/rendezVou/RendezVouCreateView.vue'),
    meta: { requiresAuth: true },
  },
]

export default rendezVouRoutes
