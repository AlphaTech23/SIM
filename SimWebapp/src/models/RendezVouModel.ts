import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'
import { createSelectSearchFunction, createMulticriteriatSearchFunction } from './SelectOption'
import { Membre } from './MembreModel'
import * as membreService from '@/services/MembreService'
import { SujetsMembre } from './SujetsMembreModel'
import * as sujetsMembreService from '@/services/SujetsMembreService'

export interface IRendezVou {
  id?: number
  iddemandeurMembres?: Membre
  idreceveurSujetsMembres?: SujetsMembre
  contexte?: string
  dateRdv?: Date
}

export class RendezVou extends BaseModel implements IRendezVou {
  id?: number
  iddemandeurMembres?: Membre
  idreceveurSujetsMembres?: SujetsMembre
  contexte?: string
  dateRdv?: Date

  constructor(data?: Partial<IRendezVou>) {
    super()
    this.id = data?.id
    this.iddemandeurMembres = data?.iddemandeurMembres
      ? new Membre(data?.iddemandeurMembres)
      : undefined
    this.idreceveurSujetsMembres = data?.idreceveurSujetsMembres
      ? new SujetsMembre(data?.idreceveurSujetsMembres)
      : undefined
    this.contexte = data?.contexte
    this.dateRdv = data?.dateRdv
  }

  static override getKey(): string {
    return 'id'
  }

  public override getKeyValue(): string {
    return String(this.id)
  }

  static override getReferenceKey(): string {
    return 'contexte'
  }

  public override getReferenceValue(): string {
    return String(this.contexte)
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
      key: 'iddemandeurMembres',
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
      key: 'idreceveurSujetsMembres',
      label: 'Sujets membre',
      type: 'select',
      searchKey: SujetsMembre.getKey(),
      sortable: false,
      selectSearch: createSelectSearchFunction(SujetsMembre, sujetsMembreService),
      multicriteriaSelect: {
        filters: SujetsMembre.getAllSearchFieldsMetadata(),
        searchFunction: createMulticriteriatSearchFunction(sujetsMembreService),
      },
      showInTable: true,
      showInFilter: true,
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
  ]
}


export class RendezVouFormDTO {
  id?: number
  iddemandeurMembres?: string
  
  idreceveurSujetsMembres?: string
  
  contexte?: string
  dateRdv?: Date

  constructor(data?: Partial<RendezVouFormDTO>) {
    this.id = data?.id
    this.iddemandeurMembres = data?.iddemandeurMembres ?? ""
    
    this.idreceveurSujetsMembres = data?.idreceveurSujetsMembres ?? ""
    
    this.contexte = data?.contexte
    this.dateRdv = data?.dateRdv
  }

  static parse(data?: Partial<IRendezVou> | IRendezVou | null) {
    const instance = new RendezVouFormDTO()
    instance.id = data?.id
    instance.iddemandeurMembres = data?.iddemandeurMembres?.getKeyValue()?.toString()
    
    instance.idreceveurSujetsMembres = data?.idreceveurSujetsMembres?.getKeyValue()?.toString()
    
    instance.contexte = data?.contexte
    instance.dateRdv = data?.dateRdv
    return instance
  }

  static parseList(data?: RendezVou[] | null): RendezVouFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => RendezVouFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<RendezVou>> {
    const entity: Partial<RendezVou> = new RendezVou({
      id: this.id,
      
      contexte: this.contexte,
      
      dateRdv: this.dateRdv,
      
    })

    if (this.iddemandeurMembres && this.iddemandeurMembres.length > 0) {
      const related = await membreService.getById(Number(this.iddemandeurMembres));
      entity.iddemandeurMembres = related.data as Membre;
    }
    if (this.idreceveurSujetsMembres && this.idreceveurSujetsMembres.length > 0) {
      const related = await sujetsMembreService.getById(Number(this.idreceveurSujetsMembres));
      entity.idreceveurSujetsMembres = related.data as SujetsMembre;
    }
    return entity
  }

  static async toEntities(data?: RendezVouFormDTO[] | null){
      const entities: Promise<Partial<RendezVou>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

