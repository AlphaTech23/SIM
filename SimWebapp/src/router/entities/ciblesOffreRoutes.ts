import type { RouteRecordRaw } from 'vue-router'

const ciblesOffreRoutes: Array<RouteRecordRaw> = [
  {
    path: 'ciblesOffres',
    name: 'ciblesOffrelistview',
    component: () => import('@/views/ciblesOffre/CiblesOffreListView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'ciblesOffres/:id',
    name: 'ciblesOffredetailsview',
    component: () => import('@/views/ciblesOffre/CiblesOffreDetailsView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'ciblesOffres/update/:id',
    name: 'ciblesOffreupdateview',
    component: () => import('@/views/ciblesOffre/CiblesOffreUpdateView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'ciblesOffres/create',
    name: 'ciblesOffrecreateview',
    component: () => import('@/views/ciblesOffre/CiblesOffreCreateView.vue'),
    meta: { requiresAuth: true },
  },
]

export default ciblesOffreRoutes
