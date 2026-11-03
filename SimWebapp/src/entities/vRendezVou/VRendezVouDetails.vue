<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="vRendezVou">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="vRendezVouIdRendezVous" class="block text-sm font-medium mb-1">Id rendez vous</label>
            <div id="vRendezVouIdRendezVous" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.idRendezVous ? $n(vRendezVou.idRendezVous, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouIdDemandeur" class="block text-sm font-medium mb-1">Id demandeur</label>
            <div id="vRendezVouIdDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.idDemandeur ? $n(vRendezVou.idDemandeur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouMembreDemandeur" class="block text-sm font-medium mb-1">Membre demandeur</label>
            <div id="vRendezVouMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.membreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouLogoDemandeur" class="block text-sm font-medium mb-1">Logo demandeur</label>
            <div id="vRendezVouLogoDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.logoDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouTelephoneMembreDemandeur" class="block text-sm font-medium mb-1">Telephone membre demandeur</label>
            <div id="vRendezVouTelephoneMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.telephoneMembreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouEmailMembreDemandeur" class="block text-sm font-medium mb-1">Email membre demandeur</label>
            <div id="vRendezVouEmailMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.emailMembreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouWhatsappMembreDemandeur" class="block text-sm font-medium mb-1">Whatsapp membre demandeur</label>
            <div id="vRendezVouWhatsappMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.whatsappMembreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouAdresseMembreDemandeur" class="block text-sm font-medium mb-1">Adresse membre demandeur</label>
            <div id="vRendezVouAdresseMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.adresseMembreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouIdSecteurDemandeur" class="block text-sm font-medium mb-1">Id secteur demandeur</label>
            <div id="vRendezVouIdSecteurDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.idSecteurDemandeur ? $n(vRendezVou.idSecteurDemandeur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouSecteurDemandeur" class="block text-sm font-medium mb-1">Secteur demandeur</label>
            <div id="vRendezVouSecteurDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.secteurDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouDescriptionSecteurDemandeur" class="block text-sm font-medium mb-1">Description secteur demandeur</label>
            <div id="vRendezVouDescriptionSecteurDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.descriptionSecteurDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouIdReceveur" class="block text-sm font-medium mb-1">Id receveur</label>
            <div id="vRendezVouIdReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.idReceveur ? $n(vRendezVou.idReceveur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouIdSujetReceveur" class="block text-sm font-medium mb-1">Id sujet receveur</label>
            <div id="vRendezVouIdSujetReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.idSujetReceveur ? $n(vRendezVou.idSujetReceveur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouLibeleSujetReceveur" class="block text-sm font-medium mb-1">Libele sujet receveur</label>
            <div id="vRendezVouLibeleSujetReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.libeleSujetReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouDescriptionSujetReceveur" class="block text-sm font-medium mb-1">Description sujet receveur</label>
            <div id="vRendezVouDescriptionSujetReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.descriptionSujetReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouNomReceveur" class="block text-sm font-medium mb-1">Nom receveur</label>
            <div id="vRendezVouNomReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.nomReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouPrenomReceveur" class="block text-sm font-medium mb-1">Prenom receveur</label>
            <div id="vRendezVouPrenomReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.prenomReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouFonctionReceveur" class="block text-sm font-medium mb-1">Fonction receveur</label>
            <div id="vRendezVouFonctionReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.fonctionReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouTelephoneReceveur" class="block text-sm font-medium mb-1">Telephone receveur</label>
            <div id="vRendezVouTelephoneReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.telephoneReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouEmailReceveur" class="block text-sm font-medium mb-1">Email receveur</label>
            <div id="vRendezVouEmailReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.emailReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouWhatsappReceveur" class="block text-sm font-medium mb-1">Whatsapp receveur</label>
            <div id="vRendezVouWhatsappReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.whatsappReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouAdresseReceveur" class="block text-sm font-medium mb-1">Adresse receveur</label>
            <div id="vRendezVouAdresseReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.adresseReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouIdMembreReceveur" class="block text-sm font-medium mb-1">Id membre receveur</label>
            <div id="vRendezVouIdMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.idMembreReceveur ? $n(vRendezVou.idMembreReceveur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouMembreReceveur" class="block text-sm font-medium mb-1">Membre receveur</label>
            <div id="vRendezVouMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.membreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouLogoReceveur" class="block text-sm font-medium mb-1">Logo receveur</label>
            <div id="vRendezVouLogoReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.logoReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouTelephoneMembreReceveur" class="block text-sm font-medium mb-1">Telephone membre receveur</label>
            <div id="vRendezVouTelephoneMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.telephoneMembreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouEmailMembreReceveur" class="block text-sm font-medium mb-1">Email membre receveur</label>
            <div id="vRendezVouEmailMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.emailMembreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouWhatsappMembreReceveur" class="block text-sm font-medium mb-1">Whatsapp membre receveur</label>
            <div id="vRendezVouWhatsappMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.whatsappMembreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouAdresseMembreReceveur" class="block text-sm font-medium mb-1">Adresse membre receveur</label>
            <div id="vRendezVouAdresseMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.adresseMembreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouIdSecteurReceveur" class="block text-sm font-medium mb-1">Id secteur receveur</label>
            <div id="vRendezVouIdSecteurReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.idSecteurReceveur ? $n(vRendezVou.idSecteurReceveur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouSecteurReceveur" class="block text-sm font-medium mb-1">Secteur receveur</label>
            <div id="vRendezVouSecteurReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.secteurReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouDescriptionSecteurReceveur" class="block text-sm font-medium mb-1">Description secteur receveur</label>
            <div id="vRendezVouDescriptionSecteurReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.descriptionSecteurReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouContexte" class="block text-sm font-medium mb-1">Contexte</label>
            <div id="vRendezVouContexte" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.contexte ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouDateRdv" class="block text-sm font-medium mb-1">Date rdv</label>
            <div id="vRendezVouDateRdv" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.dateRdv ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouIdStatut" class="block text-sm font-medium mb-1">Id statut</label>
            <div id="vRendezVouIdStatut" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.idStatut ? $n(vRendezVou.idStatut, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouStatut" class="block text-sm font-medium mb-1">Statut</label>
            <div id="vRendezVouStatut" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.statut ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouDateChangementStatut" class="block text-sm font-medium mb-1">Date changement statut</label>
            <div id="vRendezVouDateChangementStatut" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.dateChangementStatut ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouRemarque" class="block text-sm font-medium mb-1">Remarque</label>
            <div id="vRendezVouRemarque" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.remarque ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouHistoriqueStatuts" class="block text-sm font-medium mb-1">Historique statuts</label>
            <div id="vRendezVouHistoriqueStatuts" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.historiqueStatuts ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVouCreneaux" class="block text-sm font-medium mb-1">Creneaux</label>
            <div id="vRendezVouCreneaux" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVou.creneaux ?? '' }}</span>
              
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { VRendezVou } from '@/models/VRendezVouModel'



defineProps({
  vRendezVou: {
    type: Object as PropType<VRendezVou>,
    required: true,
  },
})

</script>
<style scoped></style>
