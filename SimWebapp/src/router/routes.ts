import type { RouteRecordRaw } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import SettingsView from '@/views/settings/SettingsView.vue'
import NotFoundView from '@/views/error/NotFoundView.vue'
import MainLayout from '@/layouts/MainLayout.vue'
import AuthLayout from '@/layouts/AuthLayout.vue'
import authRoutes from './authRoutes'
import activiteRoutes from './entities/activiteRoutes'
import activitesMembreRoutes from './entities/activitesMembreRoutes'
import choixHoraireDemandeurRoutes from './entities/choixHoraireDemandeurRoutes'
import ciblesOffreRoutes from './entities/ciblesOffreRoutes'
import histoOffreRoutes from './entities/histoOffreRoutes'
import membreRoutes from './entities/membreRoutes'
import offreRoutes from './entities/offreRoutes'
import rendezVouRoutes from './entities/rendezVouRoutes'
import secteurRoutes from './entities/secteurRoutes'
import statutRoutes from './entities/statutRoutes'
import statutOffreRoutes from './entities/statutOffreRoutes'
import statutRendezVouRoutes from './entities/statutRendezVouRoutes'
import sujetRoutes from './entities/sujetRoutes'
import sujetsMembreRoutes from './entities/sujetsMembreRoutes'
import typeOffreRoutes from './entities/typeOffreRoutes'
import vRendezVouRoutes from './entities/vRendezVouRoutes'
import vRendezVousAccepteRoutes from './entities/vRendezVousAccepteRoutes'
import vRendezVousAnnuleRoutes from './entities/vRendezVousAnnuleRoutes'
import vRendezVousEnAttenteRoutes from './entities/vRendezVousEnAttenteRoutes'
import vRendezVousRefuseRoutes from './entities/vRendezVousRefuseRoutes'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/home',
  },
  {
    path: '/',
    component: MainLayout,
    children: [
      { path: 'home', name: 'home', component: HomeView, meta: { requiresAuth: true } },
      { path: 'settings', name: 'settings', component: SettingsView, meta: { requiresAuth: true } },
      {
        path: 'profile',
        name: 'profile',
        component: () => import('@/views/auth/ProfileView.vue'),
      },
      {
        path: '/auth/update-profile',
        name: 'update-profile',
        component: () => import('@/views/auth/UpdateProfileView.vue'),
        meta: { requiresAuth: true },
      },
      {
        path: '/auth/change-password',
        name: 'change-password',
        component: () => import('@/views/auth/ChangePasswordView.vue'),
        meta: { requiresAuth: true },
      },
      ...activiteRoutes,
      ...activitesMembreRoutes,
      ...choixHoraireDemandeurRoutes,
      ...ciblesOffreRoutes,
      ...histoOffreRoutes,
      ...membreRoutes,
      ...offreRoutes,
      ...rendezVouRoutes,
      ...secteurRoutes,
      ...statutRoutes,
      ...statutOffreRoutes,
      ...statutRendezVouRoutes,
      ...sujetRoutes,
      ...sujetsMembreRoutes,
      ...typeOffreRoutes,
      ...vRendezVouRoutes,
      ...vRendezVousAccepteRoutes,
      ...vRendezVousAnnuleRoutes,
      ...vRendezVousEnAttenteRoutes,
      ...vRendezVousRefuseRoutes,
    ],
  },
  {
    path: '/auth',
    component: AuthLayout,
    children: [...authRoutes],
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'not-found',
    component: NotFoundView,
  },
]
export default routes
