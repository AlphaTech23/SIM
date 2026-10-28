import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'
import { createSelectSearchFunction, createMulticriteriatSearchFunction } from './SelectOption'
import { RendezVou } from './RendezVouModel'
import * as rendezVouService from '@/services/RendezVouService'

export interface IPlageHoraire {
  id?: number
  idrendezvousRendezVous?: RendezVou
  jourDebut?: Date
  jourFin?: Date
}

export class PlageHoraire extends BaseModel implements IPlageHoraire {
  id?: number
  idrendezvousRendezVous?: RendezVou
  jourDebut?: Date
  jourFin?: Date

  constructor(data?: Partial<IPlageHoraire>) {
    super()
    this.id = data?.id
    this.idrendezvousRendezVous = data?.idrendezvousRendezVous
      ? new RendezVou(data?.idrendezvousRendezVous)
      : undefined
    this.jourDebut = data?.jourDebut
    this.jourFin = data?.jourFin
  }

  static override getKey(): string {
    return 'id'
  }

  public override getKeyValue(): string {
    return String(this.id)
  }

  static override getReferenceKey(): string {
    return 'jourDebut'
  }

  public override getReferenceValue(): string {
    return String(this.jourDebut)
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
      key: 'jourDebut',
      label: 'Jour debut',
      type: 'datetime-local',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'jourFin',
      label: 'Jour fin',
      type: 'datetime-local',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
  ]
}


export class PlageHoraireFormDTO {
  id?: number
  idrendezvousRendezVous?: string
  
  jourDebut?: Date
  jourFin?: Date

  constructor(data?: Partial<PlageHoraireFormDTO>) {
    this.id = data?.id
    this.idrendezvousRendezVous = data?.idrendezvousRendezVous ?? ""
    
    this.jourDebut = data?.jourDebut
    this.jourFin = data?.jourFin
  }

  static parse(data?: Partial<IPlageHoraire> | IPlageHoraire | null) {
    const instance = new PlageHoraireFormDTO()
    instance.id = data?.id
    instance.idrendezvousRendezVous = data?.idrendezvousRendezVous?.getKeyValue()?.toString()
    
    instance.jourDebut = data?.jourDebut
    instance.jourFin = data?.jourFin
    return instance
  }

  static parseList(data?: PlageHoraire[] | null): PlageHoraireFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => PlageHoraireFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<PlageHoraire>> {
    const entity: Partial<PlageHoraire> = new PlageHoraire({
      id: this.id,
      
      jourDebut: this.jourDebut,
      
      jourFin: this.jourFin,
      
    })

    if (this.idrendezvousRendezVous && this.idrendezvousRendezVous.length > 0) {
      const related = await rendezVouService.getById(Number(this.idrendezvousRendezVous));
      entity.idrendezvousRendezVous = related.data as RendezVou;
    }
    return entity
  }

  static async toEntities(data?: PlageHoraireFormDTO[] | null){
      const entities: Promise<Partial<PlageHoraire>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

