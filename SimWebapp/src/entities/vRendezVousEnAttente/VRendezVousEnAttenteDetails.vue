<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="vRendezVousEnAttente">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="vRendezVousEnAttenteIdRendezVous" class="block text-sm font-medium mb-1">Id rendez vous</label>
            <div id="vRendezVousEnAttenteIdRendezVous" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.idRendezVous ? $n(vRendezVousEnAttente.idRendezVous, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteIdDemandeur" class="block text-sm font-medium mb-1">Id demandeur</label>
            <div id="vRendezVousEnAttenteIdDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.idDemandeur ? $n(vRendezVousEnAttente.idDemandeur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteMembreDemandeur" class="block text-sm font-medium mb-1">Membre demandeur</label>
            <div id="vRendezVousEnAttenteMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.membreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteLogoDemandeur" class="block text-sm font-medium mb-1">Logo demandeur</label>
            <div id="vRendezVousEnAttenteLogoDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.logoDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteTelephoneMembreDemandeur" class="block text-sm font-medium mb-1">Telephone membre demandeur</label>
            <div id="vRendezVousEnAttenteTelephoneMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.telephoneMembreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteEmailMembreDemandeur" class="block text-sm font-medium mb-1">Email membre demandeur</label>
            <div id="vRendezVousEnAttenteEmailMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.emailMembreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteWhatsappMembreDemandeur" class="block text-sm font-medium mb-1">Whatsapp membre demandeur</label>
            <div id="vRendezVousEnAttenteWhatsappMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.whatsappMembreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteAdresseMembreDemandeur" class="block text-sm font-medium mb-1">Adresse membre demandeur</label>
            <div id="vRendezVousEnAttenteAdresseMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.adresseMembreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteIdSecteurDemandeur" class="block text-sm font-medium mb-1">Id secteur demandeur</label>
            <div id="vRendezVousEnAttenteIdSecteurDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.idSecteurDemandeur ? $n(vRendezVousEnAttente.idSecteurDemandeur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteSecteurDemandeur" class="block text-sm font-medium mb-1">Secteur demandeur</label>
            <div id="vRendezVousEnAttenteSecteurDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.secteurDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteDescriptionSecteurDemandeur" class="block text-sm font-medium mb-1">Description secteur demandeur</label>
            <div id="vRendezVousEnAttenteDescriptionSecteurDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.descriptionSecteurDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteIdReceveur" class="block text-sm font-medium mb-1">Id receveur</label>
            <div id="vRendezVousEnAttenteIdReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.idReceveur ? $n(vRendezVousEnAttente.idReceveur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteIdSujetReceveur" class="block text-sm font-medium mb-1">Id sujet receveur</label>
            <div id="vRendezVousEnAttenteIdSujetReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.idSujetReceveur ? $n(vRendezVousEnAttente.idSujetReceveur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteLibeleSujetReceveur" class="block text-sm font-medium mb-1">Libele sujet receveur</label>
            <div id="vRendezVousEnAttenteLibeleSujetReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.libeleSujetReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteDescriptionSujetReceveur" class="block text-sm font-medium mb-1">Description sujet receveur</label>
            <div id="vRendezVousEnAttenteDescriptionSujetReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.descriptionSujetReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteNomReceveur" class="block text-sm font-medium mb-1">Nom receveur</label>
            <div id="vRendezVousEnAttenteNomReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.nomReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttentePrenomReceveur" class="block text-sm font-medium mb-1">Prenom receveur</label>
            <div id="vRendezVousEnAttentePrenomReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.prenomReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteFonctionReceveur" class="block text-sm font-medium mb-1">Fonction receveur</label>
            <div id="vRendezVousEnAttenteFonctionReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.fonctionReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteTelephoneReceveur" class="block text-sm font-medium mb-1">Telephone receveur</label>
            <div id="vRendezVousEnAttenteTelephoneReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.telephoneReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteEmailReceveur" class="block text-sm font-medium mb-1">Email receveur</label>
            <div id="vRendezVousEnAttenteEmailReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.emailReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteWhatsappReceveur" class="block text-sm font-medium mb-1">Whatsapp receveur</label>
            <div id="vRendezVousEnAttenteWhatsappReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.whatsappReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteAdresseReceveur" class="block text-sm font-medium mb-1">Adresse receveur</label>
            <div id="vRendezVousEnAttenteAdresseReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.adresseReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteIdMembreReceveur" class="block text-sm font-medium mb-1">Id membre receveur</label>
            <div id="vRendezVousEnAttenteIdMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.idMembreReceveur ? $n(vRendezVousEnAttente.idMembreReceveur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteMembreReceveur" class="block text-sm font-medium mb-1">Membre receveur</label>
            <div id="vRendezVousEnAttenteMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.membreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteLogoReceveur" class="block text-sm font-medium mb-1">Logo receveur</label>
            <div id="vRendezVousEnAttenteLogoReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.logoReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteTelephoneMembreReceveur" class="block text-sm font-medium mb-1">Telephone membre receveur</label>
            <div id="vRendezVousEnAttenteTelephoneMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.telephoneMembreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteEmailMembreReceveur" class="block text-sm font-medium mb-1">Email membre receveur</label>
            <div id="vRendezVousEnAttenteEmailMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.emailMembreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteWhatsappMembreReceveur" class="block text-sm font-medium mb-1">Whatsapp membre receveur</label>
            <div id="vRendezVousEnAttenteWhatsappMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.whatsappMembreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteAdresseMembreReceveur" class="block text-sm font-medium mb-1">Adresse membre receveur</label>
            <div id="vRendezVousEnAttenteAdresseMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.adresseMembreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteIdSecteurReceveur" class="block text-sm font-medium mb-1">Id secteur receveur</label>
            <div id="vRendezVousEnAttenteIdSecteurReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.idSecteurReceveur ? $n(vRendezVousEnAttente.idSecteurReceveur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteSecteurReceveur" class="block text-sm font-medium mb-1">Secteur receveur</label>
            <div id="vRendezVousEnAttenteSecteurReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.secteurReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteDescriptionSecteurReceveur" class="block text-sm font-medium mb-1">Description secteur receveur</label>
            <div id="vRendezVousEnAttenteDescriptionSecteurReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.descriptionSecteurReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteContexte" class="block text-sm font-medium mb-1">Contexte</label>
            <div id="vRendezVousEnAttenteContexte" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.contexte ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteDateRdv" class="block text-sm font-medium mb-1">Date rdv</label>
            <div id="vRendezVousEnAttenteDateRdv" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.dateRdv ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteIdStatut" class="block text-sm font-medium mb-1">Id statut</label>
            <div id="vRendezVousEnAttenteIdStatut" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.idStatut ? $n(vRendezVousEnAttente.idStatut, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteStatut" class="block text-sm font-medium mb-1">Statut</label>
            <div id="vRendezVousEnAttenteStatut" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.statut ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteDateChangementStatut" class="block text-sm font-medium mb-1">Date changement statut</label>
            <div id="vRendezVousEnAttenteDateChangementStatut" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.dateChangementStatut ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteRemarque" class="block text-sm font-medium mb-1">Remarque</label>
            <div id="vRendezVousEnAttenteRemarque" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.remarque ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteHistoriqueStatuts" class="block text-sm font-medium mb-1">Historique statuts</label>
            <div id="vRendezVousEnAttenteHistoriqueStatuts" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.historiqueStatuts ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousEnAttenteCreneaux" class="block text-sm font-medium mb-1">Creneaux</label>
            <div id="vRendezVousEnAttenteCreneaux" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousEnAttente.creneaux ?? '' }}</span>
              
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { VRendezVousEnAttente } from '@/models/VRendezVousEnAttenteModel'



defineProps({
  vRendezVousEnAttente: {
    type: Object as PropType<VRendezVousEnAttente>,
    required: true,
  },
})

</script>
<style scoped></style>
