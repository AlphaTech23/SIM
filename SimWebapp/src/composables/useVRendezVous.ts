import { ref, watch } from 'vue'
import { VRendezVou,  } from '@/models/VRendezVouModel'
import * as vRendezVouService from '@/services/VRendezVouService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['id']

/**
 * Composable to manage VRendezVou entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useVRendezVous() {
  /** List of all vRendezVous */
  const vRendezVous = ref<VRendezVou[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_vRendezVou') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_vRendezVou', newValue)
  })

  /**
   * Handles API response by updating the vRendezVous and pagination
   * @param data Array of VRendezVou returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: VRendezVou[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      vRendezVous.value = vRendezVous.value.concat(data)
    } else {
      vRendezVous.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('vRendezVou', 'list')
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
      const response = await columnConfigService.updateVisibleFields('vRendezVou', 'list', fields)
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
  /** Navigate to vRendezVou detail view */
  const viewVRendezVou = (vRendezVou: VRendezVou) => {
    router.push({ name: "vRendezVoudetailsview", params: { id: vRendezVou.getKeyValue() } })
  }

  /** Navigate to vRendezVou list view */
  const goToListView = () => {
    router.push({ path: "/vRendezVous" })
  }

  
  

  // API Requests
  /** Load all vRendezVous from the API */
  const loadVRendezVous = async (
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
    } = await vRendezVouService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && vRendezVous.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await vRendezVouService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single vRendezVou by id
   * @param id VRendezVou ID
   */
  const getVRendezVouById = async (id: number | string) => {
    const result = await vRendezVouService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search vRendezVous with filters, pagination, and sorting
   * @param filters Partial VRendezVou object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchVRendezVous = async (
    unpagined: boolean = false,
    filters: Partial<VRendezVou>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await vRendezVouService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await vRendezVouService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportVRendezVousToCsv = async (data: Partial<VRendezVou[]>) => {
    console.log('Exporting VRendezVou to CSV', data)
    await vRendezVouService.exportCsv(data)
  }
  
  /** Getter for pagination data */
  const getPaginationData = () => paginationData.value

  return {
    // state
    vRendezVous,
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
    exportVRendezVousToCsv,
    loadVRendezVous,
    searchVRendezVous,
    getVRendezVouById,
    viewVRendezVou,
    goToListView,
  }
}
