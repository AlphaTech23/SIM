import { ref, watch } from 'vue'
import { HistoOffre, HistoOffreFormDTO } from '@/models/HistoOffreModel'
import * as histoOffreService from '@/services/HistoOffreService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['id']

/**
 * Composable to manage HistoOffre entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useHistoOffres() {
  /** List of all histoOffres */
  const histoOffres = ref<HistoOffre[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_histoOffre') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_histoOffre', newValue)
  })

  /**
   * Handles API response by updating the histoOffres and pagination
   * @param data Array of HistoOffre returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: HistoOffre[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      histoOffres.value = histoOffres.value.concat(data)
    } else {
      histoOffres.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('histoOffre', 'list')
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
      const response = await columnConfigService.updateVisibleFields('histoOffre', 'list', fields)
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
  /** Navigate to histoOffre detail view */
  const viewHistoOffre = (histoOffre: HistoOffre) => {
    router.push({ name: "histoOffredetailsview", params: { id: histoOffre.getKeyValue() } })
  }

  /** Navigate to histoOffre list view */
  const goToListView = () => {
    router.push({ path: "/histoOffres" })
  }

  /** Navigate to histoOffre create form */
  const goToCreateFormView = () => {
    router.push({ path: "/histoOffres/create" })
  }

  /** Navigate to histoOffre update form */
  const goToUpdateFormView = (histoOffre: HistoOffre) => {
    router.push({ name: "histoOffreupdateview", params: { id: histoOffre.getKeyValue() } })
  }

  /**
   * Delete a histoOffre by id
   * @param histoOffre HistoOffre object to delete
   */
  const deleteHistoOffre = async (histoOffre: HistoOffre) => {
    const result = await histoOffreService.remove(histoOffre.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  
  /**
   * Create a new histoOffre
   * @param histoOffreFormDTO HistoOffre object to create
   */
  const createHistoOffre = async (histoOffreFormDTO: Partial<HistoOffreFormDTO>) => {
    const dto = new HistoOffreFormDTO(histoOffreFormDTO)
    const histoOffre: Partial<HistoOffre> = await dto.toEntity()
    const { data, error, errors } = await histoOffreService.create(histoOffre)
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  
  /**
   * Update an existing histoOffre
   * @param histoOffreFormDTO HistoOffre object to update
   */
  const updateHistoOffre = async (id: number | string,histoOffreFormDTO: Partial<HistoOffreFormDTO>) => {
    const dto = new HistoOffreFormDTO(histoOffreFormDTO)
    const histoOffre: Partial<HistoOffre> = await dto.toEntity()
    const { data, error } = await histoOffreService.update(id, histoOffre)
    if (error) {
      message.value = error
    }
    return data
  }

  // API Requests
  /** Load all histoOffres from the API */
  const loadHistoOffres = async (
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
    } = await histoOffreService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && histoOffres.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await histoOffreService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single histoOffre by id
   * @param id HistoOffre ID
   */
  const getHistoOffreById = async (id: number | string) => {
    const result = await histoOffreService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search histoOffres with filters, pagination, and sorting
   * @param filters Partial HistoOffre object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchHistoOffres = async (
    unpagined: boolean = false,
    filters: Partial<HistoOffre>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await histoOffreService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await histoOffreService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportHistoOffresToCsv = async (data: Partial<HistoOffre[]>) => {
    console.log('Exporting HistoOffre to CSV', data)
    await histoOffreService.exportCsv(data)
  }
  
  const getAllHistoOffresByOffreId = async (
    offreId: number | string | undefined,
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
    } = await histoOffreService.getAllByOffreId(offreId, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext() && histoOffres.value.length > 0) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await histoOffreService.getAllByOffreId(
          offreId,
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
    histoOffres,
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
    exportHistoOffresToCsv,
    loadHistoOffres,
    searchHistoOffres,
    getHistoOffreById,
    viewHistoOffre,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deleteHistoOffre,
    createHistoOffre,
    updateHistoOffre,
    getAllHistoOffresByOffreId,
    
  }
}
