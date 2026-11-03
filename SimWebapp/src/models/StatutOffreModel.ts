import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'

export interface IStatutOffre {
  id?: number
  libele?: string
}

export class StatutOffre extends BaseModel implements IStatutOffre {
  id?: number
  libele?: string

  constructor(data?: Partial<IStatutOffre>) {
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


export class StatutOffreFormDTO {
  id?: number
  libele?: string

  constructor(data?: Partial<StatutOffreFormDTO>) {
    this.id = data?.id
    this.libele = data?.libele
  }

  static parse(data?: Partial<IStatutOffre> | IStatutOffre | null) {
    const instance = new StatutOffreFormDTO()
    instance.id = data?.id
    instance.libele = data?.libele
    return instance
  }

  static parseList(data?: StatutOffre[] | null): StatutOffreFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => StatutOffreFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<StatutOffre>> {
    const entity: Partial<StatutOffre> = new StatutOffre({
      id: this.id,
      
      libele: this.libele,
      
    })

    return entity
  }

  static async toEntities(data?: StatutOffreFormDTO[] | null){
      const entities: Promise<Partial<StatutOffre>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

