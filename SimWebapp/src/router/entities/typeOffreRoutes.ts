import type { RouteRecordRaw } from 'vue-router'

const typeOffreRoutes: Array<RouteRecordRaw> = [
  {
    path: 'typeOffres',
    name: 'typeOffrelistview',
    component: () => import('@/views/typeOffre/TypeOffreListView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'typeOffres/:id',
    name: 'typeOffredetailsview',
    component: () => import('@/views/typeOffre/TypeOffreDetailsView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'typeOffres/update/:id',
    name: 'typeOffreupdateview',
    component: () => import('@/views/typeOffre/TypeOffreUpdateView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'typeOffres/create',
    name: 'typeOffrecreateview',
    component: () => import('@/views/typeOffre/TypeOffreCreateView.vue'),
    meta: { requiresAuth: true },
  },
]

export default typeOffreRoutes
