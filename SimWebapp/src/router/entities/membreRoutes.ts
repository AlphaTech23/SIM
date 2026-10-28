import type { RouteRecordRaw } from 'vue-router'

const membreRoutes: Array<RouteRecordRaw> = [
  {
    path: 'membres',
    name: 'membrelistview',
    component: () => import('@/views/membre/MembreListView.vue'),
    
  },
  {
    path: 'membres/:id',
    name: 'membredetailsview',
    component: () => import('@/views/membre/MembreDetailsView.vue'),
    
  },
  {
    path: 'membres/update/:id',
    name: 'membreupdateview',
    component: () => import('@/views/membre/MembreUpdateView.vue'),
  },
  {
    path: 'membres/create',
    name: 'membrecreateview',
    component: () => import('@/views/membre/MembreCreateView.vue'),
  },
]

export default membreRoutes
