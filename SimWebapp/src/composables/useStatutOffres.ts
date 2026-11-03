import { ref, watch } from 'vue'
import { StatutOffre, StatutOffreFormDTO } from '@/models/StatutOffreModel'
import * as statutOffreService from '@/services/StatutOffreService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['id']

/**
 * Composable to manage StatutOffre entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useStatutOffres() {
  /** List of all statutOffres */
  const statutOffres = ref<StatutOffre[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_statutOffre') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_statutOffre', newValue)
  })

  /**
   * Handles API response by updating the statutOffres and pagination
   * @param data Array of StatutOffre returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: StatutOffre[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      statutOffres.value = statutOffres.value.concat(data)
    } else {
      statutOffres.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('statutOffre', 'list')
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
      const response = await columnConfigService.updateVisibleFields('statutOffre', 'list', fields)
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
  /** Navigate to statutOffre detail view */
  const viewStatutOffre = (statutOffre: StatutOffre) => {
    router.push({ name: "statutOffredetailsview", params: { id: statutOffre.getKeyValue() } })
  }

  /** Navigate to statutOffre list view */
  const goToListView = () => {
    router.push({ path: "/statutOffres" })
  }

  /** Navigate to statutOffre create form */
  const goToCreateFormView = () => {
    router.push({ path: "/statutOffres/create" })
  }

  /** Navigate to statutOffre update form */
  const goToUpdateFormView = (statutOffre: StatutOffre) => {
    router.push({ name: "statutOffreupdateview", params: { id: statutOffre.getKeyValue() } })
  }

  /**
   * Delete a statutOffre by id
   * @param statutOffre StatutOffre object to delete
   */
  const deleteStatutOffre = async (statutOffre: StatutOffre) => {
    const result = await statutOffreService.remove(statutOffre.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  
  /**
   * Create a new statutOffre
   * @param statutOffreFormDTO StatutOffre object to create
   */
  const createStatutOffre = async (statutOffreFormDTO: Partial<StatutOffreFormDTO>) => {
    const dto = new StatutOffreFormDTO(statutOffreFormDTO)
    const statutOffre: Partial<StatutOffre> = await dto.toEntity()
    const { data, error, errors } = await statutOffreService.create(statutOffre)
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  
  /**
   * Update an existing statutOffre
   * @param statutOffreFormDTO StatutOffre object to update
   */
  const updateStatutOffre = async (id: number | string,statutOffreFormDTO: Partial<StatutOffreFormDTO>) => {
    const dto = new StatutOffreFormDTO(statutOffreFormDTO)
    const statutOffre: Partial<StatutOffre> = await dto.toEntity()
    const { data, error } = await statutOffreService.update(id, statutOffre)
    if (error) {
      message.value = error
    }
    return data
  }

  // API Requests
  /** Load all statutOffres from the API */
  const loadStatutOffres = async (
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
    } = await statutOffreService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && statutOffres.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await statutOffreService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single statutOffre by id
   * @param id StatutOffre ID
   */
  const getStatutOffreById = async (id: number | string) => {
    const result = await statutOffreService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search statutOffres with filters, pagination, and sorting
   * @param filters Partial StatutOffre object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchStatutOffres = async (
    unpagined: boolean = false,
    filters: Partial<StatutOffre>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await statutOffreService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await statutOffreService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportStatutOffresToCsv = async (data: Partial<StatutOffre[]>) => {
    console.log('Exporting StatutOffre to CSV', data)
    await statutOffreService.exportCsv(data)
  }
  
  /** Getter for pagination data */
  const getPaginationData = () => paginationData.value

  return {
    // state
    statutOffres,
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
    exportStatutOffresToCsv,
    loadStatutOffres,
    searchStatutOffres,
    getStatutOffreById,
    viewStatutOffre,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deleteStatutOffre,
    createStatutOffre,
    updateStatutOffre,
  }
}
