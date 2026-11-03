import type { RouteRecordRaw } from 'vue-router'

const statutRoutes: Array<RouteRecordRaw> = [
  {
    path: 'statuts',
    name: 'statutlistview',
    component: () => import('@/views/statut/StatutListView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'statuts/:id',
    name: 'statutdetailsview',
    component: () => import('@/views/statut/StatutDetailsView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'statuts/update/:id',
    name: 'statutupdateview',
    component: () => import('@/views/statut/StatutUpdateView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'statuts/create',
    name: 'statutcreateview',
    component: () => import('@/views/statut/StatutCreateView.vue'),
    meta: { requiresAuth: true },
  },
]

export default statutRoutes
