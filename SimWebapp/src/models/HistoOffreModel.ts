import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'
import { createSelectSearchFunction, createMulticriteriatSearchFunction } from './SelectOption'
import { Offre } from './OffreModel'
import * as offreService from '@/services/OffreService'
import { StatutOffre } from './StatutOffreModel'
import * as statutOffreService from '@/services/StatutOffreService'

export interface IHistoOffre {
  id?: number
  idoffreOffres?: Offre
  idstatutoffreStatutOffres?: StatutOffre
  date?: Date
  remarque?: string
}

export class HistoOffre extends BaseModel implements IHistoOffre {
  id?: number
  idoffreOffres?: Offre
  idstatutoffreStatutOffres?: StatutOffre
  date?: Date
  remarque?: string

  constructor(data?: Partial<IHistoOffre>) {
    super()
    this.id = data?.id
    this.idoffreOffres = data?.idoffreOffres
      ? new Offre(data?.idoffreOffres)
      : undefined
    this.idstatutoffreStatutOffres = data?.idstatutoffreStatutOffres
      ? new StatutOffre(data?.idstatutoffreStatutOffres)
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
      key: 'idoffreOffres',
      label: 'Offre',
      type: 'select',
      searchKey: Offre.getKey(),
      sortable: false,
      selectSearch: createSelectSearchFunction(Offre, offreService),
      multicriteriaSelect: {
        filters: Offre.getAllSearchFieldsMetadata(),
        searchFunction: createMulticriteriatSearchFunction(offreService),
      },
      showInTable: true,
      showInFilter: true,
    },
    
    {
      key: 'idstatutoffreStatutOffres',
      label: 'Statut offre',
      type: 'select',
      searchKey: StatutOffre.getKey(),
      sortable: false,
      selectSearch: createSelectSearchFunction(StatutOffre, statutOffreService),
      multicriteriaSelect: {
        filters: StatutOffre.getAllSearchFieldsMetadata(),
        searchFunction: createMulticriteriatSearchFunction(statutOffreService),
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


export class HistoOffreFormDTO {
  id?: number
  idoffreOffres?: string
  
  idstatutoffreStatutOffres?: string
  
  date?: Date
  remarque?: string

  constructor(data?: Partial<HistoOffreFormDTO>) {
    this.id = data?.id
    this.idoffreOffres = data?.idoffreOffres ?? ""
    
    this.idstatutoffreStatutOffres = data?.idstatutoffreStatutOffres ?? ""
    
    this.date = data?.date
    this.remarque = data?.remarque
  }

  static parse(data?: Partial<IHistoOffre> | IHistoOffre | null) {
    const instance = new HistoOffreFormDTO()
    instance.id = data?.id
    instance.idoffreOffres = data?.idoffreOffres?.getKeyValue()?.toString()
    
    instance.idstatutoffreStatutOffres = data?.idstatutoffreStatutOffres?.getKeyValue()?.toString()
    
    instance.date = data?.date
    instance.remarque = data?.remarque
    return instance
  }

  static parseList(data?: HistoOffre[] | null): HistoOffreFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => HistoOffreFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<HistoOffre>> {
    const entity: Partial<HistoOffre> = new HistoOffre({
      id: this.id,
      
      date: this.date,
      
      remarque: this.remarque,
      
    })

    if (this.idoffreOffres && this.idoffreOffres.length > 0) {
      const related = await offreService.getById(Number(this.idoffreOffres));
      entity.idoffreOffres = related.data as Offre;
    }
    if (this.idstatutoffreStatutOffres && this.idstatutoffreStatutOffres.length > 0) {
      const related = await statutOffreService.getById(Number(this.idstatutoffreStatutOffres));
      entity.idstatutoffreStatutOffres = related.data as StatutOffre;
    }
    return entity
  }

  static async toEntities(data?: HistoOffreFormDTO[] | null){
      const entities: Promise<Partial<HistoOffre>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

