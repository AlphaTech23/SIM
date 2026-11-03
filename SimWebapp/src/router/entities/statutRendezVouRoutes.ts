import type { RouteRecordRaw } from 'vue-router'

const statutRendezVouRoutes: Array<RouteRecordRaw> = [
  {
    path: 'statutRendezVous',
    name: 'statutRendezVoulistview',
    component: () => import('@/views/statutRendezVou/StatutRendezVouListView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'statutRendezVous/:id',
    name: 'statutRendezVoudetailsview',
    component: () => import('@/views/statutRendezVou/StatutRendezVouDetailsView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'statutRendezVous/update/:id',
    name: 'statutRendezVouupdateview',
    component: () => import('@/views/statutRendezVou/StatutRendezVouUpdateView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'statutRendezVous/create',
    name: 'statutRendezVoucreateview',
    component: () => import('@/views/statutRendezVou/StatutRendezVouCreateView.vue'),
    meta: { requiresAuth: true },
  },
]

export default statutRendezVouRoutes
