import { ref, watch } from 'vue'
import { PlageHoraire, PlageHoraireFormDTO } from '@/models/PlageHoraireModel'
import * as plageHoraireService from '@/services/PlageHoraireService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['id']

/**
 * Composable to manage PlageHoraire entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function usePlageHoraires() {
  /** List of all plageHoraires */
  const plageHoraires = ref<PlageHoraire[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_plageHoraire') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_plageHoraire', newValue)
  })

  /**
   * Handles API response by updating the plageHoraires and pagination
   * @param data Array of PlageHoraire returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: PlageHoraire[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      plageHoraires.value = plageHoraires.value.concat(data)
    } else {
      plageHoraires.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('plageHoraire', 'list')
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
      const response = await columnConfigService.updateVisibleFields('plageHoraire', 'list', fields)
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
  /** Navigate to plageHoraire detail view */
  const viewPlageHoraire = (plageHoraire: PlageHoraire) => {
    router.push({ name: "plageHorairedetailsview", params: { id: plageHoraire.getKeyValue() } })
  }

  /** Navigate to plageHoraire list view */
  const goToListView = () => {
    router.push({ path: "/plageHoraires" })
  }

  /** Navigate to plageHoraire create form */
  const goToCreateFormView = () => {
    router.push({ path: "/plageHoraires/create" })
  }

  /** Navigate to plageHoraire update form */
  const goToUpdateFormView = (plageHoraire: PlageHoraire) => {
    router.push({ name: "plageHoraireupdateview", params: { id: plageHoraire.getKeyValue() } })
  }

  /**
   * Delete a plageHoraire by id
   * @param plageHoraire PlageHoraire object to delete
   */
  const deletePlageHoraire = async (plageHoraire: PlageHoraire) => {
    const result = await plageHoraireService.remove(plageHoraire.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  
  /**
   * Create a new plageHoraire
   * @param plageHoraireFormDTO PlageHoraire object to create
   */
  const createPlageHoraire = async (plageHoraireFormDTO: Partial<PlageHoraireFormDTO>) => {
    const dto = new PlageHoraireFormDTO(plageHoraireFormDTO)
    const plageHoraire: Partial<PlageHoraire> = await dto.toEntity()
    const { data, error, errors } = await plageHoraireService.create(plageHoraire)
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  
  /**
   * Update an existing plageHoraire
   * @param plageHoraireFormDTO PlageHoraire object to update
   */
  const updatePlageHoraire = async (id: number | string,plageHoraireFormDTO: Partial<PlageHoraireFormDTO>) => {
    const dto = new PlageHoraireFormDTO(plageHoraireFormDTO)
    const plageHoraire: Partial<PlageHoraire> = await dto.toEntity()
    const { data, error } = await plageHoraireService.update(id, plageHoraire)
    if (error) {
      message.value = error
    }
    return data
  }

  // API Requests
  /** Load all plageHoraires from the API */
  const loadPlageHoraires = async (
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
    } = await plageHoraireService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && plageHoraires.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await plageHoraireService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single plageHoraire by id
   * @param id PlageHoraire ID
   */
  const getPlageHoraireById = async (id: number | string) => {
    const result = await plageHoraireService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search plageHoraires with filters, pagination, and sorting
   * @param filters Partial PlageHoraire object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchPlageHoraires = async (
    unpagined: boolean = false,
    filters: Partial<PlageHoraire>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await plageHoraireService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await plageHoraireService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportPlageHorairesToCsv = async (data: Partial<PlageHoraire[]>) => {
    console.log('Exporting PlageHoraire to CSV', data)
    await plageHoraireService.exportCsv(data)
  }
  
  /** Getter for pagination data */
  const getPaginationData = () => paginationData.value

  return {
    // state
    plageHoraires,
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
    exportPlageHorairesToCsv,
    loadPlageHoraires,
    searchPlageHoraires,
    getPlageHoraireById,
    viewPlageHoraire,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deletePlageHoraire,
    createPlageHoraire,
    updatePlageHoraire,
  }
}
