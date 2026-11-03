import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'

export interface ITypeOffre {
  id?: number
  libele?: string
}

export class TypeOffre extends BaseModel implements ITypeOffre {
  id?: number
  libele?: string

  constructor(data?: Partial<ITypeOffre>) {
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


export class TypeOffreFormDTO {
  id?: number
  libele?: string

  constructor(data?: Partial<TypeOffreFormDTO>) {
    this.id = data?.id
    this.libele = data?.libele
  }

  static parse(data?: Partial<ITypeOffre> | ITypeOffre | null) {
    const instance = new TypeOffreFormDTO()
    instance.id = data?.id
    instance.libele = data?.libele
    return instance
  }

  static parseList(data?: TypeOffre[] | null): TypeOffreFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => TypeOffreFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<TypeOffre>> {
    const entity: Partial<TypeOffre> = new TypeOffre({
      id: this.id,
      
      libele: this.libele,
      
    })

    return entity
  }

  static async toEntities(data?: TypeOffreFormDTO[] | null){
      const entities: Promise<Partial<TypeOffre>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

