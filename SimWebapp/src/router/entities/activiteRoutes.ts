import type { RouteRecordRaw } from 'vue-router'

const activiteRoutes: Array<RouteRecordRaw> = [
  {
    path: 'activites',
    name: 'activitelistview',
    component: () => import('@/views/activite/ActiviteListView.vue'),
    
  },
  {
    path: 'activites/:id',
    name: 'activitedetailsview',
    component: () => import('@/views/activite/ActiviteDetailsView.vue'),
    
  },
  {
    path: 'activites/update/:id',
    name: 'activiteupdateview',
    component: () => import('@/views/activite/ActiviteUpdateView.vue'),
  },
  {
    path: 'activites/create',
    name: 'activitecreateview',
    component: () => import('@/views/activite/ActiviteCreateView.vue'),
  },
]

export default activiteRoutes
