import { ref, watch } from 'vue'
import { Statut, StatutFormDTO } from '@/models/StatutModel'
import * as statutService from '@/services/StatutService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'
import { StatutRendezVou, StatutRendezVouFormDTO } from '@/models/StatutRendezVouModel'



export const DEFAULT_LIST_VIEW_FIELDS = ['id']

/**
 * Composable to manage Statut entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useStatuts() {
  /** List of all statuts */
  const statuts = ref<Statut[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_statut') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_statut', newValue)
  })

  /**
   * Handles API response by updating the statuts and pagination
   * @param data Array of Statut returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: Statut[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      statuts.value = statuts.value.concat(data)
    } else {
      statuts.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('statut', 'list')
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
      const response = await columnConfigService.updateVisibleFields('statut', 'list', fields)
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
  /** Navigate to statut detail view */
  const viewStatut = (statut: Statut) => {
    router.push({ name: "statutdetailsview", params: { id: statut.getKeyValue() } })
  }

  /** Navigate to statut list view */
  const goToListView = () => {
    router.push({ path: "/statuts" })
  }

  /** Navigate to statut create form */
  const goToCreateFormView = () => {
    router.push({ path: "/statuts/create" })
  }

  /** Navigate to statut update form */
  const goToUpdateFormView = (statut: Statut) => {
    router.push({ name: "statutupdateview", params: { id: statut.getKeyValue() } })
  }

  /**
   * Delete a statut by id
   * @param statut Statut object to delete
   */
  const deleteStatut = async (statut: Statut) => {
    const result = await statutService.remove(statut.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Create a new statut
   * @param fullData Object containing statut and his details
   */
  const createStatut = async (fullData: { statut: Partial<StatutFormDTO>; statutRendezVous: StatutRendezVouFormDTO[] })=> {
    const dto = new StatutFormDTO(fullData.statut)
    const statut: Partial<Statut> = await dto.toEntity()
    const statutRendezVous: Partial<StatutRendezVou>[] = await StatutRendezVouFormDTO.toEntities(fullData.statutRendezVous)
    
    const { data, error, errors } = await statutService.create({ statut, statutRendezVous })
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  /**
  * Update an existing statut with full details
  * @param id Statut ID to update
  * @param fullData Object containing statut and its details
  */
  const updateStatut = async (
    id: number | string,
    fullData: {
      statut: Partial<StatutFormDTO>
      ; statutRendezVous: StatutRendezVouFormDTO[] 
    },
  ) => {
    const dto = new StatutFormDTO(fullData.statut)
    const statut: Partial<Statut> = await dto.toEntity()

    const statutRendezVous: Partial<StatutRendezVou>[] = await StatutRendezVouFormDTO.toEntities(
      fullData.statutRendezVous,
    )
    
    const { data, error, errors } = await statutService.update(
    id, 
    { 
      statut
      , statutRendezVous
       
    }
   )
   if (error) {
    message.value = error
   }
   return { data, errors }
  }
  

  // API Requests
  /** Load all statuts from the API */
  const loadStatuts = async (
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
    } = await statutService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && statuts.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await statutService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single statut by id
   * @param id Statut ID
   */
  const getStatutById = async (id: number | string) => {
    const result = await statutService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search statuts with filters, pagination, and sorting
   * @param filters Partial Statut object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchStatuts = async (
    unpagined: boolean = false,
    filters: Partial<Statut>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await statutService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await statutService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportStatutsToCsv = async (data: Partial<Statut[]>) => {
    console.log('Exporting Statut to CSV', data)
    await statutService.exportCsv(data)
  }
  
  /** Getter for pagination data */
  const getPaginationData = () => paginationData.value

  return {
    // state
    statuts,
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
    exportStatutsToCsv,
    loadStatuts,
    searchStatuts,
    getStatutById,
    viewStatut,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deleteStatut,
    createStatut,
    updateStatut,
  }
}
