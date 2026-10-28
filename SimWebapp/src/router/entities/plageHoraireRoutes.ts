import type { RouteRecordRaw } from 'vue-router'

const plageHoraireRoutes: Array<RouteRecordRaw> = [
  {
    path: 'plageHoraires',
    name: 'plageHorairelistview',
    component: () => import('@/views/plageHoraire/PlageHoraireListView.vue'),
    
  },
  {
    path: 'plageHoraires/:id',
    name: 'plageHorairedetailsview',
    component: () => import('@/views/plageHoraire/PlageHoraireDetailsView.vue'),
    
  },
  {
    path: 'plageHoraires/update/:id',
    name: 'plageHoraireupdateview',
    component: () => import('@/views/plageHoraire/PlageHoraireUpdateView.vue'),
  },
  {
    path: 'plageHoraires/create',
    name: 'plageHorairecreateview',
    component: () => import('@/views/plageHoraire/PlageHoraireCreateView.vue'),
  },
]

export default plageHoraireRoutes
