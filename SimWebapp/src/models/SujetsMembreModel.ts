import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'
import { createSelectSearchFunction, createMulticriteriatSearchFunction } from './SelectOption'
import { Membre } from './MembreModel'
import * as membreService from '@/services/MembreService'
import { Sujet } from './SujetModel'
import * as sujetService from '@/services/SujetService'

export interface ISujetsMembre {
  id?: number
  idmembreMembres?: Membre
  idsujetSujets?: Sujet
}

export class SujetsMembre extends BaseModel implements ISujetsMembre {
  id?: number
  idmembreMembres?: Membre
  idsujetSujets?: Sujet

  constructor(data?: Partial<ISujetsMembre>) {
    super()
    this.id = data?.id
    this.idmembreMembres = data?.idmembreMembres
      ? new Membre(data?.idmembreMembres)
      : undefined
    this.idsujetSujets = data?.idsujetSujets
      ? new Sujet(data?.idsujetSujets)
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
      key: 'idsujetSujets',
      label: 'Sujet',
      type: 'select',
      searchKey: Sujet.getKey(),
      sortable: false,
      selectSearch: createSelectSearchFunction(Sujet, sujetService),
      multicriteriaSelect: {
        filters: Sujet.getAllSearchFieldsMetadata(),
        searchFunction: createMulticriteriatSearchFunction(sujetService),
      },
      showInTable: true,
      showInFilter: true,
    },
    
  ]
}


export class SujetsMembreFormDTO {
  id?: number
  idmembreMembres?: string
  
  idsujetSujets?: string
  

  constructor(data?: Partial<SujetsMembreFormDTO>) {
    this.id = data?.id
    this.idmembreMembres = data?.idmembreMembres ?? ""
    
    this.idsujetSujets = data?.idsujetSujets ?? ""
    
  }

  static parse(data?: Partial<ISujetsMembre> | ISujetsMembre | null) {
    const instance = new SujetsMembreFormDTO()
    instance.id = data?.id
    instance.idmembreMembres = data?.idmembreMembres?.getKeyValue()?.toString()
    
    instance.idsujetSujets = data?.idsujetSujets?.getKeyValue()?.toString()
    
    return instance
  }

  static parseList(data?: SujetsMembre[] | null): SujetsMembreFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => SujetsMembreFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<SujetsMembre>> {
    const entity: Partial<SujetsMembre> = new SujetsMembre({
      id: this.id,
      
    })

    if (this.idmembreMembres && this.idmembreMembres.length > 0) {
      const related = await membreService.getById(Number(this.idmembreMembres));
      entity.idmembreMembres = related.data as Membre;
    }
    if (this.idsujetSujets && this.idsujetSujets.length > 0) {
      const related = await sujetService.getById(Number(this.idsujetSujets));
      entity.idsujetSujets = related.data as Sujet;
    }
    return entity
  }

  static async toEntities(data?: SujetsMembreFormDTO[] | null){
      const entities: Promise<Partial<SujetsMembre>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

