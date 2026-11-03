import { ref, watch } from 'vue'
import { CiblesOffre, CiblesOffreFormDTO } from '@/models/CiblesOffreModel'
import * as ciblesOffreService from '@/services/CiblesOffreService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['id']

/**
 * Composable to manage CiblesOffre entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useCiblesOffres() {
  /** List of all ciblesOffres */
  const ciblesOffres = ref<CiblesOffre[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_ciblesOffre') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_ciblesOffre', newValue)
  })

  /**
   * Handles API response by updating the ciblesOffres and pagination
   * @param data Array of CiblesOffre returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: CiblesOffre[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      ciblesOffres.value = ciblesOffres.value.concat(data)
    } else {
      ciblesOffres.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('ciblesOffre', 'list')
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
      const response = await columnConfigService.updateVisibleFields('ciblesOffre', 'list', fields)
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
  /** Navigate to ciblesOffre detail view */
  const viewCiblesOffre = (ciblesOffre: CiblesOffre) => {
    router.push({ name: "ciblesOffredetailsview", params: { id: ciblesOffre.getKeyValue() } })
  }

  /** Navigate to ciblesOffre list view */
  const goToListView = () => {
    router.push({ path: "/ciblesOffres" })
  }

  /** Navigate to ciblesOffre create form */
  const goToCreateFormView = () => {
    router.push({ path: "/ciblesOffres/create" })
  }

  /** Navigate to ciblesOffre update form */
  const goToUpdateFormView = (ciblesOffre: CiblesOffre) => {
    router.push({ name: "ciblesOffreupdateview", params: { id: ciblesOffre.getKeyValue() } })
  }

  /**
   * Delete a ciblesOffre by id
   * @param ciblesOffre CiblesOffre object to delete
   */
  const deleteCiblesOffre = async (ciblesOffre: CiblesOffre) => {
    const result = await ciblesOffreService.remove(ciblesOffre.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  
  /**
   * Create a new ciblesOffre
   * @param ciblesOffreFormDTO CiblesOffre object to create
   */
  const createCiblesOffre = async (ciblesOffreFormDTO: Partial<CiblesOffreFormDTO>) => {
    const dto = new CiblesOffreFormDTO(ciblesOffreFormDTO)
    const ciblesOffre: Partial<CiblesOffre> = await dto.toEntity()
    const { data, error, errors } = await ciblesOffreService.create(ciblesOffre)
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  
  /**
   * Update an existing ciblesOffre
   * @param ciblesOffreFormDTO CiblesOffre object to update
   */
  const updateCiblesOffre = async (id: number | string,ciblesOffreFormDTO: Partial<CiblesOffreFormDTO>) => {
    const dto = new CiblesOffreFormDTO(ciblesOffreFormDTO)
    const ciblesOffre: Partial<CiblesOffre> = await dto.toEntity()
    const { data, error } = await ciblesOffreService.update(id, ciblesOffre)
    if (error) {
      message.value = error
    }
    return data
  }

  // API Requests
  /** Load all ciblesOffres from the API */
  const loadCiblesOffres = async (
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
    } = await ciblesOffreService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && ciblesOffres.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await ciblesOffreService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single ciblesOffre by id
   * @param id CiblesOffre ID
   */
  const getCiblesOffreById = async (id: number | string) => {
    const result = await ciblesOffreService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search ciblesOffres with filters, pagination, and sorting
   * @param filters Partial CiblesOffre object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchCiblesOffres = async (
    unpagined: boolean = false,
    filters: Partial<CiblesOffre>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await ciblesOffreService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await ciblesOffreService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportCiblesOffresToCsv = async (data: Partial<CiblesOffre[]>) => {
    console.log('Exporting CiblesOffre to CSV', data)
    await ciblesOffreService.exportCsv(data)
  }
  
  const getAllCiblesOffresByOffreId = async (
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
    } = await ciblesOffreService.getAllByOffreId(offreId, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext() && ciblesOffres.value.length > 0) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await ciblesOffreService.getAllByOffreId(
          offreId,
          paginationData.value.toParameter(),
          sortFields,
        )
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const getAllCiblesOffresByMembreId = async (
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
    } = await ciblesOffreService.getAllByMembreId(membreId, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext() && ciblesOffres.value.length > 0) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await ciblesOffreService.getAllByMembreId(
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
    ciblesOffres,
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
    exportCiblesOffresToCsv,
    loadCiblesOffres,
    searchCiblesOffres,
    getCiblesOffreById,
    viewCiblesOffre,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deleteCiblesOffre,
    createCiblesOffre,
    updateCiblesOffre,
    getAllCiblesOffresByOffreId,
    getAllCiblesOffresByMembreId,
    
  }
}
