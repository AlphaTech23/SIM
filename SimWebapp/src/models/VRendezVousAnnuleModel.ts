import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'

export interface IVRendezVousAnnule {
  idRendezVous?: number
  idDemandeur?: number
  membreDemandeur?: string
  logoDemandeur?: string
  telephoneMembreDemandeur?: string
  emailMembreDemandeur?: string
  whatsappMembreDemandeur?: string
  adresseMembreDemandeur?: string
  idSecteurDemandeur?: number
  secteurDemandeur?: string
  descriptionSecteurDemandeur?: string
  idReceveur?: number
  idSujetReceveur?: number
  libeleSujetReceveur?: string
  descriptionSujetReceveur?: string
  nomReceveur?: string
  prenomReceveur?: string
  fonctionReceveur?: string
  telephoneReceveur?: string
  emailReceveur?: string
  whatsappReceveur?: string
  adresseReceveur?: string
  idMembreReceveur?: number
  membreReceveur?: string
  logoReceveur?: string
  telephoneMembreReceveur?: string
  emailMembreReceveur?: string
  whatsappMembreReceveur?: string
  adresseMembreReceveur?: string
  idSecteurReceveur?: number
  secteurReceveur?: string
  descriptionSecteurReceveur?: string
  contexte?: string
  dateRdv?: Date
  idStatut?: number
  statut?: string
  dateChangementStatut?: Date
  remarque?: string
  historiqueStatuts?: any
  creneaux?: any
}

export class VRendezVousAnnule extends BaseModel implements IVRendezVousAnnule {
  idRendezVous?: number
  idDemandeur?: number
  membreDemandeur?: string
  logoDemandeur?: string
  telephoneMembreDemandeur?: string
  emailMembreDemandeur?: string
  whatsappMembreDemandeur?: string
  adresseMembreDemandeur?: string
  idSecteurDemandeur?: number
  secteurDemandeur?: string
  descriptionSecteurDemandeur?: string
  idReceveur?: number
  idSujetReceveur?: number
  libeleSujetReceveur?: string
  descriptionSujetReceveur?: string
  nomReceveur?: string
  prenomReceveur?: string
  fonctionReceveur?: string
  telephoneReceveur?: string
  emailReceveur?: string
  whatsappReceveur?: string
  adresseReceveur?: string
  idMembreReceveur?: number
  membreReceveur?: string
  logoReceveur?: string
  telephoneMembreReceveur?: string
  emailMembreReceveur?: string
  whatsappMembreReceveur?: string
  adresseMembreReceveur?: string
  idSecteurReceveur?: number
  secteurReceveur?: string
  descriptionSecteurReceveur?: string
  contexte?: string
  dateRdv?: Date
  idStatut?: number
  statut?: string
  dateChangementStatut?: Date
  remarque?: string
  historiqueStatuts?: any
  creneaux?: any

  constructor(data?: Partial<IVRendezVousAnnule>) {
    super()
    this.idRendezVous = data?.idRendezVous
    this.idDemandeur = data?.idDemandeur
    this.membreDemandeur = data?.membreDemandeur
    this.logoDemandeur = data?.logoDemandeur
    this.telephoneMembreDemandeur = data?.telephoneMembreDemandeur
    this.emailMembreDemandeur = data?.emailMembreDemandeur
    this.whatsappMembreDemandeur = data?.whatsappMembreDemandeur
    this.adresseMembreDemandeur = data?.adresseMembreDemandeur
    this.idSecteurDemandeur = data?.idSecteurDemandeur
    this.secteurDemandeur = data?.secteurDemandeur
    this.descriptionSecteurDemandeur = data?.descriptionSecteurDemandeur
    this.idReceveur = data?.idReceveur
    this.idSujetReceveur = data?.idSujetReceveur
    this.libeleSujetReceveur = data?.libeleSujetReceveur
    this.descriptionSujetReceveur = data?.descriptionSujetReceveur
    this.nomReceveur = data?.nomReceveur
    this.prenomReceveur = data?.prenomReceveur
    this.fonctionReceveur = data?.fonctionReceveur
    this.telephoneReceveur = data?.telephoneReceveur
    this.emailReceveur = data?.emailReceveur
    this.whatsappReceveur = data?.whatsappReceveur
    this.adresseReceveur = data?.adresseReceveur
    this.idMembreReceveur = data?.idMembreReceveur
    this.membreReceveur = data?.membreReceveur
    this.logoReceveur = data?.logoReceveur
    this.telephoneMembreReceveur = data?.telephoneMembreReceveur
    this.emailMembreReceveur = data?.emailMembreReceveur
    this.whatsappMembreReceveur = data?.whatsappMembreReceveur
    this.adresseMembreReceveur = data?.adresseMembreReceveur
    this.idSecteurReceveur = data?.idSecteurReceveur
    this.secteurReceveur = data?.secteurReceveur
    this.descriptionSecteurReceveur = data?.descriptionSecteurReceveur
    this.contexte = data?.contexte
    this.dateRdv = data?.dateRdv
    this.idStatut = data?.idStatut
    this.statut = data?.statut
    this.dateChangementStatut = data?.dateChangementStatut
    this.remarque = data?.remarque
    this.historiqueStatuts = data?.historiqueStatuts
    this.creneaux = data?.creneaux
  }

  static override getKey(): string {
    return 'idRendezVous'
  }

  public override getKeyValue(): string {
    return String(this.idRendezVous)
  }

  static override getReferenceKey(): string {
    return 'idRendezVous'
  }

  public override getReferenceValue(): string {
    return String(this.idRendezVous)
  }

  protected static override searchDaoMetadata: EntitySearchField[] = [
    {
      key: 'idRendezVous',
      label: 'Id rendez vous',
      type: 'string',
      sortable: true,
      showInTable: true,
      showInFilter: true,
      identifier: true 
    },
    
    { 
      key: 'idDemandeur',
      label: 'Id demandeur',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'idDemandeurMin',
      label: 'Id demandeur Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'idDemandeurMax',
      label: 'Id demandeur Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'membreDemandeur',
      label: 'Membre demandeur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'logoDemandeur',
      label: 'Logo demandeur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'telephoneMembreDemandeur',
      label: 'Telephone membre demandeur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'emailMembreDemandeur',
      label: 'Email membre demandeur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'whatsappMembreDemandeur',
      label: 'Whatsapp membre demandeur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'adresseMembreDemandeur',
      label: 'Adresse membre demandeur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'idSecteurDemandeur',
      label: 'Id secteur demandeur',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'idSecteurDemandeurMin',
      label: 'Id secteur demandeur Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'idSecteurDemandeurMax',
      label: 'Id secteur demandeur Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'secteurDemandeur',
      label: 'Secteur demandeur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'descriptionSecteurDemandeur',
      label: 'Description secteur demandeur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'idReceveur',
      label: 'Id receveur',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'idReceveurMin',
      label: 'Id receveur Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'idReceveurMax',
      label: 'Id receveur Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'idSujetReceveur',
      label: 'Id sujet receveur',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'idSujetReceveurMin',
      label: 'Id sujet receveur Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'idSujetReceveurMax',
      label: 'Id sujet receveur Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'libeleSujetReceveur',
      label: 'Libele sujet receveur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'descriptionSujetReceveur',
      label: 'Description sujet receveur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'nomReceveur',
      label: 'Nom receveur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'prenomReceveur',
      label: 'Prenom receveur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'fonctionReceveur',
      label: 'Fonction receveur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'telephoneReceveur',
      label: 'Telephone receveur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'emailReceveur',
      label: 'Email receveur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'whatsappReceveur',
      label: 'Whatsapp receveur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'adresseReceveur',
      label: 'Adresse receveur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'idMembreReceveur',
      label: 'Id membre receveur',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'idMembreReceveurMin',
      label: 'Id membre receveur Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'idMembreReceveurMax',
      label: 'Id membre receveur Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'membreReceveur',
      label: 'Membre receveur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'logoReceveur',
      label: 'Logo receveur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'telephoneMembreReceveur',
      label: 'Telephone membre receveur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'emailMembreReceveur',
      label: 'Email membre receveur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'whatsappMembreReceveur',
      label: 'Whatsapp membre receveur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'adresseMembreReceveur',
      label: 'Adresse membre receveur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'idSecteurReceveur',
      label: 'Id secteur receveur',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'idSecteurReceveurMin',
      label: 'Id secteur receveur Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'idSecteurReceveurMax',
      label: 'Id secteur receveur Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'secteurReceveur',
      label: 'Secteur receveur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'descriptionSecteurReceveur',
      label: 'Description secteur receveur',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'contexte',
      label: 'Contexte',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'dateRdv',
      label: 'Date rdv',
      type: 'datetime-local',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'idStatut',
      label: 'Id statut',
      type: 'number',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'idStatutMin',
      label: 'Id statut Min',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'idStatutMax',
      label: 'Id statut Max',
      type: 'number',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
    { 
      key: 'statut',
      label: 'Statut',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'dateChangementStatut',
      label: 'Date changement statut',
      type: 'datetime-local',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'remarque',
      label: 'Remarque',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'historiqueStatuts',
      label: 'Historique statuts',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'creneaux',
      label: 'Creneaux',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
  ]
}



