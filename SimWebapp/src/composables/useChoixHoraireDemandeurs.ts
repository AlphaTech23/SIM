import { ref, watch } from 'vue'
import { ChoixHoraireDemandeur, ChoixHoraireDemandeurFormDTO } from '@/models/ChoixHoraireDemandeurModel'
import * as choixHoraireDemandeurService from '@/services/ChoixHoraireDemandeurService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['id']

/**
 * Composable to manage ChoixHoraireDemandeur entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useChoixHoraireDemandeurs() {
  /** List of all choixHoraireDemandeurs */
  const choixHoraireDemandeurs = ref<ChoixHoraireDemandeur[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_choixHoraireDemandeur') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_choixHoraireDemandeur', newValue)
  })

  /**
   * Handles API response by updating the choixHoraireDemandeurs and pagination
   * @param data Array of ChoixHoraireDemandeur returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: ChoixHoraireDemandeur[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      choixHoraireDemandeurs.value = choixHoraireDemandeurs.value.concat(data)
    } else {
      choixHoraireDemandeurs.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('choixHoraireDemandeur', 'list')
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
      const response = await columnConfigService.updateVisibleFields('choixHoraireDemandeur', 'list', fields)
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
  /** Navigate to choixHoraireDemandeur detail view */
  const viewChoixHoraireDemandeur = (choixHoraireDemandeur: ChoixHoraireDemandeur) => {
    router.push({ name: "choixHoraireDemandeurdetailsview", params: { id: choixHoraireDemandeur.getKeyValue() } })
  }

  /** Navigate to choixHoraireDemandeur list view */
  const goToListView = () => {
    router.push({ path: "/choixHoraireDemandeurs" })
  }

  /** Navigate to choixHoraireDemandeur create form */
  const goToCreateFormView = () => {
    router.push({ path: "/choixHoraireDemandeurs/create" })
  }

  /** Navigate to choixHoraireDemandeur update form */
  const goToUpdateFormView = (choixHoraireDemandeur: ChoixHoraireDemandeur) => {
    router.push({ name: "choixHoraireDemandeurupdateview", params: { id: choixHoraireDemandeur.getKeyValue() } })
  }

  /**
   * Delete a choixHoraireDemandeur by id
   * @param choixHoraireDemandeur ChoixHoraireDemandeur object to delete
   */
  const deleteChoixHoraireDemandeur = async (choixHoraireDemandeur: ChoixHoraireDemandeur) => {
    const result = await choixHoraireDemandeurService.remove(choixHoraireDemandeur.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  
  /**
   * Create a new choixHoraireDemandeur
   * @param choixHoraireDemandeurFormDTO ChoixHoraireDemandeur object to create
   */
  const createChoixHoraireDemandeur = async (choixHoraireDemandeurFormDTO: Partial<ChoixHoraireDemandeurFormDTO>) => {
    const dto = new ChoixHoraireDemandeurFormDTO(choixHoraireDemandeurFormDTO)
    const choixHoraireDemandeur: Partial<ChoixHoraireDemandeur> = await dto.toEntity()
    const { data, error, errors } = await choixHoraireDemandeurService.create(choixHoraireDemandeur)
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  
  /**
   * Update an existing choixHoraireDemandeur
   * @param choixHoraireDemandeurFormDTO ChoixHoraireDemandeur object to update
   */
  const updateChoixHoraireDemandeur = async (id: number | string,choixHoraireDemandeurFormDTO: Partial<ChoixHoraireDemandeurFormDTO>) => {
    const dto = new ChoixHoraireDemandeurFormDTO(choixHoraireDemandeurFormDTO)
    const choixHoraireDemandeur: Partial<ChoixHoraireDemandeur> = await dto.toEntity()
    const { data, error } = await choixHoraireDemandeurService.update(id, choixHoraireDemandeur)
    if (error) {
      message.value = error
    }
    return data
  }

  // API Requests
  /** Load all choixHoraireDemandeurs from the API */
  const loadChoixHoraireDemandeurs = async (
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
    } = await choixHoraireDemandeurService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && choixHoraireDemandeurs.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await choixHoraireDemandeurService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single choixHoraireDemandeur by id
   * @param id ChoixHoraireDemandeur ID
   */
  const getChoixHoraireDemandeurById = async (id: number | string) => {
    const result = await choixHoraireDemandeurService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search choixHoraireDemandeurs with filters, pagination, and sorting
   * @param filters Partial ChoixHoraireDemandeur object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchChoixHoraireDemandeurs = async (
    unpagined: boolean = false,
    filters: Partial<ChoixHoraireDemandeur>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await choixHoraireDemandeurService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await choixHoraireDemandeurService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportChoixHoraireDemandeursToCsv = async (data: Partial<ChoixHoraireDemandeur[]>) => {
    console.log('Exporting ChoixHoraireDemandeur to CSV', data)
    await choixHoraireDemandeurService.exportCsv(data)
  }
  
  /** Getter for pagination data */
  const getPaginationData = () => paginationData.value

  return {
    // state
    choixHoraireDemandeurs,
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
    exportChoixHoraireDemandeursToCsv,
    loadChoixHoraireDemandeurs,
    searchChoixHoraireDemandeurs,
    getChoixHoraireDemandeurById,
    viewChoixHoraireDemandeur,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deleteChoixHoraireDemandeur,
    createChoixHoraireDemandeur,
    updateChoixHoraireDemandeur,
  }
}
