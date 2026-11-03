<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="vRendezVousAccepte">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="vRendezVousAccepteIdRendezVous" class="block text-sm font-medium mb-1">Id rendez vous</label>
            <div id="vRendezVousAccepteIdRendezVous" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.idRendezVous ? $n(vRendezVousAccepte.idRendezVous, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteIdDemandeur" class="block text-sm font-medium mb-1">Id demandeur</label>
            <div id="vRendezVousAccepteIdDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.idDemandeur ? $n(vRendezVousAccepte.idDemandeur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteMembreDemandeur" class="block text-sm font-medium mb-1">Membre demandeur</label>
            <div id="vRendezVousAccepteMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.membreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteLogoDemandeur" class="block text-sm font-medium mb-1">Logo demandeur</label>
            <div id="vRendezVousAccepteLogoDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.logoDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteTelephoneMembreDemandeur" class="block text-sm font-medium mb-1">Telephone membre demandeur</label>
            <div id="vRendezVousAccepteTelephoneMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.telephoneMembreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteEmailMembreDemandeur" class="block text-sm font-medium mb-1">Email membre demandeur</label>
            <div id="vRendezVousAccepteEmailMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.emailMembreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteWhatsappMembreDemandeur" class="block text-sm font-medium mb-1">Whatsapp membre demandeur</label>
            <div id="vRendezVousAccepteWhatsappMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.whatsappMembreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteAdresseMembreDemandeur" class="block text-sm font-medium mb-1">Adresse membre demandeur</label>
            <div id="vRendezVousAccepteAdresseMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.adresseMembreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteIdSecteurDemandeur" class="block text-sm font-medium mb-1">Id secteur demandeur</label>
            <div id="vRendezVousAccepteIdSecteurDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.idSecteurDemandeur ? $n(vRendezVousAccepte.idSecteurDemandeur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteSecteurDemandeur" class="block text-sm font-medium mb-1">Secteur demandeur</label>
            <div id="vRendezVousAccepteSecteurDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.secteurDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteDescriptionSecteurDemandeur" class="block text-sm font-medium mb-1">Description secteur demandeur</label>
            <div id="vRendezVousAccepteDescriptionSecteurDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.descriptionSecteurDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteIdReceveur" class="block text-sm font-medium mb-1">Id receveur</label>
            <div id="vRendezVousAccepteIdReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.idReceveur ? $n(vRendezVousAccepte.idReceveur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteIdSujetReceveur" class="block text-sm font-medium mb-1">Id sujet receveur</label>
            <div id="vRendezVousAccepteIdSujetReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.idSujetReceveur ? $n(vRendezVousAccepte.idSujetReceveur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteLibeleSujetReceveur" class="block text-sm font-medium mb-1">Libele sujet receveur</label>
            <div id="vRendezVousAccepteLibeleSujetReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.libeleSujetReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteDescriptionSujetReceveur" class="block text-sm font-medium mb-1">Description sujet receveur</label>
            <div id="vRendezVousAccepteDescriptionSujetReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.descriptionSujetReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteNomReceveur" class="block text-sm font-medium mb-1">Nom receveur</label>
            <div id="vRendezVousAccepteNomReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.nomReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAcceptePrenomReceveur" class="block text-sm font-medium mb-1">Prenom receveur</label>
            <div id="vRendezVousAcceptePrenomReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.prenomReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteFonctionReceveur" class="block text-sm font-medium mb-1">Fonction receveur</label>
            <div id="vRendezVousAccepteFonctionReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.fonctionReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteTelephoneReceveur" class="block text-sm font-medium mb-1">Telephone receveur</label>
            <div id="vRendezVousAccepteTelephoneReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.telephoneReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteEmailReceveur" class="block text-sm font-medium mb-1">Email receveur</label>
            <div id="vRendezVousAccepteEmailReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.emailReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteWhatsappReceveur" class="block text-sm font-medium mb-1">Whatsapp receveur</label>
            <div id="vRendezVousAccepteWhatsappReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.whatsappReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteAdresseReceveur" class="block text-sm font-medium mb-1">Adresse receveur</label>
            <div id="vRendezVousAccepteAdresseReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.adresseReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteIdMembreReceveur" class="block text-sm font-medium mb-1">Id membre receveur</label>
            <div id="vRendezVousAccepteIdMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.idMembreReceveur ? $n(vRendezVousAccepte.idMembreReceveur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteMembreReceveur" class="block text-sm font-medium mb-1">Membre receveur</label>
            <div id="vRendezVousAccepteMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.membreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteLogoReceveur" class="block text-sm font-medium mb-1">Logo receveur</label>
            <div id="vRendezVousAccepteLogoReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.logoReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteTelephoneMembreReceveur" class="block text-sm font-medium mb-1">Telephone membre receveur</label>
            <div id="vRendezVousAccepteTelephoneMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.telephoneMembreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteEmailMembreReceveur" class="block text-sm font-medium mb-1">Email membre receveur</label>
            <div id="vRendezVousAccepteEmailMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.emailMembreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteWhatsappMembreReceveur" class="block text-sm font-medium mb-1">Whatsapp membre receveur</label>
            <div id="vRendezVousAccepteWhatsappMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.whatsappMembreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteAdresseMembreReceveur" class="block text-sm font-medium mb-1">Adresse membre receveur</label>
            <div id="vRendezVousAccepteAdresseMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.adresseMembreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteIdSecteurReceveur" class="block text-sm font-medium mb-1">Id secteur receveur</label>
            <div id="vRendezVousAccepteIdSecteurReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.idSecteurReceveur ? $n(vRendezVousAccepte.idSecteurReceveur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteSecteurReceveur" class="block text-sm font-medium mb-1">Secteur receveur</label>
            <div id="vRendezVousAccepteSecteurReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.secteurReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteDescriptionSecteurReceveur" class="block text-sm font-medium mb-1">Description secteur receveur</label>
            <div id="vRendezVousAccepteDescriptionSecteurReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.descriptionSecteurReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteContexte" class="block text-sm font-medium mb-1">Contexte</label>
            <div id="vRendezVousAccepteContexte" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.contexte ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteDateRdv" class="block text-sm font-medium mb-1">Date rdv</label>
            <div id="vRendezVousAccepteDateRdv" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.dateRdv ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteIdStatut" class="block text-sm font-medium mb-1">Id statut</label>
            <div id="vRendezVousAccepteIdStatut" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.idStatut ? $n(vRendezVousAccepte.idStatut, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteStatut" class="block text-sm font-medium mb-1">Statut</label>
            <div id="vRendezVousAccepteStatut" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.statut ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteDateChangementStatut" class="block text-sm font-medium mb-1">Date changement statut</label>
            <div id="vRendezVousAccepteDateChangementStatut" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.dateChangementStatut ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteRemarque" class="block text-sm font-medium mb-1">Remarque</label>
            <div id="vRendezVousAccepteRemarque" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.remarque ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteHistoriqueStatuts" class="block text-sm font-medium mb-1">Historique statuts</label>
            <div id="vRendezVousAccepteHistoriqueStatuts" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.historiqueStatuts ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAccepteCreneaux" class="block text-sm font-medium mb-1">Creneaux</label>
            <div id="vRendezVousAccepteCreneaux" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAccepte.creneaux ?? '' }}</span>
              
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { VRendezVousAccepte } from '@/models/VRendezVousAccepteModel'



defineProps({
  vRendezVousAccepte: {
    type: Object as PropType<VRendezVousAccepte>,
    required: true,
  },
})

</script>
<style scoped></style>
