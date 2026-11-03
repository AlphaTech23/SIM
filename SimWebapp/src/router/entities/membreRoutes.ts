import type { RouteRecordRaw } from 'vue-router'

const membreRoutes: Array<RouteRecordRaw> = [
  {
    path: 'membres',
    name: 'membrelistview',
    component: () => import('@/views/membre/MembreListView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'membres/:id',
    name: 'membredetailsview',
    component: () => import('@/views/membre/MembreDetailsView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'membres/update/:id',
    name: 'membreupdateview',
    component: () => import('@/views/membre/MembreUpdateView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'membres/create',
    name: 'membrecreateview',
    component: () => import('@/views/membre/MembreCreateView.vue'),
    meta: { requiresAuth: true },
  },
]

export default membreRoutes
