<template>
  <div class="card bg-base-100">
    <div class="card-body">
      <div v-if="vRendezVousAnnule">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label for="vRendezVousAnnuleIdRendezVous" class="block text-sm font-medium mb-1">Id rendez vous</label>
            <div id="vRendezVousAnnuleIdRendezVous" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.idRendezVous ? $n(vRendezVousAnnule.idRendezVous, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleIdDemandeur" class="block text-sm font-medium mb-1">Id demandeur</label>
            <div id="vRendezVousAnnuleIdDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.idDemandeur ? $n(vRendezVousAnnule.idDemandeur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleMembreDemandeur" class="block text-sm font-medium mb-1">Membre demandeur</label>
            <div id="vRendezVousAnnuleMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.membreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleLogoDemandeur" class="block text-sm font-medium mb-1">Logo demandeur</label>
            <div id="vRendezVousAnnuleLogoDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.logoDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleTelephoneMembreDemandeur" class="block text-sm font-medium mb-1">Telephone membre demandeur</label>
            <div id="vRendezVousAnnuleTelephoneMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.telephoneMembreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleEmailMembreDemandeur" class="block text-sm font-medium mb-1">Email membre demandeur</label>
            <div id="vRendezVousAnnuleEmailMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.emailMembreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleWhatsappMembreDemandeur" class="block text-sm font-medium mb-1">Whatsapp membre demandeur</label>
            <div id="vRendezVousAnnuleWhatsappMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.whatsappMembreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleAdresseMembreDemandeur" class="block text-sm font-medium mb-1">Adresse membre demandeur</label>
            <div id="vRendezVousAnnuleAdresseMembreDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.adresseMembreDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleIdSecteurDemandeur" class="block text-sm font-medium mb-1">Id secteur demandeur</label>
            <div id="vRendezVousAnnuleIdSecteurDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.idSecteurDemandeur ? $n(vRendezVousAnnule.idSecteurDemandeur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleSecteurDemandeur" class="block text-sm font-medium mb-1">Secteur demandeur</label>
            <div id="vRendezVousAnnuleSecteurDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.secteurDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleDescriptionSecteurDemandeur" class="block text-sm font-medium mb-1">Description secteur demandeur</label>
            <div id="vRendezVousAnnuleDescriptionSecteurDemandeur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.descriptionSecteurDemandeur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleIdReceveur" class="block text-sm font-medium mb-1">Id receveur</label>
            <div id="vRendezVousAnnuleIdReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.idReceveur ? $n(vRendezVousAnnule.idReceveur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleIdSujetReceveur" class="block text-sm font-medium mb-1">Id sujet receveur</label>
            <div id="vRendezVousAnnuleIdSujetReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.idSujetReceveur ? $n(vRendezVousAnnule.idSujetReceveur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleLibeleSujetReceveur" class="block text-sm font-medium mb-1">Libele sujet receveur</label>
            <div id="vRendezVousAnnuleLibeleSujetReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.libeleSujetReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleDescriptionSujetReceveur" class="block text-sm font-medium mb-1">Description sujet receveur</label>
            <div id="vRendezVousAnnuleDescriptionSujetReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.descriptionSujetReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleNomReceveur" class="block text-sm font-medium mb-1">Nom receveur</label>
            <div id="vRendezVousAnnuleNomReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.nomReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnulePrenomReceveur" class="block text-sm font-medium mb-1">Prenom receveur</label>
            <div id="vRendezVousAnnulePrenomReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.prenomReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleFonctionReceveur" class="block text-sm font-medium mb-1">Fonction receveur</label>
            <div id="vRendezVousAnnuleFonctionReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.fonctionReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleTelephoneReceveur" class="block text-sm font-medium mb-1">Telephone receveur</label>
            <div id="vRendezVousAnnuleTelephoneReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.telephoneReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleEmailReceveur" class="block text-sm font-medium mb-1">Email receveur</label>
            <div id="vRendezVousAnnuleEmailReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.emailReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleWhatsappReceveur" class="block text-sm font-medium mb-1">Whatsapp receveur</label>
            <div id="vRendezVousAnnuleWhatsappReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.whatsappReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleAdresseReceveur" class="block text-sm font-medium mb-1">Adresse receveur</label>
            <div id="vRendezVousAnnuleAdresseReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.adresseReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleIdMembreReceveur" class="block text-sm font-medium mb-1">Id membre receveur</label>
            <div id="vRendezVousAnnuleIdMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.idMembreReceveur ? $n(vRendezVousAnnule.idMembreReceveur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleMembreReceveur" class="block text-sm font-medium mb-1">Membre receveur</label>
            <div id="vRendezVousAnnuleMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.membreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleLogoReceveur" class="block text-sm font-medium mb-1">Logo receveur</label>
            <div id="vRendezVousAnnuleLogoReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.logoReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleTelephoneMembreReceveur" class="block text-sm font-medium mb-1">Telephone membre receveur</label>
            <div id="vRendezVousAnnuleTelephoneMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.telephoneMembreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleEmailMembreReceveur" class="block text-sm font-medium mb-1">Email membre receveur</label>
            <div id="vRendezVousAnnuleEmailMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.emailMembreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleWhatsappMembreReceveur" class="block text-sm font-medium mb-1">Whatsapp membre receveur</label>
            <div id="vRendezVousAnnuleWhatsappMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.whatsappMembreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleAdresseMembreReceveur" class="block text-sm font-medium mb-1">Adresse membre receveur</label>
            <div id="vRendezVousAnnuleAdresseMembreReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.adresseMembreReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleIdSecteurReceveur" class="block text-sm font-medium mb-1">Id secteur receveur</label>
            <div id="vRendezVousAnnuleIdSecteurReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.idSecteurReceveur ? $n(vRendezVousAnnule.idSecteurReceveur, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleSecteurReceveur" class="block text-sm font-medium mb-1">Secteur receveur</label>
            <div id="vRendezVousAnnuleSecteurReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.secteurReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleDescriptionSecteurReceveur" class="block text-sm font-medium mb-1">Description secteur receveur</label>
            <div id="vRendezVousAnnuleDescriptionSecteurReceveur" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.descriptionSecteurReceveur ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleContexte" class="block text-sm font-medium mb-1">Contexte</label>
            <div id="vRendezVousAnnuleContexte" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.contexte ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleDateRdv" class="block text-sm font-medium mb-1">Date rdv</label>
            <div id="vRendezVousAnnuleDateRdv" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.dateRdv ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleIdStatut" class="block text-sm font-medium mb-1">Id statut</label>
            <div id="vRendezVousAnnuleIdStatut" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.idStatut ? $n(vRendezVousAnnule.idStatut, 'decimal') : '--' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleStatut" class="block text-sm font-medium mb-1">Statut</label>
            <div id="vRendezVousAnnuleStatut" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.statut ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleDateChangementStatut" class="block text-sm font-medium mb-1">Date changement statut</label>
            <div id="vRendezVousAnnuleDateChangementStatut" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.dateChangementStatut ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleRemarque" class="block text-sm font-medium mb-1">Remarque</label>
            <div id="vRendezVousAnnuleRemarque" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.remarque ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleHistoriqueStatuts" class="block text-sm font-medium mb-1">Historique statuts</label>
            <div id="vRendezVousAnnuleHistoriqueStatuts" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.historiqueStatuts ?? '' }}</span>
              
            </div>
          </div>
          <div>
            <label for="vRendezVousAnnuleCreneaux" class="block text-sm font-medium mb-1">Creneaux</label>
            <div id="vRendezVousAnnuleCreneaux" class="input min-h-16 w-full bg-base-100 cursor-default">
              
              <span>{{ vRendezVousAnnule.creneaux ?? '' }}</span>
              
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

import type { VRendezVousAnnule } from '@/models/VRendezVousAnnuleModel'



defineProps({
  vRendezVousAnnule: {
    type: Object as PropType<VRendezVousAnnule>,
    required: true,
  },
})

</script>
<style scoped></style>
