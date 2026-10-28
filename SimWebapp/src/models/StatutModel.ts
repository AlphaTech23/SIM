import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'

export interface IStatut {
  id?: number
  libele?: string
}

export class Statut extends BaseModel implements IStatut {
  id?: number
  libele?: string

  constructor(data?: Partial<IStatut>) {
    super()
    this.id = data?.id
    this.libele = data?.libele
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
  ]
}


export class StatutFormDTO {
  id?: number
  libele?: string

  constructor(data?: Partial<StatutFormDTO>) {
    this.id = data?.id
    this.libele = data?.libele
  }

  static parse(data?: Partial<IStatut> | IStatut | null) {
    const instance = new StatutFormDTO()
    instance.id = data?.id
    instance.libele = data?.libele
    return instance
  }

  static parseList(data?: Statut[] | null): StatutFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => StatutFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<Statut>> {
    const entity: Partial<Statut> = new Statut({
      id: this.id,
      
      libele: this.libele,
      
    })

    return entity
  }

  static async toEntities(data?: StatutFormDTO[] | null){
      const entities: Promise<Partial<Statut>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

