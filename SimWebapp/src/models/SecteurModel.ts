import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'

export interface ISecteur {
  id?: number
  libele?: string
  description?: string
}

export class Secteur extends BaseModel implements ISecteur {
  id?: number
  libele?: string
  description?: string

  constructor(data?: Partial<ISecteur>) {
    super()
    this.id = data?.id
    this.libele = data?.libele
    this.description = data?.description
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
  ]
}


export class SecteurFormDTO {
  id?: number
  libele?: string
  description?: string

  constructor(data?: Partial<SecteurFormDTO>) {
    this.id = data?.id
    this.libele = data?.libele
    this.description = data?.description
  }

  static parse(data?: Partial<ISecteur> | ISecteur | null) {
    const instance = new SecteurFormDTO()
    instance.id = data?.id
    instance.libele = data?.libele
    instance.description = data?.description
    return instance
  }

  static parseList(data?: Secteur[] | null): SecteurFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => SecteurFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<Secteur>> {
    const entity: Partial<Secteur> = new Secteur({
      id: this.id,
      
      libele: this.libele,
      
      description: this.description,
      
    })

    return entity
  }

  static async toEntities(data?: SecteurFormDTO[] | null){
      const entities: Promise<Partial<Secteur>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

