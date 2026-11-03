import type { RouteRecordRaw } from 'vue-router'

const secteurRoutes: Array<RouteRecordRaw> = [
  {
    path: 'secteurs',
    name: 'secteurlistview',
    component: () => import('@/views/secteur/SecteurListView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'secteurs/:id',
    name: 'secteurdetailsview',
    component: () => import('@/views/secteur/SecteurDetailsView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'secteurs/update/:id',
    name: 'secteurupdateview',
    component: () => import('@/views/secteur/SecteurUpdateView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'secteurs/create',
    name: 'secteurcreateview',
    component: () => import('@/views/secteur/SecteurCreateView.vue'),
    meta: { requiresAuth: true },
  },
]

export default secteurRoutes
