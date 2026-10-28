import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'
import { createSelectSearchFunction, createMulticriteriatSearchFunction } from './SelectOption'
import { Membre } from './MembreModel'
import * as membreService from '@/services/MembreService'
import { Activite } from './ActiviteModel'
import * as activiteService from '@/services/ActiviteService'

export interface IActivitesMembre {
  id?: number
  idmembreMembres?: Membre
  idactiviteActivites?: Activite
}

export class ActivitesMembre extends BaseModel implements IActivitesMembre {
  id?: number
  idmembreMembres?: Membre
  idactiviteActivites?: Activite

  constructor(data?: Partial<IActivitesMembre>) {
    super()
    this.id = data?.id
    this.idmembreMembres = data?.idmembreMembres
      ? new Membre(data?.idmembreMembres)
      : undefined
    this.idactiviteActivites = data?.idactiviteActivites
      ? new Activite(data?.idactiviteActivites)
      : undefined
  }

  static override getKey(): string {
    return 'id'
  }

  public override getKeyValue(): string {
    return String(this.id)
  }

  static override getReferenceKey(): string {
  }

  public override getReferenceValue(): string {
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
      key: 'idmembreMembres',
      label: 'Membre',
      type: 'select',
      searchKey: Membre.getKey(),
      sortable: false,
      selectSearch: createSelectSearchFunction(Membre, membreService),
      multicriteriaSelect: {
        filters: Membre.getAllSearchFieldsMetadata(),
        searchFunction: createMulticriteriatSearchFunction(membreService),
      },
      showInTable: true,
      showInFilter: true,
    },
    
    {
      key: 'idactiviteActivites',
      label: 'Activite',
      type: 'select',
      searchKey: Activite.getKey(),
      sortable: false,
      selectSearch: createSelectSearchFunction(Activite, activiteService),
      multicriteriaSelect: {
        filters: Activite.getAllSearchFieldsMetadata(),
        searchFunction: createMulticriteriatSearchFunction(activiteService),
      },
      showInTable: true,
      showInFilter: true,
    },
    
  ]
}


export class ActivitesMembreFormDTO {
  id?: number
  idmembreMembres?: string
  
  idactiviteActivites?: string
  

  constructor(data?: Partial<ActivitesMembreFormDTO>) {
    this.id = data?.id
    this.idmembreMembres = data?.idmembreMembres ?? ""
    
    this.idactiviteActivites = data?.idactiviteActivites ?? ""
    
  }

  static parse(data?: Partial<IActivitesMembre> | IActivitesMembre | null) {
    const instance = new ActivitesMembreFormDTO()
    instance.id = data?.id
    instance.idmembreMembres = data?.idmembreMembres?.getKeyValue()?.toString()
    
    instance.idactiviteActivites = data?.idactiviteActivites?.getKeyValue()?.toString()
    
    return instance
  }

  static parseList(data?: ActivitesMembre[] | null): ActivitesMembreFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => ActivitesMembreFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<ActivitesMembre>> {
    const entity: Partial<ActivitesMembre> = new ActivitesMembre({
      id: this.id,
      
    })

    if (this.idmembreMembres && this.idmembreMembres.length > 0) {
      const related = await membreService.getById(Number(this.idmembreMembres));
      entity.idmembreMembres = related.data as Membre;
    }
    if (this.idactiviteActivites && this.idactiviteActivites.length > 0) {
      const related = await activiteService.getById(Number(this.idactiviteActivites));
      entity.idactiviteActivites = related.data as Activite;
    }
    return entity
  }

  static async toEntities(data?: ActivitesMembreFormDTO[] | null){
      const entities: Promise<Partial<ActivitesMembre>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

