import type { RouteRecordRaw } from 'vue-router'

const secteurRoutes: Array<RouteRecordRaw> = [
  {
    path: 'secteurs',
    name: 'secteurlistview',
    component: () => import('@/views/secteur/SecteurListView.vue'),
    
  },
  {
    path: 'secteurs/:id',
    name: 'secteurdetailsview',
    component: () => import('@/views/secteur/SecteurDetailsView.vue'),
    
  },
  {
    path: 'secteurs/update/:id',
    name: 'secteurupdateview',
    component: () => import('@/views/secteur/SecteurUpdateView.vue'),
  },
  {
    path: 'secteurs/create',
    name: 'secteurcreateview',
    component: () => import('@/views/secteur/SecteurCreateView.vue'),
  },
]

export default secteurRoutes
