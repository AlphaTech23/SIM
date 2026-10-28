import { ref, watch } from 'vue'
import { RendezVou, RendezVouFormDTO } from '@/models/RendezVouModel'
import * as rendezVouService from '@/services/RendezVouService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['id']

/**
 * Composable to manage RendezVou entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useRendezVous() {
  /** List of all rendezVous */
  const rendezVous = ref<RendezVou[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_rendezVou') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_rendezVou', newValue)
  })

  /**
   * Handles API response by updating the rendezVous and pagination
   * @param data Array of RendezVou returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: RendezVou[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      rendezVous.value = rendezVous.value.concat(data)
    } else {
      rendezVous.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('rendezVou', 'list')
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
      const response = await columnConfigService.updateVisibleFields('rendezVou', 'list', fields)
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
  /** Navigate to rendezVou detail view */
  const viewRendezVou = (rendezVou: RendezVou) => {
    router.push({ name: "rendezVoudetailsview", params: { id: rendezVou.getKeyValue() } })
  }

  /** Navigate to rendezVou list view */
  const goToListView = () => {
    router.push({ path: "/rendezVous" })
  }

  /** Navigate to rendezVou create form */
  const goToCreateFormView = () => {
    router.push({ path: "/rendezVous/create" })
  }

  /** Navigate to rendezVou update form */
  const goToUpdateFormView = (rendezVou: RendezVou) => {
    router.push({ name: "rendezVouupdateview", params: { id: rendezVou.getKeyValue() } })
  }

  /**
   * Delete a rendezVou by id
   * @param rendezVou RendezVou object to delete
   */
  const deleteRendezVou = async (rendezVou: RendezVou) => {
    const result = await rendezVouService.remove(rendezVou.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  
  /**
   * Create a new rendezVou
   * @param rendezVouFormDTO RendezVou object to create
   */
  const createRendezVou = async (rendezVouFormDTO: Partial<RendezVouFormDTO>) => {
    const dto = new RendezVouFormDTO(rendezVouFormDTO)
    const rendezVou: Partial<RendezVou> = await dto.toEntity()
    const { data, error, errors } = await rendezVouService.create(rendezVou)
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  
  /**
   * Update an existing rendezVou
   * @param rendezVouFormDTO RendezVou object to update
   */
  const updateRendezVou = async (id: number | string,rendezVouFormDTO: Partial<RendezVouFormDTO>) => {
    const dto = new RendezVouFormDTO(rendezVouFormDTO)
    const rendezVou: Partial<RendezVou> = await dto.toEntity()
    const { data, error } = await rendezVouService.update(id, rendezVou)
    if (error) {
      message.value = error
    }
    return data
  }

  // API Requests
  /** Load all rendezVous from the API */
  const loadRendezVous = async (
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
    } = await rendezVouService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && rendezVous.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await rendezVouService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single rendezVou by id
   * @param id RendezVou ID
   */
  const getRendezVouById = async (id: number | string) => {
    const result = await rendezVouService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search rendezVous with filters, pagination, and sorting
   * @param filters Partial RendezVou object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchRendezVous = async (
    unpagined: boolean = false,
    filters: Partial<RendezVou>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await rendezVouService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await rendezVouService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportRendezVousToCsv = async (data: Partial<RendezVou[]>) => {
    console.log('Exporting RendezVou to CSV', data)
    await rendezVouService.exportCsv(data)
  }
  
  const getAllRendezVousByMembreId = async (
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
    } = await rendezVouService.getAllByMembreId(membreId, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext() && rendezVous.value.length > 0) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await rendezVouService.getAllByMembreId(
          membreId,
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
    rendezVous,
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
    exportRendezVousToCsv,
    loadRendezVous,
    searchRendezVous,
    getRendezVouById,
    viewRendezVou,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deleteRendezVou,
    createRendezVou,
    updateRendezVou,
    getAllRendezVousByMembreId,
    
  }
}
