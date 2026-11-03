import type { RouteRecordRaw } from 'vue-router'

const vRendezVousEnAttenteRoutes: Array<RouteRecordRaw> = [
  {
    path: 'vRendezVousEnAttentes',
    name: 'vRendezVousEnAttentelistview',
    component: () => import('@/views/vRendezVousEnAttente/VRendezVousEnAttenteListView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'vRendezVousEnAttentes/:id',
    name: 'vRendezVousEnAttentedetailsview',
    component: () => import('@/views/vRendezVousEnAttente/VRendezVousEnAttenteDetailsView.vue'),
    meta: { requiresAuth: true },
  },
]

export default vRendezVousEnAttenteRoutes
