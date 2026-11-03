import { ref, watch } from 'vue'
import { TypeOffre, TypeOffreFormDTO } from '@/models/TypeOffreModel'
import * as typeOffreService from '@/services/TypeOffreService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'

export const DEFAULT_LIST_VIEW_FIELDS = ['id']

/**
 * Composable to manage TypeOffre entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useTypeOffres() {
  /** List of all typeOffres */
  const typeOffres = ref<TypeOffre[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_typeOffre') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_typeOffre', newValue)
  })

  /**
   * Handles API response by updating the typeOffres and pagination
   * @param data Array of TypeOffre returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: TypeOffre[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      typeOffres.value = typeOffres.value.concat(data)
    } else {
      typeOffres.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('typeOffre', 'list')
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
      const response = await columnConfigService.updateVisibleFields('typeOffre', 'list', fields)
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
  /** Navigate to typeOffre detail view */
  const viewTypeOffre = (typeOffre: TypeOffre) => {
    router.push({ name: "typeOffredetailsview", params: { id: typeOffre.getKeyValue() } })
  }

  /** Navigate to typeOffre list view */
  const goToListView = () => {
    router.push({ path: "/typeOffres" })
  }

  /** Navigate to typeOffre create form */
  const goToCreateFormView = () => {
    router.push({ path: "/typeOffres/create" })
  }

  /** Navigate to typeOffre update form */
  const goToUpdateFormView = (typeOffre: TypeOffre) => {
    router.push({ name: "typeOffreupdateview", params: { id: typeOffre.getKeyValue() } })
  }

  /**
   * Delete a typeOffre by id
   * @param typeOffre TypeOffre object to delete
   */
  const deleteTypeOffre = async (typeOffre: TypeOffre) => {
    const result = await typeOffreService.remove(typeOffre.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  
  /**
   * Create a new typeOffre
   * @param typeOffreFormDTO TypeOffre object to create
   */
  const createTypeOffre = async (typeOffreFormDTO: Partial<TypeOffreFormDTO>) => {
    const dto = new TypeOffreFormDTO(typeOffreFormDTO)
    const typeOffre: Partial<TypeOffre> = await dto.toEntity()
    const { data, error, errors } = await typeOffreService.create(typeOffre)
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  
  /**
   * Update an existing typeOffre
   * @param typeOffreFormDTO TypeOffre object to update
   */
  const updateTypeOffre = async (id: number | string,typeOffreFormDTO: Partial<TypeOffreFormDTO>) => {
    const dto = new TypeOffreFormDTO(typeOffreFormDTO)
    const typeOffre: Partial<TypeOffre> = await dto.toEntity()
    const { data, error } = await typeOffreService.update(id, typeOffre)
    if (error) {
      message.value = error
    }
    return data
  }

  // API Requests
  /** Load all typeOffres from the API */
  const loadTypeOffres = async (
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
    } = await typeOffreService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && typeOffres.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await typeOffreService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single typeOffre by id
   * @param id TypeOffre ID
   */
  const getTypeOffreById = async (id: number | string) => {
    const result = await typeOffreService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search typeOffres with filters, pagination, and sorting
   * @param filters Partial TypeOffre object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchTypeOffres = async (
    unpagined: boolean = false,
    filters: Partial<TypeOffre>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await typeOffreService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await typeOffreService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportTypeOffresToCsv = async (data: Partial<TypeOffre[]>) => {
    console.log('Exporting TypeOffre to CSV', data)
    await typeOffreService.exportCsv(data)
  }
  
  /** Getter for pagination data */
  const getPaginationData = () => paginationData.value

  return {
    // state
    typeOffres,
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
    exportTypeOffresToCsv,
    loadTypeOffres,
    searchTypeOffres,
    getTypeOffreById,
    viewTypeOffre,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deleteTypeOffre,
    createTypeOffre,
    updateTypeOffre,
  }
}
