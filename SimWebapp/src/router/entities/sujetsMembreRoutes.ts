import type { RouteRecordRaw } from 'vue-router'

const sujetsMembreRoutes: Array<RouteRecordRaw> = [
  {
    path: 'sujetsMembres',
    name: 'sujetsMembrelistview',
    component: () => import('@/views/sujetsMembre/SujetsMembreListView.vue'),
    
  },
  {
    path: 'sujetsMembres/:id',
    name: 'sujetsMembredetailsview',
    component: () => import('@/views/sujetsMembre/SujetsMembreDetailsView.vue'),
    
  },
  {
    path: 'sujetsMembres/update/:id',
    name: 'sujetsMembreupdateview',
    component: () => import('@/views/sujetsMembre/SujetsMembreUpdateView.vue'),
  },
  {
    path: 'sujetsMembres/create',
    name: 'sujetsMembrecreateview',
    component: () => import('@/views/sujetsMembre/SujetsMembreCreateView.vue'),
  },
]

export default sujetsMembreRoutes
