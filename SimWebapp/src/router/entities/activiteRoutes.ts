import type { RouteRecordRaw } from 'vue-router'

const activiteRoutes: Array<RouteRecordRaw> = [
  {
    path: 'activites',
    name: 'activitelistview',
    component: () => import('@/views/activite/ActiviteListView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'activites/:id',
    name: 'activitedetailsview',
    component: () => import('@/views/activite/ActiviteDetailsView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'activites/update/:id',
    name: 'activiteupdateview',
    component: () => import('@/views/activite/ActiviteUpdateView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'activites/create',
    name: 'activitecreateview',
    component: () => import('@/views/activite/ActiviteCreateView.vue'),
    meta: { requiresAuth: true },
  },
]

export default activiteRoutes
