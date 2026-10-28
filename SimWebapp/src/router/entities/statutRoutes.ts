import type { RouteRecordRaw } from 'vue-router'

const statutRoutes: Array<RouteRecordRaw> = [
  {
    path: 'statuts',
    name: 'statutlistview',
    component: () => import('@/views/statut/StatutListView.vue'),
    
  },
  {
    path: 'statuts/:id',
    name: 'statutdetailsview',
    component: () => import('@/views/statut/StatutDetailsView.vue'),
    
  },
  {
    path: 'statuts/update/:id',
    name: 'statutupdateview',
    component: () => import('@/views/statut/StatutUpdateView.vue'),
  },
  {
    path: 'statuts/create',
    name: 'statutcreateview',
    component: () => import('@/views/statut/StatutCreateView.vue'),
  },
]

export default statutRoutes
