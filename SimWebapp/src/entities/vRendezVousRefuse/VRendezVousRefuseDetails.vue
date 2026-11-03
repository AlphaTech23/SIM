<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="vRendezVousRefuse">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="vRendezVousRefuseIdRendezVous" class="block text-sm font-medium mb-1">Id rendez vous</label>
            <div id="vRendezVousRefuseIdRendezVous" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.idRendezVous ? $n(vRendezVousRefuse.idRendezVous, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseIdDemandeur" class="block text-sm font-medium mb-1">Id demandeur</label>
            <div id="vRendezVousRefuseIdDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.idDemandeur ? $n(vRendezVousRefuse.idDemandeur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseMembreDemandeur" class="block text-sm font-medium mb-1">Membre demandeur</label>
            <div id="vRendezVousRefuseMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.membreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseLogoDemandeur" class="block text-sm font-medium mb-1">Logo demandeur</label>
            <div id="vRendezVousRefuseLogoDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.logoDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseTelephoneMembreDemandeur" class="block text-sm font-medium mb-1">Telephone membre demandeur</label>
            <div id="vRendezVousRefuseTelephoneMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.telephoneMembreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseEmailMembreDemandeur" class="block text-sm font-medium mb-1">Email membre demandeur</label>
            <div id="vRendezVousRefuseEmailMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.emailMembreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseWhatsappMembreDemandeur" class="block text-sm font-medium mb-1">Whatsapp membre demandeur</label>
            <div id="vRendezVousRefuseWhatsappMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.whatsappMembreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseAdresseMembreDemandeur" class="block text-sm font-medium mb-1">Adresse membre demandeur</label>
            <div id="vRendezVousRefuseAdresseMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.adresseMembreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseIdSecteurDemandeur" class="block text-sm font-medium mb-1">Id secteur demandeur</label>
            <div id="vRendezVousRefuseIdSecteurDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.idSecteurDemandeur ? $n(vRendezVousRefuse.idSecteurDemandeur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseSecteurDemandeur" class="block text-sm font-medium mb-1">Secteur demandeur</label>
            <div id="vRendezVousRefuseSecteurDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.secteurDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseDescriptionSecteurDemandeur" class="block text-sm font-medium mb-1">Description secteur demandeur</label>
            <div id="vRendezVousRefuseDescriptionSecteurDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.descriptionSecteurDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseIdReceveur" class="block text-sm font-medium mb-1">Id receveur</label>
            <div id="vRendezVousRefuseIdReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.idReceveur ? $n(vRendezVousRefuse.idReceveur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseIdSujetReceveur" class="block text-sm font-medium mb-1">Id sujet receveur</label>
            <div id="vRendezVousRefuseIdSujetReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.idSujetReceveur ? $n(vRendezVousRefuse.idSujetReceveur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseLibeleSujetReceveur" class="block text-sm font-medium mb-1">Libele sujet receveur</label>
            <div id="vRendezVousRefuseLibeleSujetReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.libeleSujetReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseDescriptionSujetReceveur" class="block text-sm font-medium mb-1">Description sujet receveur</label>
            <div id="vRendezVousRefuseDescriptionSujetReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.descriptionSujetReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseNomReceveur" class="block text-sm font-medium mb-1">Nom receveur</label>
            <div id="vRendezVousRefuseNomReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.nomReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefusePrenomReceveur" class="block text-sm font-medium mb-1">Prenom receveur</label>
            <div id="vRendezVousRefusePrenomReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.prenomReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseFonctionReceveur" class="block text-sm font-medium mb-1">Fonction receveur</label>
            <div id="vRendezVousRefuseFonctionReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.fonctionReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseTelephoneReceveur" class="block text-sm font-medium mb-1">Telephone receveur</label>
            <div id="vRendezVousRefuseTelephoneReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.telephoneReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseEmailReceveur" class="block text-sm font-medium mb-1">Email receveur</label>
            <div id="vRendezVousRefuseEmailReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.emailReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseWhatsappReceveur" class="block text-sm font-medium mb-1">Whatsapp receveur</label>
            <div id="vRendezVousRefuseWhatsappReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.whatsappReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseAdresseReceveur" class="block text-sm font-medium mb-1">Adresse receveur</label>
            <div id="vRendezVousRefuseAdresseReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.adresseReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseIdMembreReceveur" class="block text-sm font-medium mb-1">Id membre receveur</label>
            <div id="vRendezVousRefuseIdMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.idMembreReceveur ? $n(vRendezVousRefuse.idMembreReceveur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseMembreReceveur" class="block text-sm font-medium mb-1">Membre receveur</label>
            <div id="vRendezVousRefuseMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.membreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseLogoReceveur" class="block text-sm font-medium mb-1">Logo receveur</label>
            <div id="vRendezVousRefuseLogoReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.logoReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseTelephoneMembreReceveur" class="block text-sm font-medium mb-1">Telephone membre receveur</label>
            <div id="vRendezVousRefuseTelephoneMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.telephoneMembreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseEmailMembreReceveur" class="block text-sm font-medium mb-1">Email membre receveur</label>
            <div id="vRendezVousRefuseEmailMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.emailMembreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseWhatsappMembreReceveur" class="block text-sm font-medium mb-1">Whatsapp membre receveur</label>
            <div id="vRendezVousRefuseWhatsappMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.whatsappMembreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseAdresseMembreReceveur" class="block text-sm font-medium mb-1">Adresse membre receveur</label>
            <div id="vRendezVousRefuseAdresseMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.adresseMembreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseIdSecteurReceveur" class="block text-sm font-medium mb-1">Id secteur receveur</label>
            <div id="vRendezVousRefuseIdSecteurReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.idSecteurReceveur ? $n(vRendezVousRefuse.idSecteurReceveur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseSecteurReceveur" class="block text-sm font-medium mb-1">Secteur receveur</label>
            <div id="vRendezVousRefuseSecteurReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.secteurReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseDescriptionSecteurReceveur" class="block text-sm font-medium mb-1">Description secteur receveur</label>
            <div id="vRendezVousRefuseDescriptionSecteurReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.descriptionSecteurReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseContexte" class="block text-sm font-medium mb-1">Contexte</label>
            <div id="vRendezVousRefuseContexte" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.contexte ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseDateRdv" class="block text-sm font-medium mb-1">Date rdv</label>
            <div id="vRendezVousRefuseDateRdv" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.dateRdv ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseIdStatut" class="block text-sm font-medium mb-1">Id statut</label>
            <div id="vRendezVousRefuseIdStatut" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.idStatut ? $n(vRendezVousRefuse.idStatut, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseStatut" class="block text-sm font-medium mb-1">Statut</label>
            <div id="vRendezVousRefuseStatut" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.statut ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseDateChangementStatut" class="block text-sm font-medium mb-1">Date changement statut</label>
            <div id="vRendezVousRefuseDateChangementStatut" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.dateChangementStatut ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseRemarque" class="block text-sm font-medium mb-1">Remarque</label>
            <div id="vRendezVousRefuseRemarque" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.remarque ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseHistoriqueStatuts" class="block text-sm font-medium mb-1">Historique statuts</label>
            <div id="vRendezVousRefuseHistoriqueStatuts" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.historiqueStatuts ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousRefuseCreneaux" class="block text-sm font-medium mb-1">Creneaux</label>
            <div id="vRendezVousRefuseCreneaux" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousRefuse.creneaux ?? '' }}</span>
              
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { VRendezVousRefuse } from '@/models/VRendezVousRefuseModel'



defineProps({
  vRendezVousRefuse: {
    type: Object as PropType<VRendezVousRefuse>,
    required: true,
  },
})

</script>
<style scoped></style>
