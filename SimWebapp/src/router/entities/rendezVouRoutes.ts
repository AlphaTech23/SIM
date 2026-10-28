import type { RouteRecordRaw } from 'vue-router'

const rendezVouRoutes: Array<RouteRecordRaw> = [
  {
    path: 'rendezVous',
    name: 'rendezVoulistview',
    component: () => import('@/views/rendezVou/RendezVouListView.vue'),
    
  },
  {
    path: 'rendezVous/:id',
    name: 'rendezVoudetailsview',
    component: () => import('@/views/rendezVou/RendezVouDetailsView.vue'),
    
  },
  {
    path: 'rendezVous/update/:id',
    name: 'rendezVouupdateview',
    component: () => import('@/views/rendezVou/RendezVouUpdateView.vue'),
  },
  {
    path: 'rendezVous/create',
    name: 'rendezVoucreateview',
    component: () => import('@/views/rendezVou/RendezVouCreateView.vue'),
  },
]

export default rendezVouRoutes
