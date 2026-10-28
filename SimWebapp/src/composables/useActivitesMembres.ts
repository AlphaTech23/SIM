import { ref, watch } from 'vue'
import { ActivitesMembre, ActivitesMembreFormDTO } from '@/models/ActivitesMembreModel'
import * as activitesMembreService from '@/services/ActivitesMembreService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['id']

/**
 * Composable to manage ActivitesMembre entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useActivitesMembres() {
  /** List of all activitesMembres */
  const activitesMembres = ref<ActivitesMembre[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_activitesMembre') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_activitesMembre', newValue)
  })

  /**
   * Handles API response by updating the activitesMembres and pagination
   * @param data Array of ActivitesMembre returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: ActivitesMembre[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      activitesMembres.value = activitesMembres.value.concat(data)
    } else {
      activitesMembres.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('activitesMembre', 'list')
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
      const response = await columnConfigService.updateVisibleFields('activitesMembre', 'list', fields)
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
  /** Navigate to activitesMembre detail view */
  const viewActivitesMembre = (activitesMembre: ActivitesMembre) => {
    router.push({ name: "activitesMembredetailsview", params: { id: activitesMembre.getKeyValue() } })
  }

  /** Navigate to activitesMembre list view */
  const goToListView = () => {
    router.push({ path: "/activitesMembres" })
  }

  /** Navigate to activitesMembre create form */
  const goToCreateFormView = () => {
    router.push({ path: "/activitesMembres/create" })
  }

  /** Navigate to activitesMembre update form */
  const goToUpdateFormView = (activitesMembre: ActivitesMembre) => {
    router.push({ name: "activitesMembreupdateview", params: { id: activitesMembre.getKeyValue() } })
  }

  /**
   * Delete a activitesMembre by id
   * @param activitesMembre ActivitesMembre object to delete
   */
  const deleteActivitesMembre = async (activitesMembre: ActivitesMembre) => {
    const result = await activitesMembreService.remove(activitesMembre.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  
  /**
   * Create a new activitesMembre
   * @param activitesMembreFormDTO ActivitesMembre object to create
   */
  const createActivitesMembre = async (activitesMembreFormDTO: Partial<ActivitesMembreFormDTO>) => {
    const dto = new ActivitesMembreFormDTO(activitesMembreFormDTO)
    const activitesMembre: Partial<ActivitesMembre> = await dto.toEntity()
    const { data, error, errors } = await activitesMembreService.create(activitesMembre)
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  
  /**
   * Update an existing activitesMembre
   * @param activitesMembreFormDTO ActivitesMembre object to update
   */
  const updateActivitesMembre = async (id: number | string,activitesMembreFormDTO: Partial<ActivitesMembreFormDTO>) => {
    const dto = new ActivitesMembreFormDTO(activitesMembreFormDTO)
    const activitesMembre: Partial<ActivitesMembre> = await dto.toEntity()
    const { data, error } = await activitesMembreService.update(id, activitesMembre)
    if (error) {
      message.value = error
    }
    return data
  }

  // API Requests
  /** Load all activitesMembres from the API */
  const loadActivitesMembres = async (
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
    } = await activitesMembreService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && activitesMembres.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await activitesMembreService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single activitesMembre by id
   * @param id ActivitesMembre ID
   */
  const getActivitesMembreById = async (id: number | string) => {
    const result = await activitesMembreService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search activitesMembres with filters, pagination, and sorting
   * @param filters Partial ActivitesMembre object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchActivitesMembres = async (
    unpagined: boolean = false,
    filters: Partial<ActivitesMembre>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await activitesMembreService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await activitesMembreService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportActivitesMembresToCsv = async (data: Partial<ActivitesMembre[]>) => {
    console.log('Exporting ActivitesMembre to CSV', data)
    await activitesMembreService.exportCsv(data)
  }
  
  const getAllActivitesMembresByMembreId = async (
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
    } = await activitesMembreService.getAllByMembreId(membreId, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext() && activitesMembres.value.length > 0) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await activitesMembreService.getAllByMembreId(
          membreId,
          paginationData.value.toParameter(),
          sortFields,
        )
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const getAllActivitesMembresByActiviteId = async (
    activiteId: number | string | undefined,
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
    } = await activitesMembreService.getAllByActiviteId(activiteId, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext() && activitesMembres.value.length > 0) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await activitesMembreService.getAllByActiviteId(
          activiteId,
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
    activitesMembres,
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
    exportActivitesMembresToCsv,
    loadActivitesMembres,
    searchActivitesMembres,
    getActivitesMembreById,
    viewActivitesMembre,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deleteActivitesMembre,
    createActivitesMembre,
    updateActivitesMembre,
    getAllActivitesMembresByMembreId,
    getAllActivitesMembresByActiviteId,
    
  }
}
