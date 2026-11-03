import type { RouteRecordRaw } from 'vue-router'

const vRendezVouRoutes: Array<RouteRecordRaw> = [
  {
    path: 'vRendezVous',
    name: 'vRendezVoulistview',
    component: () => import('@/views/vRendezVou/VRendezVouListView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'vRendezVous/:id',
    name: 'vRendezVoudetailsview',
    component: () => import('@/views/vRendezVou/VRendezVouDetailsView.vue'),
    meta: { requiresAuth: true },
  },
]

export default vRendezVouRoutes
