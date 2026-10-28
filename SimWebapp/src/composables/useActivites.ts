import { ref, watch } from 'vue'
import { Activite, ActiviteFormDTO } from '@/models/ActiviteModel'
import * as activiteService from '@/services/ActiviteService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'
import { ActivitesMembre, ActivitesMembreFormDTO } from '@/models/ActivitesMembreModel'



export const DEFAULT_LIST_VIEW_FIELDS = ['id']

/**
 * Composable to manage Activite entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useActivites() {
  /** List of all activites */
  const activites = ref<Activite[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_activite') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_activite', newValue)
  })

  /**
   * Handles API response by updating the activites and pagination
   * @param data Array of Activite returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: Activite[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      activites.value = activites.value.concat(data)
    } else {
      activites.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('activite', 'list')
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
      const response = await columnConfigService.updateVisibleFields('activite', 'list', fields)
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
  /** Navigate to activite detail view */
  const viewActivite = (activite: Activite) => {
    router.push({ name: "activitedetailsview", params: { id: activite.getKeyValue() } })
  }

  /** Navigate to activite list view */
  const goToListView = () => {
    router.push({ path: "/activites" })
  }

  /** Navigate to activite create form */
  const goToCreateFormView = () => {
    router.push({ path: "/activites/create" })
  }

  /** Navigate to activite update form */
  const goToUpdateFormView = (activite: Activite) => {
    router.push({ name: "activiteupdateview", params: { id: activite.getKeyValue() } })
  }

  /**
   * Delete a activite by id
   * @param activite Activite object to delete
   */
  const deleteActivite = async (activite: Activite) => {
    const result = await activiteService.remove(activite.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Create a new activite
   * @param fullData Object containing activite and his details
   */
  const createActivite = async (fullData: { activite: Partial<ActiviteFormDTO>; activitesMembres: ActivitesMembreFormDTO[] })=> {
    const dto = new ActiviteFormDTO(fullData.activite)
    const activite: Partial<Activite> = await dto.toEntity()
    const activitesMembres: Partial<ActivitesMembre>[] = await ActivitesMembreFormDTO.toEntities(fullData.activitesMembres)
    
    const { data, error, errors } = await activiteService.create({ activite, activitesMembres })
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  /**
  * Update an existing activite with full details
  * @param id Activite ID to update
  * @param fullData Object containing activite and its details
  */
  const updateActivite = async (
    id: number | string,
    fullData: {
      activite: Partial<ActiviteFormDTO>
      ; activitesMembres: ActivitesMembreFormDTO[] 
    },
  ) => {
    const dto = new ActiviteFormDTO(fullData.activite)
    const activite: Partial<Activite> = await dto.toEntity()

    const activitesMembres: Partial<ActivitesMembre>[] = await ActivitesMembreFormDTO.toEntities(
      fullData.activitesMembres,
    )
    
    const { data, error, errors } = await activiteService.update(
    id, 
    { 
      activite
      , activitesMembres
       
    }
   )
   if (error) {
    message.value = error
   }
   return { data, errors }
  }
  

  // API Requests
  /** Load all activites from the API */
  const loadActivites = async (
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
    } = await activiteService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && activites.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await activiteService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single activite by id
   * @param id Activite ID
   */
  const getActiviteById = async (id: number | string) => {
    const result = await activiteService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search activites with filters, pagination, and sorting
   * @param filters Partial Activite object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchActivites = async (
    unpagined: boolean = false,
    filters: Partial<Activite>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await activiteService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await activiteService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportActivitesToCsv = async (data: Partial<Activite[]>) => {
    console.log('Exporting Activite to CSV', data)
    await activiteService.exportCsv(data)
  }
  
  /** Getter for pagination data */
  const getPaginationData = () => paginationData.value

  return {
    // state
    activites,
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
    exportActivitesToCsv,
    loadActivites,
    searchActivites,
    getActiviteById,
    viewActivite,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deleteActivite,
    createActivite,
    updateActivite,
  }
}
