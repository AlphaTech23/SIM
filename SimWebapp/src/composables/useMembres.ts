import { ref, watch } from 'vue'
import { Membre, MembreFormDTO } from '@/models/MembreModel'
import * as membreService from '@/services/MembreService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'
import { ActivitesMembre, ActivitesMembreFormDTO } from '@/models/ActivitesMembreModel'
import { RendezVou, RendezVouFormDTO } from '@/models/RendezVouModel'
import { SujetsMembre, SujetsMembreFormDTO } from '@/models/SujetsMembreModel'



export const DEFAULT_LIST_VIEW_FIELDS = ['id']

/**
 * Composable to manage Membre entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useMembres() {
  /** List of all membres */
  const membres = ref<Membre[]>([])

  /** Loading state management */
  const { loading, startLoading, stopLoading } = useLoading()

  /** Pagination information returned by API */
  const paginationData = ref(new PaginationData())

  /** Message for errors or notifications */
  const message = ref<string | null>(null)

  /** Vue router instance for navigation */
  const router = useRouter()

  const visibleListFields = ref<string[]>([...DEFAULT_LIST_VIEW_FIELDS])

  // On utilise une clé spécifique à l'entité pour plus de flexibilité future
  const savedLayoutMode = localStorage.getItem('layoutMode_membre') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_membre', newValue)
  })

  /**
   * Handles API response by updating the membres and pagination
   * @param data Array of Membre returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: Membre[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      membres.value = membres.value.concat(data)
    } else {
      membres.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('membre', 'list')
      if (response.returnCode === 1 && response.data && response.data.length > 0) {
        visibleListFields.value = response.data
      }
    } catch (error) {
      console.warn('Impossible de charger la config des colonnes, utilisation des valeurs par défaut.', error)
    }
  }

  /** Sauvegarde la nouvelle configuration vers le serveur */
  const saveColumnConfig = async (fields: string[]) => {
    try {
      const response = await columnConfigService.updateVisibleFields('membre', 'list', fields)
      if (response.returnCode === 1) {
        visibleListFields.value = fields
        return { success: true, error: null }
      } else {
        return { success: false, error: response.message || 'Erreur lors de la sauvegarde' }
      }
    } catch (error) {
      return { success: false, error: 'Erreur réseau lors de la sauvegarde' }
    }
  }

  // Navigation functions
  /** Navigate to membre detail view */
  const viewMembre = (membre: Membre) => {
    router.push({ name: "membredetailsview", params: { id: membre.getKeyValue() } })
  }

  /** Navigate to membre list view */
  const goToListView = () => {
    router.push({ path: "/membres" })
  }

  /** Navigate to membre create form */
  const goToCreateFormView = () => {
    router.push({ path: "/membres/create" })
  }

  /** Navigate to membre update form */
  const goToUpdateFormView = (membre: Membre) => {
    router.push({ name: "membreupdateview", params: { id: membre.getKeyValue() } })
  }

  /**
   * Delete a membre by id
   * @param membre Membre object to delete
   */
  const deleteMembre = async (membre: Membre) => {
    const result = await membreService.remove(membre.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Create a new membre
   * @param fullData Object containing membre and his details
   */
  const createMembre = async (fullData: { membre: Partial<MembreFormDTO>; activitesMembres: ActivitesMembreFormDTO[]; rendezVous: RendezVouFormDTO[]; sujetsMembres: SujetsMembreFormDTO[] })=> {
    const dto = new MembreFormDTO(fullData.membre)
    const membre: Partial<Membre> = await dto.toEntity()
    const activitesMembres: Partial<ActivitesMembre>[] = await ActivitesMembreFormDTO.toEntities(fullData.activitesMembres)
    const rendezVous: Partial<RendezVou>[] = await RendezVouFormDTO.toEntities(fullData.rendezVous)
    const sujetsMembres: Partial<SujetsMembre>[] = await SujetsMembreFormDTO.toEntities(fullData.sujetsMembres)
    
    const { data, error, errors } = await membreService.create({ membre, activitesMembres, rendezVous, sujetsMembres })
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  /**
  * Update an existing membre with full details
  * @param id Membre ID to update
  * @param fullData Object containing membre and its details
  */
  const updateMembre = async (
    id: number | string,
    fullData: {
      membre: Partial<MembreFormDTO>
      ; activitesMembres: ActivitesMembreFormDTO[]; rendezVous: RendezVouFormDTO[]; sujetsMembres: SujetsMembreFormDTO[] 
    },
  ) => {
    const dto = new MembreFormDTO(fullData.membre)
    const membre: Partial<Membre> = await dto.toEntity()

    const activitesMembres: Partial<ActivitesMembre>[] = await ActivitesMembreFormDTO.toEntities(
      fullData.activitesMembres,
    )
    const rendezVous: Partial<RendezVou>[] = await RendezVouFormDTO.toEntities(
      fullData.rendezVous,
    )
    const sujetsMembres: Partial<SujetsMembre>[] = await SujetsMembreFormDTO.toEntities(
      fullData.sujetsMembres,
    )
    
    const { data, error, errors } = await membreService.update(
    id, 
    { 
      membre
      , activitesMembres
      , rendezVous
      , sujetsMembres
       
    }
   )
   if (error) {
    message.value = error
   }
   return { data, errors }
  }
  

  // API Requests
  /** Load all membres from the API */
  const loadMembres = async (
    unpagined: boolean = false,
    pagination?: PaginationRequestParameter,
    sortFields?: SortFieldParameter[],
  ): Promise<void> => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await membreService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && membres.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await membreService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single membre by id
   * @param id Membre ID
   */
  const getMembreById = async (id: number | string) => {
    const result = await membreService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search membres with filters, pagination, and sorting
   * @param filters Partial Membre object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchMembres = async (
    unpagined: boolean = false,
    filters: Partial<Membre>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await membreService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await membreService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportMembresToCsv = async (data: Partial<Membre[]>) => {
    console.log('Exporting Membre to CSV', data)
    await membreService.exportCsv(data)
  }
  
  const getAllMembresBySecteurId = async (
    secteurId: number | string | undefined,
    unpagined: boolean = false,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await membreService.getAllBySecteurId(secteurId, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext() && membres.value.length > 0) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await membreService.getAllBySecteurId(
          secteurId,
          paginationData.value.toParameter(),
          sortFields,
        )
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  
  /** Getter for pagination data */
  const getPaginationData = () => paginationData.value

  return {
    // state
    membres,
    loading,
    message,
    paginationData,
    layoutMode,

    // State & function of column configuration
    visibleListFields,
    loadColumnConfig,
    saveColumnConfig,

    // Actions
    getPaginationData,
    exportMembresToCsv,
    loadMembres,
    searchMembres,
    getMembreById,
    viewMembre,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deleteMembre,
    createMembre,
    updateMembre,
    getAllMembresBySecteurId,
    
  }
}
