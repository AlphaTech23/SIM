import type { RouteRecordRaw } from 'vue-router'

const activitesMembreRoutes: Array<RouteRecordRaw> = [
  {
    path: 'activitesMembres',
    name: 'activitesMembrelistview',
    component: () => import('@/views/activitesMembre/ActivitesMembreListView.vue'),
    
  },
  {
    path: 'activitesMembres/:id',
    name: 'activitesMembredetailsview',
    component: () => import('@/views/activitesMembre/ActivitesMembreDetailsView.vue'),
    
  },
  {
    path: 'activitesMembres/update/:id',
    name: 'activitesMembreupdateview',
    component: () => import('@/views/activitesMembre/ActivitesMembreUpdateView.vue'),
  },
  {
    path: 'activitesMembres/create',
    name: 'activitesMembrecreateview',
    component: () => import('@/views/activitesMembre/ActivitesMembreCreateView.vue'),
  },
]

export default activitesMembreRoutes
