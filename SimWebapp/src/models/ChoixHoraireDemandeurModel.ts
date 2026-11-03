import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'
import { createSelectSearchFunction, createMulticriteriatSearchFunction } from './SelectOption'
import { RendezVou } from './RendezVouModel'
import * as rendezVouService from '@/services/RendezVouService'

export interface IChoixHoraireDemandeur {
  id?: number
  idrendezvousRendezVous?: RendezVou
  dateChoix?: Date
}

export class ChoixHoraireDemandeur extends BaseModel implements IChoixHoraireDemandeur {
  id?: number
  idrendezvousRendezVous?: RendezVou
  dateChoix?: Date

  constructor(data?: Partial<IChoixHoraireDemandeur>) {
    super()
    this.id = data?.id
    this.idrendezvousRendezVous = data?.idrendezvousRendezVous
      ? new RendezVou(data?.idrendezvousRendezVous)
      : undefined
    this.dateChoix = data?.dateChoix
  }

  static override getKey(): string {
    return 'id'
  }

  public override getKeyValue(): string {
    return String(this.id)
  }

  static override getReferenceKey(): string {
    return 'dateChoix'
  }

  public override getReferenceValue(): string {
    return String(this.dateChoix)
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
      key: 'dateChoix',
      label: 'Date choix',
      type: 'datetime-local',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
  ]
}


export class ChoixHoraireDemandeurFormDTO {
  id?: number
  idrendezvousRendezVous?: string
  
  dateChoix?: Date

  constructor(data?: Partial<ChoixHoraireDemandeurFormDTO>) {
    this.id = data?.id
    this.idrendezvousRendezVous = data?.idrendezvousRendezVous ?? ""
    
    this.dateChoix = data?.dateChoix
  }

  static parse(data?: Partial<IChoixHoraireDemandeur> | IChoixHoraireDemandeur | null) {
    const instance = new ChoixHoraireDemandeurFormDTO()
    instance.id = data?.id
    instance.idrendezvousRendezVous = data?.idrendezvousRendezVous?.getKeyValue()?.toString()
    
    instance.dateChoix = data?.dateChoix
    return instance
  }

  static parseList(data?: ChoixHoraireDemandeur[] | null): ChoixHoraireDemandeurFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => ChoixHoraireDemandeurFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<ChoixHoraireDemandeur>> {
    const entity: Partial<ChoixHoraireDemandeur> = new ChoixHoraireDemandeur({
      id: this.id,
      
      dateChoix: this.dateChoix,
      
    })

    if (this.idrendezvousRendezVous && this.idrendezvousRendezVous.length > 0) {
      const related = await rendezVouService.getById(Number(this.idrendezvousRendezVous));
      entity.idrendezvousRendezVous = related.data as RendezVou;
    }
    return entity
  }

  static async toEntities(data?: ChoixHoraireDemandeurFormDTO[] | null){
      const entities: Promise<Partial<ChoixHoraireDemandeur>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

