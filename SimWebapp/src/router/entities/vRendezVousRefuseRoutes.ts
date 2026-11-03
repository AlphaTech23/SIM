import type { RouteRecordRaw } from 'vue-router'

const vRendezVousRefuseRoutes: Array<RouteRecordRaw> = [
  {
    path: 'vRendezVousRefuses',
    name: 'vRendezVousRefuselistview',
    component: () => import('@/views/vRendezVousRefuse/VRendezVousRefuseListView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'vRendezVousRefuses/:id',
    name: 'vRendezVousRefusedetailsview',
    component: () => import('@/views/vRendezVousRefuse/VRendezVousRefuseDetailsView.vue'),
    meta: { requiresAuth: true },
  },
]

export default vRendezVousRefuseRoutes
