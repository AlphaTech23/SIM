<template>
  <div class="w-full">
    <!-- Header -->
    <div class="items-center mb-6">
      <h2 class="text-xl font-bold text-primary">{{ $t('home.greetDeveloper') }}</h2>
      <span class="text-gray-500">{{ $t('home.welcome') }}</span>
    </div>

    <h2 class="text-xl font-bold text-neutral">{{ $t('home.titles.entities') }}</h2>
    <hr class="border-gray-200 mb-4" />
    <!-- Quick Actions -->
    <div class="grid grid-cols-1 md:grid-cols-4 gap-4 mb-8">
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">Activites</h3>
        <ul class="menu w-full">
          <li @click="useActivite.goToListView()">
            <span>
              <LinkIcon />
              View list of Activite
            </span>
          </li>

          <li @click="useActivite.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new Activite
            </span>
          </li>
        </ul>
      </div>
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">ActivitesMembres</h3>
        <ul class="menu w-full">
          <li @click="useActivitesMembre.goToListView()">
            <span>
              <LinkIcon />
              View list of ActivitesMembre
            </span>
          </li>

          <li @click="useActivitesMembre.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new ActivitesMembre
            </span>
          </li>
        </ul>
      </div>
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">ChoixHoraireDemandeurs</h3>
        <ul class="menu w-full">
          <li @click="useChoixHoraireDemandeur.goToListView()">
            <span>
              <LinkIcon />
              View list of ChoixHoraireDemandeur
            </span>
          </li>

          <li @click="useChoixHoraireDemandeur.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new ChoixHoraireDemandeur
            </span>
          </li>
        </ul>
      </div>
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">CiblesOffres</h3>
        <ul class="menu w-full">
          <li @click="useCiblesOffre.goToListView()">
            <span>
              <LinkIcon />
              View list of CiblesOffre
            </span>
          </li>

          <li @click="useCiblesOffre.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new CiblesOffre
            </span>
          </li>
        </ul>
      </div>
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">HistoOffres</h3>
        <ul class="menu w-full">
          <li @click="useHistoOffre.goToListView()">
            <span>
              <LinkIcon />
              View list of HistoOffre
            </span>
          </li>

          <li @click="useHistoOffre.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new HistoOffre
            </span>
          </li>
        </ul>
      </div>
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">Membres</h3>
        <ul class="menu w-full">
          <li @click="useMembre.goToListView()">
            <span>
              <LinkIcon />
              View list of Membre
            </span>
          </li>

          <li @click="useMembre.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new Membre
            </span>
          </li>
        </ul>
      </div>
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">Offres</h3>
        <ul class="menu w-full">
          <li @click="useOffre.goToListView()">
            <span>
              <LinkIcon />
              View list of Offre
            </span>
          </li>

          <li @click="useOffre.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new Offre
            </span>
          </li>
        </ul>
      </div>
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">RendezVous</h3>
        <ul class="menu w-full">
          <li @click="useRendezVou.goToListView()">
            <span>
              <LinkIcon />
              View list of RendezVou
            </span>
          </li>

          <li @click="useRendezVou.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new RendezVou
            </span>
          </li>
        </ul>
      </div>
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">Secteurs</h3>
        <ul class="menu w-full">
          <li @click="useSecteur.goToListView()">
            <span>
              <LinkIcon />
              View list of Secteur
            </span>
          </li>

          <li @click="useSecteur.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new Secteur
            </span>
          </li>
        </ul>
      </div>
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">Statuts</h3>
        <ul class="menu w-full">
          <li @click="useStatut.goToListView()">
            <span>
              <LinkIcon />
              View list of Statut
            </span>
          </li>

          <li @click="useStatut.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new Statut
            </span>
          </li>
        </ul>
      </div>
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">StatutOffres</h3>
        <ul class="menu w-full">
          <li @click="useStatutOffre.goToListView()">
            <span>
              <LinkIcon />
              View list of StatutOffre
            </span>
          </li>

          <li @click="useStatutOffre.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new StatutOffre
            </span>
          </li>
        </ul>
      </div>
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">StatutRendezVous</h3>
        <ul class="menu w-full">
          <li @click="useStatutRendezVou.goToListView()">
            <span>
              <LinkIcon />
              View list of StatutRendezVou
            </span>
          </li>

          <li @click="useStatutRendezVou.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new StatutRendezVou
            </span>
          </li>
        </ul>
      </div>
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">Sujets</h3>
        <ul class="menu w-full">
          <li @click="useSujet.goToListView()">
            <span>
              <LinkIcon />
              View list of Sujet
            </span>
          </li>

          <li @click="useSujet.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new Sujet
            </span>
          </li>
        </ul>
      </div>
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">SujetsMembres</h3>
        <ul class="menu w-full">
          <li @click="useSujetsMembre.goToListView()">
            <span>
              <LinkIcon />
              View list of SujetsMembre
            </span>
          </li>

          <li @click="useSujetsMembre.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new SujetsMembre
            </span>
          </li>
        </ul>
      </div>
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">TypeOffres</h3>
        <ul class="menu w-full">
          <li @click="useTypeOffre.goToListView()">
            <span>
              <LinkIcon />
              View list of TypeOffre
            </span>
          </li>

          <li @click="useTypeOffre.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new TypeOffre
            </span>
          </li>
        </ul>
      </div>
    </div>

    <h2 class="text-xl font-bold text-neutral">{{ $t('home.titles.views') }}</h2>
    <hr class="border-gray-200 mb-4" />
    <!-- Quick Actions -->
    <div class="grid grid-cols-1 md:grid-cols-4 gap-4 mb-8">
      <div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">VRendezVous</h3>
        <ul class="menu w-full">
          <li @click="useVRendezVou.goToListView()">
            <span>
              <LinkIcon />
              View list of VRendezVou
            </span>
          </li>
        </ul>
      </div>
<div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">VRendezVousAcceptes</h3>
        <ul class="menu w-full">
          <li @click="useVRendezVousAccepte.goToListView()">
            <span>
              <LinkIcon />
              View list of VRendezVousAccepte
            </span>
          </li>
        </ul>
      </div>
<div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">VRendezVousAnnules</h3>
        <ul class="menu w-full">
          <li @click="useVRendezVousAnnule.goToListView()">
            <span>
              <LinkIcon />
              View list of VRendezVousAnnule
            </span>
          </li>
        </ul>
      </div>
<div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">VRendezVousEnAttentes</h3>
        <ul class="menu w-full">
          <li @click="useVRendezVousEnAttente.goToListView()">
            <span>
              <LinkIcon />
              View list of VRendezVousEnAttente
            </span>
          </li>
        </ul>
      </div>
<div
        class="p-4 bg-base-100 shadow-md rounded-xl flex flex-col border border-gray-200 items-start"
      >
        <h3 class="font-semibold text-lg mb-2">VRendezVousRefuses</h3>
        <ul class="menu w-full">
          <li @click="useVRendezVousRefuse.goToListView()">
            <span>
              <LinkIcon />
              View list of VRendezVousRefuse
            </span>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import LinkIcon from '@/components/icons/LinkIcon.vue'
import { useActivites } from '@/composables/useActivites'
import { useActivitesMembres } from '@/composables/useActivitesMembres'
import { useChoixHoraireDemandeurs } from '@/composables/useChoixHoraireDemandeurs'
import { useCiblesOffres } from '@/composables/useCiblesOffres'
import { useHistoOffres } from '@/composables/useHistoOffres'
import { useMembres } from '@/composables/useMembres'
import { useOffres } from '@/composables/useOffres'
import { useRendezVous } from '@/composables/useRendezVous'
import { useSecteurs } from '@/composables/useSecteurs'
import { useStatuts } from '@/composables/useStatuts'
import { useStatutOffres } from '@/composables/useStatutOffres'
import { useStatutRendezVous } from '@/composables/useStatutRendezVous'
import { useSujets } from '@/composables/useSujets'
import { useSujetsMembres } from '@/composables/useSujetsMembres'
import { useTypeOffres } from '@/composables/useTypeOffres'
import { useVRendezVous } from '@/composables/useVRendezVous'
import { useVRendezVousAcceptes } from '@/composables/useVRendezVousAcceptes'
import { useVRendezVousAnnules } from '@/composables/useVRendezVousAnnules'
import { useVRendezVousEnAttentes } from '@/composables/useVRendezVousEnAttentes'
import { useVRendezVousRefuses } from '@/composables/useVRendezVousRefuses'

const useActivite = useActivites()
const useActivitesMembre = useActivitesMembres()
const useChoixHoraireDemandeur = useChoixHoraireDemandeurs()
const useCiblesOffre = useCiblesOffres()
const useHistoOffre = useHistoOffres()
const useMembre = useMembres()
const useOffre = useOffres()
const useRendezVou = useRendezVous()
const useSecteur = useSecteurs()
const useStatut = useStatuts()
const useStatutOffre = useStatutOffres()
const useStatutRendezVou = useStatutRendezVous()
const useSujet = useSujets()
const useSujetsMembre = useSujetsMembres()
const useTypeOffre = useTypeOffres()
const useVRendezVou = useVRendezVous()
const useVRendezVousAccepte = useVRendezVousAcceptes()
const useVRendezVousAnnule = useVRendezVousAnnules()
const useVRendezVousEnAttente = useVRendezVousEnAttentes()
const useVRendezVousRefuse = useVRendezVousRefuses()
</script>
