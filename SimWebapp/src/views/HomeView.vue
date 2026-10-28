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
        <h3 class="font-semibold text-lg mb-2">PlageHoraires</h3>
        <ul class="menu w-full">
          <li @click="usePlageHoraire.goToListView()">
            <span>
              <LinkIcon />
              View list of PlageHoraire
            </span>
          </li>

          <li @click="usePlageHoraire.goToCreateFormView()">
            <span>
              <LinkIcon />
              Add new PlageHoraire
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
    </div>

    <h2 class="text-xl font-bold text-neutral">{{ $t('home.titles.views') }}</h2>
    <hr class="border-gray-200 mb-4" />
    <!-- Quick Actions -->
    <div class="grid grid-cols-1 md:grid-cols-4 gap-4 mb-8">
    </div>
  </div>
</template>

<script setup lang="ts">
import LinkIcon from '@/components/icons/LinkIcon.vue'
import { useActivites } from '@/composables/useActivites'
import { useActivitesMembres } from '@/composables/useActivitesMembres'
import { useMembres } from '@/composables/useMembres'
import { usePlageHoraires } from '@/composables/usePlageHoraires'
import { useRendezVous } from '@/composables/useRendezVous'
import { useSecteurs } from '@/composables/useSecteurs'
import { useStatuts } from '@/composables/useStatuts'
import { useStatutRendezVous } from '@/composables/useStatutRendezVous'
import { useSujets } from '@/composables/useSujets'
import { useSujetsMembres } from '@/composables/useSujetsMembres'

const useActivite = useActivites()
const useActivitesMembre = useActivitesMembres()
const useMembre = useMembres()
const usePlageHoraire = usePlageHoraires()
const useRendezVou = useRendezVous()
const useSecteur = useSecteurs()
const useStatut = useStatuts()
const useStatutRendezVou = useStatutRendezVous()
const useSujet = useSujets()
const useSujetsMembre = useSujetsMembres()
</script>
