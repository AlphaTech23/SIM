import { ref, watch } from 'vue'
import { VRendezVousEnAttente,  } from '@/models/VRendezVousEnAttenteModel'
import * as vRendezVousEnAttenteService from '@/services/VRendezVousEnAttenteService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['id']

/**
 * Composable to manage VRendezVousEnAttente entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useVRendezVousEnAttentes() {
  /** List of all vRendezVousEnAttentes */
  const vRendezVousEnAttentes = ref<VRendezVousEnAttente[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_vRendezVousEnAttente') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_vRendezVousEnAttente', newValue)
  })

  /**
   * Handles API response by updating the vRendezVousEnAttentes and pagination
   * @param data Array of VRendezVousEnAttente returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: VRendezVousEnAttente[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      vRendezVousEnAttentes.value = vRendezVousEnAttentes.value.concat(data)
    } else {
      vRendezVousEnAttentes.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('vRendezVousEnAttente', 'list')
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
      const response = await columnConfigService.updateVisibleFields('vRendezVousEnAttente', 'list', fields)
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
  /** Navigate to vRendezVousEnAttente detail view */
  const viewVRendezVousEnAttente = (vRendezVousEnAttente: VRendezVousEnAttente) => {
    router.push({ name: "vRendezVousEnAttentedetailsview", params: { id: vRendezVousEnAttente.getKeyValue() } })
  }

  /** Navigate to vRendezVousEnAttente list view */
  const goToListView = () => {
    router.push({ path: "/vRendezVousEnAttentes" })
  }

  
  

  // API Requests
  /** Load all vRendezVousEnAttentes from the API */
  const loadVRendezVousEnAttentes = async (
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
    } = await vRendezVousEnAttenteService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && vRendezVousEnAttentes.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await vRendezVousEnAttenteService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single vRendezVousEnAttente by id
   * @param id VRendezVousEnAttente ID
   */
  const getVRendezVousEnAttenteById = async (id: number | string) => {
    const result = await vRendezVousEnAttenteService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search vRendezVousEnAttentes with filters, pagination, and sorting
   * @param filters Partial VRendezVousEnAttente object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchVRendezVousEnAttentes = async (
    unpagined: boolean = false,
    filters: Partial<VRendezVousEnAttente>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await vRendezVousEnAttenteService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await vRendezVousEnAttenteService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportVRendezVousEnAttentesToCsv = async (data: Partial<VRendezVousEnAttente[]>) => {
    console.log('Exporting VRendezVousEnAttente to CSV', data)
    await vRendezVousEnAttenteService.exportCsv(data)
  }
  
  /** Getter for pagination data */
  const getPaginationData = () => paginationData.value

  return {
    // state
    vRendezVousEnAttentes,
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
    exportVRendezVousEnAttentesToCsv,
    loadVRendezVousEnAttentes,
    searchVRendezVousEnAttentes,
    getVRendezVousEnAttenteById,
    viewVRendezVousEnAttente,
    goToListView,
  }
}
