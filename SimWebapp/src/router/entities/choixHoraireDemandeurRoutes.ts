import type { RouteRecordRaw } from 'vue-router'

const choixHoraireDemandeurRoutes: Array<RouteRecordRaw> = [
  {
    path: 'choixHoraireDemandeurs',
    name: 'choixHoraireDemandeurlistview',
    component: () => import('@/views/choixHoraireDemandeur/ChoixHoraireDemandeurListView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'choixHoraireDemandeurs/:id',
    name: 'choixHoraireDemandeurdetailsview',
    component: () => import('@/views/choixHoraireDemandeur/ChoixHoraireDemandeurDetailsView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'choixHoraireDemandeurs/update/:id',
    name: 'choixHoraireDemandeurupdateview',
    component: () => import('@/views/choixHoraireDemandeur/ChoixHoraireDemandeurUpdateView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'choixHoraireDemandeurs/create',
    name: 'choixHoraireDemandeurcreateview',
    component: () => import('@/views/choixHoraireDemandeur/ChoixHoraireDemandeurCreateView.vue'),
    meta: { requiresAuth: true },
  },
]

export default choixHoraireDemandeurRoutes
