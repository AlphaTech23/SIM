import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'
import { createSelectSearchFunction, createMulticriteriatSearchFunction } from './SelectOption'
import { Offre } from './OffreModel'
import * as offreService from '@/services/OffreService'
import { Membre } from './MembreModel'
import * as membreService from '@/services/MembreService'

export interface ICiblesOffre {
  id?: number
  idoffreOffres?: Offre
  idmembreMembres?: Membre
  dateEnvoi?: Date
}

export class CiblesOffre extends BaseModel implements ICiblesOffre {
  id?: number
  idoffreOffres?: Offre
  idmembreMembres?: Membre
  dateEnvoi?: Date

  constructor(data?: Partial<ICiblesOffre>) {
    super()
    this.id = data?.id
    this.idoffreOffres = data?.idoffreOffres
      ? new Offre(data?.idoffreOffres)
      : undefined
    this.idmembreMembres = data?.idmembreMembres
      ? new Membre(data?.idmembreMembres)
      : undefined
    this.dateEnvoi = data?.dateEnvoi
  }

  static override getKey(): string {
    return 'id'
  }

  public override getKeyValue(): string {
    return String(this.id)
  }

  static override getReferenceKey(): string {
    return 'dateEnvoi'
  }

  public override getReferenceValue(): string {
    return String(this.dateEnvoi)
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
      key: 'dateEnvoi',
      label: 'Date envoi',
      type: 'datetime-local',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
  ]
}


export class CiblesOffreFormDTO {
  id?: number
  idoffreOffres?: string
  
  idmembreMembres?: string
  
  dateEnvoi?: Date

  constructor(data?: Partial<CiblesOffreFormDTO>) {
    this.id = data?.id
    this.idoffreOffres = data?.idoffreOffres ?? ""
    
    this.idmembreMembres = data?.idmembreMembres ?? ""
    
    this.dateEnvoi = data?.dateEnvoi
  }

  static parse(data?: Partial<ICiblesOffre> | ICiblesOffre | null) {
    const instance = new CiblesOffreFormDTO()
    instance.id = data?.id
    instance.idoffreOffres = data?.idoffreOffres?.getKeyValue()?.toString()
    
    instance.idmembreMembres = data?.idmembreMembres?.getKeyValue()?.toString()
    
    instance.dateEnvoi = data?.dateEnvoi
    return instance
  }

  static parseList(data?: CiblesOffre[] | null): CiblesOffreFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => CiblesOffreFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<CiblesOffre>> {
    const entity: Partial<CiblesOffre> = new CiblesOffre({
      id: this.id,
      
      dateEnvoi: this.dateEnvoi,
      
    })

    if (this.idoffreOffres && this.idoffreOffres.length > 0) {
      const related = await offreService.getById(Number(this.idoffreOffres));
      entity.idoffreOffres = related.data as Offre;
    }
    if (this.idmembreMembres && this.idmembreMembres.length > 0) {
      const related = await membreService.getById(Number(this.idmembreMembres));
      entity.idmembreMembres = related.data as Membre;
    }
    return entity
  }

  static async toEntities(data?: CiblesOffreFormDTO[] | null){
      const entities: Promise<Partial<CiblesOffre>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

