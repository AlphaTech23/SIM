import { ref, watch } from 'vue'
import { VRendezVousRefuse,  } from '@/models/VRendezVousRefuseModel'
import * as vRendezVousRefuseService from '@/services/VRendezVousRefuseService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['id']

/**
 * Composable to manage VRendezVousRefuse entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useVRendezVousRefuses() {
  /** List of all vRendezVousRefuses */
  const vRendezVousRefuses = ref<VRendezVousRefuse[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_vRendezVousRefuse') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_vRendezVousRefuse', newValue)
  })

  /**
   * Handles API response by updating the vRendezVousRefuses and pagination
   * @param data Array of VRendezVousRefuse returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: VRendezVousRefuse[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      vRendezVousRefuses.value = vRendezVousRefuses.value.concat(data)
    } else {
      vRendezVousRefuses.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('vRendezVousRefuse', 'list')
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
      const response = await columnConfigService.updateVisibleFields('vRendezVousRefuse', 'list', fields)
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
  /** Navigate to vRendezVousRefuse detail view */
  const viewVRendezVousRefuse = (vRendezVousRefuse: VRendezVousRefuse) => {
    router.push({ name: "vRendezVousRefusedetailsview", params: { id: vRendezVousRefuse.getKeyValue() } })
  }

  /** Navigate to vRendezVousRefuse list view */
  const goToListView = () => {
    router.push({ path: "/vRendezVousRefuses" })
  }

  
  

  // API Requests
  /** Load all vRendezVousRefuses from the API */
  const loadVRendezVousRefuses = async (
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
    } = await vRendezVousRefuseService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && vRendezVousRefuses.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await vRendezVousRefuseService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single vRendezVousRefuse by id
   * @param id VRendezVousRefuse ID
   */
  const getVRendezVousRefuseById = async (id: number | string) => {
    const result = await vRendezVousRefuseService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search vRendezVousRefuses with filters, pagination, and sorting
   * @param filters Partial VRendezVousRefuse object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchVRendezVousRefuses = async (
    unpagined: boolean = false,
    filters: Partial<VRendezVousRefuse>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await vRendezVousRefuseService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await vRendezVousRefuseService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportVRendezVousRefusesToCsv = async (data: Partial<VRendezVousRefuse[]>) => {
    console.log('Exporting VRendezVousRefuse to CSV', data)
    await vRendezVousRefuseService.exportCsv(data)
  }
  
  /** Getter for pagination data */
  const getPaginationData = () => paginationData.value

  return {
    // state
    vRendezVousRefuses,
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
    exportVRendezVousRefusesToCsv,
    loadVRendezVousRefuses,
    searchVRendezVousRefuses,
    getVRendezVousRefuseById,
    viewVRendezVousRefuse,
    goToListView,
  }
}
