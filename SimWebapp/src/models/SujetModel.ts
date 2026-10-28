import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'

export interface ISujet {
  id?: number
  libele?: string
  description?: string
  nom?: string
  prenom?: string
  fonction?: string
  telephone?: string
  email?: string
  whatsapp?: string
  adresse?: string
}

export class Sujet extends BaseModel implements ISujet {
  id?: number
  libele?: string
  description?: string
  nom?: string
  prenom?: string
  fonction?: string
  telephone?: string
  email?: string
  whatsapp?: string
  adresse?: string

  constructor(data?: Partial<ISujet>) {
    super()
    this.id = data?.id
    this.libele = data?.libele
    this.description = data?.description
    this.nom = data?.nom
    this.prenom = data?.prenom
    this.fonction = data?.fonction
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
    return 'libele'
  }

  public override getReferenceValue(): string {
    return String(this.libele)
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
      key: 'libele',
      label: 'Libele',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'description',
      label: 'Description',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'nom',
      label: 'Nom',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'prenom',
      label: 'Prenom',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'fonction',
      label: 'Fonction',
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


export class SujetFormDTO {
  id?: number
  libele?: string
  description?: string
  nom?: string
  prenom?: string
  fonction?: string
  telephone?: string
  email?: string
  whatsapp?: string
  adresse?: string

  constructor(data?: Partial<SujetFormDTO>) {
    this.id = data?.id
    this.libele = data?.libele
    this.description = data?.description
    this.nom = data?.nom
    this.prenom = data?.prenom
    this.fonction = data?.fonction
    this.telephone = data?.telephone
    this.email = data?.email
    this.whatsapp = data?.whatsapp
    this.adresse = data?.adresse
  }

  static parse(data?: Partial<ISujet> | ISujet | null) {
    const instance = new SujetFormDTO()
    instance.id = data?.id
    instance.libele = data?.libele
    instance.description = data?.description
    instance.nom = data?.nom
    instance.prenom = data?.prenom
    instance.fonction = data?.fonction
    instance.telephone = data?.telephone
    instance.email = data?.email
    instance.whatsapp = data?.whatsapp
    instance.adresse = data?.adresse
    return instance
  }

  static parseList(data?: Sujet[] | null): SujetFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => SujetFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<Sujet>> {
    const entity: Partial<Sujet> = new Sujet({
      id: this.id,
      
      libele: this.libele,
      
      description: this.description,
      
      nom: this.nom,
      
      prenom: this.prenom,
      
      fonction: this.fonction,
      
      telephone: this.telephone,
      
      email: this.email,
      
      whatsapp: this.whatsapp,
      
      adresse: this.adresse,
      
    })

    return entity
  }

  static async toEntities(data?: SujetFormDTO[] | null){
      const entities: Promise<Partial<Sujet>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

