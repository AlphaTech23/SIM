import { ref, watch } from 'vue'
import { SujetsMembre, SujetsMembreFormDTO } from '@/models/SujetsMembreModel'
import * as sujetsMembreService from '@/services/SujetsMembreService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['id']

/**
 * Composable to manage SujetsMembre entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useSujetsMembres() {
  /** List of all sujetsMembres */
  const sujetsMembres = ref<SujetsMembre[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_sujetsMembre') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_sujetsMembre', newValue)
  })

  /**
   * Handles API response by updating the sujetsMembres and pagination
   * @param data Array of SujetsMembre returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: SujetsMembre[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      sujetsMembres.value = sujetsMembres.value.concat(data)
    } else {
      sujetsMembres.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('sujetsMembre', 'list')
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
      const response = await columnConfigService.updateVisibleFields('sujetsMembre', 'list', fields)
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
  /** Navigate to sujetsMembre detail view */
  const viewSujetsMembre = (sujetsMembre: SujetsMembre) => {
    router.push({ name: "sujetsMembredetailsview", params: { id: sujetsMembre.getKeyValue() } })
  }

  /** Navigate to sujetsMembre list view */
  const goToListView = () => {
    router.push({ path: "/sujetsMembres" })
  }

  /** Navigate to sujetsMembre create form */
  const goToCreateFormView = () => {
    router.push({ path: "/sujetsMembres/create" })
  }

  /** Navigate to sujetsMembre update form */
  const goToUpdateFormView = (sujetsMembre: SujetsMembre) => {
    router.push({ name: "sujetsMembreupdateview", params: { id: sujetsMembre.getKeyValue() } })
  }

  /**
   * Delete a sujetsMembre by id
   * @param sujetsMembre SujetsMembre object to delete
   */
  const deleteSujetsMembre = async (sujetsMembre: SujetsMembre) => {
    const result = await sujetsMembreService.remove(sujetsMembre.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  
  /**
   * Create a new sujetsMembre
   * @param sujetsMembreFormDTO SujetsMembre object to create
   */
  const createSujetsMembre = async (sujetsMembreFormDTO: Partial<SujetsMembreFormDTO>) => {
    const dto = new SujetsMembreFormDTO(sujetsMembreFormDTO)
    const sujetsMembre: Partial<SujetsMembre> = await dto.toEntity()
    const { data, error, errors } = await sujetsMembreService.create(sujetsMembre)
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  
  /**
   * Update an existing sujetsMembre
   * @param sujetsMembreFormDTO SujetsMembre object to update
   */
  const updateSujetsMembre = async (id: number | string,sujetsMembreFormDTO: Partial<SujetsMembreFormDTO>) => {
    const dto = new SujetsMembreFormDTO(sujetsMembreFormDTO)
    const sujetsMembre: Partial<SujetsMembre> = await dto.toEntity()
    const { data, error } = await sujetsMembreService.update(id, sujetsMembre)
    if (error) {
      message.value = error
    }
    return data
  }

  // API Requests
  /** Load all sujetsMembres from the API */
  const loadSujetsMembres = async (
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
    } = await sujetsMembreService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && sujetsMembres.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await sujetsMembreService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single sujetsMembre by id
   * @param id SujetsMembre ID
   */
  const getSujetsMembreById = async (id: number | string) => {
    const result = await sujetsMembreService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search sujetsMembres with filters, pagination, and sorting
   * @param filters Partial SujetsMembre object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchSujetsMembres = async (
    unpagined: boolean = false,
    filters: Partial<SujetsMembre>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await sujetsMembreService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await sujetsMembreService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportSujetsMembresToCsv = async (data: Partial<SujetsMembre[]>) => {
    console.log('Exporting SujetsMembre to CSV', data)
    await sujetsMembreService.exportCsv(data)
  }
  
  const getAllSujetsMembresByMembreId = async (
    membreId: number | string | undefined,
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
    } = await sujetsMembreService.getAllByMembreId(membreId, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext() && sujetsMembres.value.length > 0) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await sujetsMembreService.getAllByMembreId(
          membreId,
          paginationData.value.toParameter(),
          sortFields,
        )
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const getAllSujetsMembresBySujetId = async (
    sujetId: number | string | undefined,
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
    } = await sujetsMembreService.getAllBySujetId(sujetId, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext() && sujetsMembres.value.length > 0) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await sujetsMembreService.getAllBySujetId(
          sujetId,
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
    sujetsMembres,
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
    exportSujetsMembresToCsv,
    loadSujetsMembres,
    searchSujetsMembres,
    getSujetsMembreById,
    viewSujetsMembre,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deleteSujetsMembre,
    createSujetsMembre,
    updateSujetsMembre,
    getAllSujetsMembresByMembreId,
    getAllSujetsMembresBySujetId,
    
  }
}
