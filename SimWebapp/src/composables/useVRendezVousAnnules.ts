import { ref, watch } from 'vue'
import { VRendezVousAnnule,  } from '@/models/VRendezVousAnnuleModel'
import * as vRendezVousAnnuleService from '@/services/VRendezVousAnnuleService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['id']

/**
 * Composable to manage VRendezVousAnnule entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useVRendezVousAnnules() {
  /** List of all vRendezVousAnnules */
  const vRendezVousAnnules = ref<VRendezVousAnnule[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_vRendezVousAnnule') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_vRendezVousAnnule', newValue)
  })

  /**
   * Handles API response by updating the vRendezVousAnnules and pagination
   * @param data Array of VRendezVousAnnule returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: VRendezVousAnnule[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      vRendezVousAnnules.value = vRendezVousAnnules.value.concat(data)
    } else {
      vRendezVousAnnules.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('vRendezVousAnnule', 'list')
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
      const response = await columnConfigService.updateVisibleFields('vRendezVousAnnule', 'list', fields)
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
  /** Navigate to vRendezVousAnnule detail view */
  const viewVRendezVousAnnule = (vRendezVousAnnule: VRendezVousAnnule) => {
    router.push({ name: "vRendezVousAnnuledetailsview", params: { id: vRendezVousAnnule.getKeyValue() } })
  }

  /** Navigate to vRendezVousAnnule list view */
  const goToListView = () => {
    router.push({ path: "/vRendezVousAnnules" })
  }

  
  

  // API Requests
  /** Load all vRendezVousAnnules from the API */
  const loadVRendezVousAnnules = async (
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
    } = await vRendezVousAnnuleService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && vRendezVousAnnules.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await vRendezVousAnnuleService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single vRendezVousAnnule by id
   * @param id VRendezVousAnnule ID
   */
  const getVRendezVousAnnuleById = async (id: number | string) => {
    const result = await vRendezVousAnnuleService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search vRendezVousAnnules with filters, pagination, and sorting
   * @param filters Partial VRendezVousAnnule object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchVRendezVousAnnules = async (
    unpagined: boolean = false,
    filters: Partial<VRendezVousAnnule>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await vRendezVousAnnuleService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await vRendezVousAnnuleService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportVRendezVousAnnulesToCsv = async (data: Partial<VRendezVousAnnule[]>) => {
    console.log('Exporting VRendezVousAnnule to CSV', data)
    await vRendezVousAnnuleService.exportCsv(data)
  }
  
  /** Getter for pagination data */
  const getPaginationData = () => paginationData.value

  return {
    // state
    vRendezVousAnnules,
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
    exportVRendezVousAnnulesToCsv,
    loadVRendezVousAnnules,
    searchVRendezVousAnnules,
    getVRendezVousAnnuleById,
    viewVRendezVousAnnule,
    goToListView,
  }
}
