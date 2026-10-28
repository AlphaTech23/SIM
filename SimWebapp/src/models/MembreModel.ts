import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'
import { createSelectSearchFunction, createMulticriteriatSearchFunction } from './SelectOption'
import { Secteur } from './SecteurModel'
import * as secteurService from '@/services/SecteurService'

export interface IMembre {
  id?: number
  idsecteurSecteurs?: Secteur
  intitule?: string
  logo?: string
  telephone?: string
  email?: string
  whatsapp?: string
  adresse?: string
}

export class Membre extends BaseModel implements IMembre {
  id?: number
  idsecteurSecteurs?: Secteur
  intitule?: string
  logo?: string
  telephone?: string
  email?: string
  whatsapp?: string
  adresse?: string

  constructor(data?: Partial<IMembre>) {
    super()
    this.id = data?.id
    this.idsecteurSecteurs = data?.idsecteurSecteurs
      ? new Secteur(data?.idsecteurSecteurs)
      : undefined
    this.intitule = data?.intitule
    this.logo = data?.logo
    this.telephone = data?.telephone
    this.email = data?.email
    this.whatsapp = data?.whatsapp
    this.adresse = data?.adresse
  }

  static override getKey(): string {
    return 'id'
  }

  public override getKeyValue(): string {
    return String(this.id)
  }

  static override getReferenceKey(): string {
    return 'intitule'
  }

  public override getReferenceValue(): string {
    return String(this.intitule)
  }

  protected static override searchDaoMetadata: EntitySearchField[] = [
    {
      key: 'id',
      label: 'Id',
      type: 'string',
      sortable: true,
      showInTable: true,
      showInFilter: true,
      identifier: true 
    },
    
    {
      key: 'idsecteurSecteurs',
      label: 'Secteur',
      type: 'select',
      searchKey: Secteur.getKey(),
      sortable: false,
      selectSearch: createSelectSearchFunction(Secteur, secteurService),
      multicriteriaSelect: {
        filters: Secteur.getAllSearchFieldsMetadata(),
        searchFunction: createMulticriteriatSearchFunction(secteurService),
      },
      showInTable: true,
      showInFilter: true,
    },
    
    { 
      key: 'intitule',
      label: 'Intitule',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'logo',
      label: 'Logo',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'telephone',
      label: 'Telephone',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'email',
      label: 'Email',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'whatsapp',
      label: 'Whatsapp',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'adresse',
      label: 'Adresse',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
  ]
}


export class MembreFormDTO {
  id?: number
  idsecteurSecteurs?: string
  
  intitule?: string
  logo?: string
  telephone?: string
  email?: string
  whatsapp?: string
  adresse?: string

  constructor(data?: Partial<MembreFormDTO>) {
    this.id = data?.id
    this.idsecteurSecteurs = data?.idsecteurSecteurs ?? ""
    
    this.intitule = data?.intitule
    this.logo = data?.logo
    this.telephone = data?.telephone
    this.email = data?.email
    this.whatsapp = data?.whatsapp
    this.adresse = data?.adresse
  }

  static parse(data?: Partial<IMembre> | IMembre | null) {
    const instance = new MembreFormDTO()
    instance.id = data?.id
    instance.idsecteurSecteurs = data?.idsecteurSecteurs?.getKeyValue()?.toString()
    
    instance.intitule = data?.intitule
    instance.logo = data?.logo
    instance.telephone = data?.telephone
    instance.email = data?.email
    instance.whatsapp = data?.whatsapp
    instance.adresse = data?.adresse
    return instance
  }

  static parseList(data?: Membre[] | null): MembreFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => MembreFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<Membre>> {
    const entity: Partial<Membre> = new Membre({
      id: this.id,
      
      intitule: this.intitule,
      
      logo: this.logo,
      
      telephone: this.telephone,
      
      email: this.email,
      
      whatsapp: this.whatsapp,
      
      adresse: this.adresse,
      
    })

    if (this.idsecteurSecteurs && this.idsecteurSecteurs.length > 0) {
      const related = await secteurService.getById(Number(this.idsecteurSecteurs));
      entity.idsecteurSecteurs = related.data as Secteur;
    }
    return entity
  }

  static async toEntities(data?: MembreFormDTO[] | null){
      const entities: Promise<Partial<Membre>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

