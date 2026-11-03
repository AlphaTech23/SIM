import type { RouteRecordRaw } from 'vue-router'

const sujetRoutes: Array<RouteRecordRaw> = [
  {
    path: 'sujets',
    name: 'sujetlistview',
    component: () => import('@/views/sujet/SujetListView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'sujets/:id',
    name: 'sujetdetailsview',
    component: () => import('@/views/sujet/SujetDetailsView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'sujets/update/:id',
    name: 'sujetupdateview',
    component: () => import('@/views/sujet/SujetUpdateView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: 'sujets/create',
    name: 'sujetcreateview',
    component: () => import('@/views/sujet/SujetCreateView.vue'),
    meta: { requiresAuth: true },
  },
]

export default sujetRoutes
