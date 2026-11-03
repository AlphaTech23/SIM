import type { RouteRecordRaw } from 'vue-router'

const vRendezVousAccepteRoutes: Array<RouteRecordRaw> = [
  {
    path: 'vRendezVousAcceptes',
    name: 'vRendezVousAcceptelistview',
    component: () => import('@/views/vRendezVousAccepte/VRendezVousAccepteListView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'vRendezVousAcceptes/:id',
    name: 'vRendezVousAcceptedetailsview',
    component: () => import('@/views/vRendezVousAccepte/VRendezVousAccepteDetailsView.vue'),
    meta: { requiresAuth: true },
  },
]

export default vRendezVousAccepteRoutes
