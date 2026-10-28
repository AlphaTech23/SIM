import type { RouteRecordRaw } from 'vue-router'

const statutRendezVouRoutes: Array<RouteRecordRaw> = [
  {
    path: 'statutRendezVous',
    name: 'statutRendezVoulistview',
    component: () => import('@/views/statutRendezVou/StatutRendezVouListView.vue'),
    
  },
  {
    path: 'statutRendezVous/:id',
    name: 'statutRendezVoudetailsview',
    component: () => import('@/views/statutRendezVou/StatutRendezVouDetailsView.vue'),
    
  },
  {
    path: 'statutRendezVous/update/:id',
    name: 'statutRendezVouupdateview',
    component: () => import('@/views/statutRendezVou/StatutRendezVouUpdateView.vue'),
  },
  {
    path: 'statutRendezVous/create',
    name: 'statutRendezVoucreateview',
    component: () => import('@/views/statutRendezVou/StatutRendezVouCreateView.vue'),
  },
]

export default statutRendezVouRoutes
