import type { RouteRecordRaw } from 'vue-router'

const sujetRoutes: Array<RouteRecordRaw> = [
  {
    path: 'sujets',
    name: 'sujetlistview',
    component: () => import('@/views/sujet/SujetListView.vue'),
    
  },
  {
    path: 'sujets/:id',
    name: 'sujetdetailsview',
    component: () => import('@/views/sujet/SujetDetailsView.vue'),
    
  },
  {
    path: 'sujets/update/:id',
    name: 'sujetupdateview',
    component: () => import('@/views/sujet/SujetUpdateView.vue'),
  },
  {
    path: 'sujets/create',
    name: 'sujetcreateview',
    component: () => import('@/views/sujet/SujetCreateView.vue'),
  },
]

export default sujetRoutes
