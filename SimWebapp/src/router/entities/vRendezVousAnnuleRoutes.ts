import type { RouteRecordRaw } from 'vue-router'

const vRendezVousAnnuleRoutes: Array<RouteRecordRaw> = [
  {
    path: 'vRendezVousAnnules',
    name: 'vRendezVousAnnulelistview',
    component: () => import('@/views/vRendezVousAnnule/VRendezVousAnnuleListView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'vRendezVousAnnules/:id',
    name: 'vRendezVousAnnuledetailsview',
    component: () => import('@/views/vRendezVousAnnule/VRendezVousAnnuleDetailsView.vue'),
    meta: { requiresAuth: true },
  },
]

export default vRendezVousAnnuleRoutes
