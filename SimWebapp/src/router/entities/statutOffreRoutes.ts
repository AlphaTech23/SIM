import type { RouteRecordRaw } from 'vue-router'

const statutOffreRoutes: Array<RouteRecordRaw> = [
  {
    path: 'statutOffres',
    name: 'statutOffrelistview',
    component: () => import('@/views/statutOffre/StatutOffreListView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'statutOffres/:id',
    name: 'statutOffredetailsview',
    component: () => import('@/views/statutOffre/StatutOffreDetailsView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'statutOffres/update/:id',
    name: 'statutOffreupdateview',
    component: () => import('@/views/statutOffre/StatutOffreUpdateView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'statutOffres/create',
    name: 'statutOffrecreateview',
    component: () => import('@/views/statutOffre/StatutOffreCreateView.vue'),
    meta: { requiresAuth: true },
  },
]

export default statutOffreRoutes
