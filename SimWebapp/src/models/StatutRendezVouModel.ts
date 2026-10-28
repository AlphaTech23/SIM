import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'
import { createSelectSearchFunction, createMulticriteriatSearchFunction } from './SelectOption'
import { RendezVou } from './RendezVouModel'
import * as rendezVouService from '@/services/RendezVouService'
import { Statut } from './StatutModel'
import * as statutService from '@/services/StatutService'

export interface IStatutRendezVou {
  id?: number
  idrendezvousRendezVous?: RendezVou
  idstatutStatut?: Statut
  date?: Date
  remarque?: string
}

export class StatutRendezVou extends BaseModel implements IStatutRendezVou {
  id?: number
  idrendezvousRendezVous?: RendezVou
  idstatutStatut?: Statut
  date?: Date
  remarque?: string

  constructor(data?: Partial<IStatutRendezVou>) {
    super()
    this.id = data?.id
    this.idrendezvousRendezVous = data?.idrendezvousRendezVous
      ? new RendezVou(data?.idrendezvousRendezVous)
      : undefined
    this.idstatutStatut = data?.idstatutStatut
      ? new Statut(data?.idstatutStatut)
      : undefined
    this.date = data?.date
    this.remarque = data?.remarque
  }

  static override getKey(): string {
    return 'id'
  }

  public override getKeyValue(): string {
    return String(this.id)
  }

  static override getReferenceKey(): string {
    return 'date'
  }

  public override getReferenceValue(): string {
    return String(this.date)
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
      key: 'idrendezvousRendezVous',
      label: 'Rendez vou',
      type: 'select',
      searchKey: RendezVou.getKey(),
      sortable: false,
      selectSearch: createSelectSearchFunction(RendezVou, rendezVouService),
      multicriteriaSelect: {
        filters: RendezVou.getAllSearchFieldsMetadata(),
        searchFunction: createMulticriteriatSearchFunction(rendezVouService),
      },
      showInTable: true,
      showInFilter: true,
    },
    
    {
      key: 'idstatutStatut',
      label: 'Statut',
      type: 'select',
      searchKey: Statut.getKey(),
      sortable: false,
      selectSearch: createSelectSearchFunction(Statut, statutService),
      multicriteriaSelect: {
        filters: Statut.getAllSearchFieldsMetadata(),
        searchFunction: createMulticriteriatSearchFunction(statutService),
      },
      showInTable: true,
      showInFilter: true,
    },
    
    { 
      key: 'date',
      label: 'Date',
      type: 'datetime-local',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'remarque',
      label: 'Remarque',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
  ]
}


export class StatutRendezVouFormDTO {
  id?: number
  idrendezvousRendezVous?: string
  
  idstatutStatut?: string
  
  date?: Date
  remarque?: string

  constructor(data?: Partial<StatutRendezVouFormDTO>) {
    this.id = data?.id
    this.idrendezvousRendezVous = data?.idrendezvousRendezVous ?? ""
    
    this.idstatutStatut = data?.idstatutStatut ?? ""
    
    this.date = data?.date
    this.remarque = data?.remarque
  }

  static parse(data?: Partial<IStatutRendezVou> | IStatutRendezVou | null) {
    const instance = new StatutRendezVouFormDTO()
    instance.id = data?.id
    instance.idrendezvousRendezVous = data?.idrendezvousRendezVous?.getKeyValue()?.toString()
    
    instance.idstatutStatut = data?.idstatutStatut?.getKeyValue()?.toString()
    
    instance.date = data?.date
    instance.remarque = data?.remarque
    return instance
  }

  static parseList(data?: StatutRendezVou[] | null): StatutRendezVouFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => StatutRendezVouFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<StatutRendezVou>> {
    const entity: Partial<StatutRendezVou> = new StatutRendezVou({
      id: this.id,
      
      date: this.date,
      
      remarque: this.remarque,
      
    })

    if (this.idrendezvousRendezVous && this.idrendezvousRendezVous.length > 0) {
      const related = await rendezVouService.getById(Number(this.idrendezvousRendezVous));
      entity.idrendezvousRendezVous = related.data as RendezVou;
    }
    if (this.idstatutStatut && this.idstatutStatut.length > 0) {
      const related = await statutService.getById(Number(this.idstatutStatut));
      entity.idstatutStatut = related.data as Statut;
    }
    return entity
  }

  static async toEntities(data?: StatutRendezVouFormDTO[] | null){
      const entities: Promise<Partial<StatutRendezVou>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

