import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'

export interface IActivite {
  id?: number
  libele?: string
  description?: string
}

export class Activite extends BaseModel implements IActivite {
  id?: number
  libele?: string
  description?: string

  constructor(data?: Partial<IActivite>) {
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


export class ActiviteFormDTO {
  id?: number
  libele?: string
  description?: string

  constructor(data?: Partial<ActiviteFormDTO>) {
    this.id = data?.id
    this.libele = data?.libele
    this.description = data?.description
  }

  static parse(data?: Partial<IActivite> | IActivite | null) {
    const instance = new ActiviteFormDTO()
    instance.id = data?.id
    instance.libele = data?.libele
    instance.description = data?.description
    return instance
  }

  static parseList(data?: Activite[] | null): ActiviteFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => ActiviteFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<Activite>> {
    const entity: Partial<Activite> = new Activite({
      id: this.id,
      
      libele: this.libele,
      
      description: this.description,
      
    })

    return entity
  }

  static async toEntities(data?: ActiviteFormDTO[] | null){
      const entities: Promise<Partial<Activite>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

