import type { RouteRecordRaw } from 'vue-router'

const offreRoutes: Array<RouteRecordRaw> = [
  {
    path: 'offres',
    name: 'offrelistview',
    component: () => import('@/views/offre/OffreListView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'offres/:id',
    name: 'offredetailsview',
    component: () => import('@/views/offre/OffreDetailsView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'offres/update/:id',
    name: 'offreupdateview',
    component: () => import('@/views/offre/OffreUpdateView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'offres/create',
    name: 'offrecreateview',
    component: () => import('@/views/offre/OffreCreateView.vue'),
    meta: { requiresAuth: true },
  },
]

export default offreRoutes
