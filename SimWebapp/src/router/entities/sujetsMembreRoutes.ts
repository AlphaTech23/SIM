import type { RouteRecordRaw } from 'vue-router'

const sujetsMembreRoutes: Array<RouteRecordRaw> = [
  {
    path: 'sujetsMembres',
    name: 'sujetsMembrelistview',
    component: () => import('@/views/sujetsMembre/SujetsMembreListView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'sujetsMembres/:id',
    name: 'sujetsMembredetailsview',
    component: () => import('@/views/sujetsMembre/SujetsMembreDetailsView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'sujetsMembres/update/:id',
    name: 'sujetsMembreupdateview',
    component: () => import('@/views/sujetsMembre/SujetsMembreUpdateView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'sujetsMembres/create',
    name: 'sujetsMembrecreateview',
    component: () => import('@/views/sujetsMembre/SujetsMembreCreateView.vue'),
    meta: { requiresAuth: true },
  },
]

export default sujetsMembreRoutes
