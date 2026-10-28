import { ref, watch } from 'vue'
import { StatutRendezVou, StatutRendezVouFormDTO } from '@/models/StatutRendezVouModel'
import * as statutRendezVouService from '@/services/StatutRendezVouService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['id']

/**
 * Composable to manage StatutRendezVou entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useStatutRendezVous() {
  /** List of all statutRendezVous */
  const statutRendezVous = ref<StatutRendezVou[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_statutRendezVou') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_statutRendezVou', newValue)
  })

  /**
   * Handles API response by updating the statutRendezVous and pagination
   * @param data Array of StatutRendezVou returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: StatutRendezVou[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      statutRendezVous.value = statutRendezVous.value.concat(data)
    } else {
      statutRendezVous.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('statutRendezVou', 'list')
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
      const response = await columnConfigService.updateVisibleFields('statutRendezVou', 'list', fields)
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
  /** Navigate to statutRendezVou detail view */
  const viewStatutRendezVou = (statutRendezVou: StatutRendezVou) => {
    router.push({ name: "statutRendezVoudetailsview", params: { id: statutRendezVou.getKeyValue() } })
  }

  /** Navigate to statutRendezVou list view */
  const goToListView = () => {
    router.push({ path: "/statutRendezVous" })
  }

  /** Navigate to statutRendezVou create form */
  const goToCreateFormView = () => {
    router.push({ path: "/statutRendezVous/create" })
  }

  /** Navigate to statutRendezVou update form */
  const goToUpdateFormView = (statutRendezVou: StatutRendezVou) => {
    router.push({ name: "statutRendezVouupdateview", params: { id: statutRendezVou.getKeyValue() } })
  }

  /**
   * Delete a statutRendezVou by id
   * @param statutRendezVou StatutRendezVou object to delete
   */
  const deleteStatutRendezVou = async (statutRendezVou: StatutRendezVou) => {
    const result = await statutRendezVouService.remove(statutRendezVou.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  
  /**
   * Create a new statutRendezVou
   * @param statutRendezVouFormDTO StatutRendezVou object to create
   */
  const createStatutRendezVou = async (statutRendezVouFormDTO: Partial<StatutRendezVouFormDTO>) => {
    const dto = new StatutRendezVouFormDTO(statutRendezVouFormDTO)
    const statutRendezVou: Partial<StatutRendezVou> = await dto.toEntity()
    const { data, error, errors } = await statutRendezVouService.create(statutRendezVou)
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  
  /**
   * Update an existing statutRendezVou
   * @param statutRendezVouFormDTO StatutRendezVou object to update
   */
  const updateStatutRendezVou = async (id: number | string,statutRendezVouFormDTO: Partial<StatutRendezVouFormDTO>) => {
    const dto = new StatutRendezVouFormDTO(statutRendezVouFormDTO)
    const statutRendezVou: Partial<StatutRendezVou> = await dto.toEntity()
    const { data, error } = await statutRendezVouService.update(id, statutRendezVou)
    if (error) {
      message.value = error
    }
    return data
  }

  // API Requests
  /** Load all statutRendezVous from the API */
  const loadStatutRendezVous = async (
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
    } = await statutRendezVouService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && statutRendezVous.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await statutRendezVouService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single statutRendezVou by id
   * @param id StatutRendezVou ID
   */
  const getStatutRendezVouById = async (id: number | string) => {
    const result = await statutRendezVouService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search statutRendezVous with filters, pagination, and sorting
   * @param filters Partial StatutRendezVou object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchStatutRendezVous = async (
    unpagined: boolean = false,
    filters: Partial<StatutRendezVou>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await statutRendezVouService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await statutRendezVouService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportStatutRendezVousToCsv = async (data: Partial<StatutRendezVou[]>) => {
    console.log('Exporting StatutRendezVou to CSV', data)
    await statutRendezVouService.exportCsv(data)
  }
  
  const getAllStatutRendezVousByStatutId = async (
    statutId: number | string | undefined,
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
    } = await statutRendezVouService.getAllByStatutId(statutId, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext() && statutRendezVous.value.length > 0) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await statutRendezVouService.getAllByStatutId(
          statutId,
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
    statutRendezVous,
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
    exportStatutRendezVousToCsv,
    loadStatutRendezVous,
    searchStatutRendezVous,
    getStatutRendezVouById,
    viewStatutRendezVou,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deleteStatutRendezVou,
    createStatutRendezVou,
    updateStatutRendezVou,
    getAllStatutRendezVousByStatutId,
    
  }
}
