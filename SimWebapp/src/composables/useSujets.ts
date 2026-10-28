import { ref, watch } from 'vue'
import { Sujet, SujetFormDTO } from '@/models/SujetModel'
import * as sujetService from '@/services/SujetService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'
import { SujetsMembre, SujetsMembreFormDTO } from '@/models/SujetsMembreModel'



export const DEFAULT_LIST_VIEW_FIELDS = ['id']

/**
 * Composable to manage Sujet entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useSujets() {
  /** List of all sujets */
  const sujets = ref<Sujet[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_sujet') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_sujet', newValue)
  })

  /**
   * Handles API response by updating the sujets and pagination
   * @param data Array of Sujet returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: Sujet[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      sujets.value = sujets.value.concat(data)
    } else {
      sujets.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('sujet', 'list')
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
      const response = await columnConfigService.updateVisibleFields('sujet', 'list', fields)
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
  /** Navigate to sujet detail view */
  const viewSujet = (sujet: Sujet) => {
    router.push({ name: "sujetdetailsview", params: { id: sujet.getKeyValue() } })
  }

  /** Navigate to sujet list view */
  const goToListView = () => {
    router.push({ path: "/sujets" })
  }

  /** Navigate to sujet create form */
  const goToCreateFormView = () => {
    router.push({ path: "/sujets/create" })
  }

  /** Navigate to sujet update form */
  const goToUpdateFormView = (sujet: Sujet) => {
    router.push({ name: "sujetupdateview", params: { id: sujet.getKeyValue() } })
  }

  /**
   * Delete a sujet by id
   * @param sujet Sujet object to delete
   */
  const deleteSujet = async (sujet: Sujet) => {
    const result = await sujetService.remove(sujet.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Create a new sujet
   * @param fullData Object containing sujet and his details
   */
  const createSujet = async (fullData: { sujet: Partial<SujetFormDTO>; sujetsMembres: SujetsMembreFormDTO[] })=> {
    const dto = new SujetFormDTO(fullData.sujet)
    const sujet: Partial<Sujet> = await dto.toEntity()
    const sujetsMembres: Partial<SujetsMembre>[] = await SujetsMembreFormDTO.toEntities(fullData.sujetsMembres)
    
    const { data, error, errors } = await sujetService.create({ sujet, sujetsMembres })
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  /**
  * Update an existing sujet with full details
  * @param id Sujet ID to update
  * @param fullData Object containing sujet and its details
  */
  const updateSujet = async (
    id: number | string,
    fullData: {
      sujet: Partial<SujetFormDTO>
      ; sujetsMembres: SujetsMembreFormDTO[] 
    },
  ) => {
    const dto = new SujetFormDTO(fullData.sujet)
    const sujet: Partial<Sujet> = await dto.toEntity()

    const sujetsMembres: Partial<SujetsMembre>[] = await SujetsMembreFormDTO.toEntities(
      fullData.sujetsMembres,
    )
    
    const { data, error, errors } = await sujetService.update(
    id, 
    { 
      sujet
      , sujetsMembres
       
    }
   )
   if (error) {
    message.value = error
   }
   return { data, errors }
  }
  

  // API Requests
  /** Load all sujets from the API */
  const loadSujets = async (
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
    } = await sujetService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && sujets.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await sujetService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single sujet by id
   * @param id Sujet ID
   */
  const getSujetById = async (id: number | string) => {
    const result = await sujetService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search sujets with filters, pagination, and sorting
   * @param filters Partial Sujet object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchSujets = async (
    unpagined: boolean = false,
    filters: Partial<Sujet>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await sujetService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await sujetService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportSujetsToCsv = async (data: Partial<Sujet[]>) => {
    console.log('Exporting Sujet to CSV', data)
    await sujetService.exportCsv(data)
  }
  
  /** Getter for pagination data */
  const getPaginationData = () => paginationData.value

  return {
    // state
    sujets,
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
    exportSujetsToCsv,
    loadSujets,
    searchSujets,
    getSujetById,
    viewSujet,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deleteSujet,
    createSujet,
    updateSujet,
  }
}
