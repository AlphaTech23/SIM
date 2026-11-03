import { BaseModel } from './BaseModel'
import { type EntitySearchField } from './EntityModel'
import { createSelectSearchFunction, createMulticriteriatSearchFunction } from './SelectOption'
import { TypeOffre } from './TypeOffreModel'
import * as typeOffreService from '@/services/TypeOffreService'
import { Membre } from './MembreModel'
import * as membreService from '@/services/MembreService'

export interface IOffre {
  id?: number
  idtypeoffreTypeOffres?: TypeOffre
  idmembreMembres?: Membre
  titreOffre?: string
  descriptions?: string
  conditions?: string
  dateCreation?: Date
  dateFinValidite?: string
}

export class Offre extends BaseModel implements IOffre {
  id?: number
  idtypeoffreTypeOffres?: TypeOffre
  idmembreMembres?: Membre
  titreOffre?: string
  descriptions?: string
  conditions?: string
  dateCreation?: Date
  dateFinValidite?: string

  constructor(data?: Partial<IOffre>) {
    super()
    this.id = data?.id
    this.idtypeoffreTypeOffres = data?.idtypeoffreTypeOffres
      ? new TypeOffre(data?.idtypeoffreTypeOffres)
      : undefined
    this.idmembreMembres = data?.idmembreMembres
      ? new Membre(data?.idmembreMembres)
      : undefined
    this.titreOffre = data?.titreOffre
    this.descriptions = data?.descriptions
    this.conditions = data?.conditions
    this.dateCreation = data?.dateCreation
    this.dateFinValidite = data?.dateFinValidite
  }

  static override getKey(): string {
    return 'id'
  }

  public override getKeyValue(): string {
    return String(this.id)
  }

  static override getReferenceKey(): string {
    return 'titreOffre'
  }

  public override getReferenceValue(): string {
    return String(this.titreOffre)
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
      key: 'idtypeoffreTypeOffres',
      label: 'Type offre',
      type: 'select',
      searchKey: TypeOffre.getKey(),
      sortable: false,
      selectSearch: createSelectSearchFunction(TypeOffre, typeOffreService),
      multicriteriaSelect: {
        filters: TypeOffre.getAllSearchFieldsMetadata(),
        searchFunction: createMulticriteriatSearchFunction(typeOffreService),
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
      key: 'titreOffre',
      label: 'Titre offre',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'descriptions',
      label: 'Descriptions',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'conditions',
      label: 'Conditions',
      type: 'text',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'dateCreation',
      label: 'Date creation',
      type: 'datetime-local',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'dateFinValidite',
      label: 'Date fin validite',
      type: 'date',
      sortable: true,
      showInTable: true,
      showInFilter: true 
    },
    { 
      key: 'dateFinValiditeMin',
      label: 'Date fin validite Min',
      type: 'date',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    { 
      key: 'dateFinValiditeMax',
      label: 'Date fin validite Max',
      type: 'date',
      sortable: false,
      showInTable: false,
      showInFilter: true 
    },
    
  ]
}


export class OffreFormDTO {
  id?: number
  idtypeoffreTypeOffres?: string
  
  idmembreMembres?: string
  
  titreOffre?: string
  descriptions?: string
  conditions?: string
  dateCreation?: Date
  dateFinValidite?: string

  constructor(data?: Partial<OffreFormDTO>) {
    this.id = data?.id
    this.idtypeoffreTypeOffres = data?.idtypeoffreTypeOffres ?? ""
    
    this.idmembreMembres = data?.idmembreMembres ?? ""
    
    this.titreOffre = data?.titreOffre
    this.descriptions = data?.descriptions
    this.conditions = data?.conditions
    this.dateCreation = data?.dateCreation
    this.dateFinValidite = data?.dateFinValidite ? data.dateFinValidite : undefined
    
  }

  static parse(data?: Partial<IOffre> | IOffre | null) {
    const instance = new OffreFormDTO()
    instance.id = data?.id
    instance.idtypeoffreTypeOffres = data?.idtypeoffreTypeOffres?.getKeyValue()?.toString()
    
    instance.idmembreMembres = data?.idmembreMembres?.getKeyValue()?.toString()
    
    instance.titreOffre = data?.titreOffre
    instance.descriptions = data?.descriptions
    instance.conditions = data?.conditions
    instance.dateCreation = data?.dateCreation
    instance.dateFinValidite = data?.dateFinValidite
    return instance
  }

  static parseList(data?: Offre[] | null): OffreFormDTO[] {
    if (!data || data.length === 0) return []
    return data.map((item) => OffreFormDTO.parse(item))
  }

  async toEntity(): Promise<Partial<Offre>> {
    const entity: Partial<Offre> = new Offre({
      id: this.id,
      
      titreOffre: this.titreOffre,
      
      descriptions: this.descriptions,
      
      conditions: this.conditions,
      
      dateCreation: this.dateCreation,
      
      dateFinValidite: this.dateFinValidite ? this.dateFinValidite.split('T')[0] : undefined,
      
    })

    if (this.idtypeoffreTypeOffres && this.idtypeoffreTypeOffres.length > 0) {
      const related = await typeOffreService.getById(Number(this.idtypeoffreTypeOffres));
      entity.idtypeoffreTypeOffres = related.data as TypeOffre;
    }
    if (this.idmembreMembres && this.idmembreMembres.length > 0) {
      const related = await membreService.getById(Number(this.idmembreMembres));
      entity.idmembreMembres = related.data as Membre;
    }
    return entity
  }

  static async toEntities(data?: OffreFormDTO[] | null){
      const entities: Promise<Partial<Offre>>[] = []
      data?.forEach(async (item) => {
        entities.push(item.toEntity())
      })
      return Promise.all(entities)
    }
}

