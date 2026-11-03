import type { RouteRecordRaw } from 'vue-router'

const activitesMembreRoutes: Array<RouteRecordRaw> = [
  {
    path: 'activitesMembres',
    name: 'activitesMembrelistview',
    component: () => import('@/views/activitesMembre/ActivitesMembreListView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'activitesMembres/:id',
    name: 'activitesMembredetailsview',
    component: () => import('@/views/activitesMembre/ActivitesMembreDetailsView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'activitesMembres/update/:id',
    name: 'activitesMembreupdateview',
    component: () => import('@/views/activitesMembre/ActivitesMembreUpdateView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'activitesMembres/create',
    name: 'activitesMembrecreateview',
    component: () => import('@/views/activitesMembre/ActivitesMembreCreateView.vue'),
    meta: { requiresAuth: true },
  },
]

export default activitesMembreRoutes
