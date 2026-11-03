import type { RouteRecordRaw } from 'vue-router'

const histoOffreRoutes: Array<RouteRecordRaw> = [
  {
    path: 'histoOffres',
    name: 'histoOffrelistview',
    component: () => import('@/views/histoOffre/HistoOffreListView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'histoOffres/:id',
    name: 'histoOffredetailsview',
    component: () => import('@/views/histoOffre/HistoOffreDetailsView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'histoOffres/update/:id',
    name: 'histoOffreupdateview',
    component: () => import('@/views/histoOffre/HistoOffreUpdateView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'histoOffres/create',
    name: 'histoOffrecreateview',
    component: () => import('@/views/histoOffre/HistoOffreCreateView.vue'),
    meta: { requiresAuth: true },
  },
]

export default histoOffreRoutes
