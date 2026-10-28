import type { RouteRecordRaw } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import SettingsView from '@/views/settings/SettingsView.vue'
import NotFoundView from '@/views/error/NotFoundView.vue'
import MainLayout from '@/layouts/MainLayout.vue'
import activiteRoutes from './entities/activiteRoutes'
import activitesMembreRoutes from './entities/activitesMembreRoutes'
import membreRoutes from './entities/membreRoutes'
import plageHoraireRoutes from './entities/plageHoraireRoutes'
import rendezVouRoutes from './entities/rendezVouRoutes'
import secteurRoutes from './entities/secteurRoutes'
import statutRoutes from './entities/statutRoutes'
import statutRendezVouRoutes from './entities/statutRendezVouRoutes'
import sujetRoutes from './entities/sujetRoutes'
import sujetsMembreRoutes from './entities/sujetsMembreRoutes'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/home',
  },
  {
    path: '/',
    component: MainLayout,
    children: [
      { path: 'home', name: 'home', component: HomeView },
      { path: 'settings', name: 'settings', component: SettingsView },
      ...activiteRoutes,
      ...activitesMembreRoutes,
      ...membreRoutes,
      ...plageHoraireRoutes,
      ...rendezVouRoutes,
      ...secteurRoutes,
      ...statutRoutes,
      ...statutRendezVouRoutes,
      ...sujetRoutes,
      ...sujetsMembreRoutes,
    ],
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'not-found',
    component: NotFoundView,
  },
]
export default routes
