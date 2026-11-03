import { ref, watch } from 'vue'
import { Offre, OffreFormDTO } from '@/models/OffreModel'
import * as offreService from '@/services/OffreService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'
import { CiblesOffre, CiblesOffreFormDTO } from '@/models/CiblesOffreModel'
import { HistoOffre, HistoOffreFormDTO } from '@/models/HistoOffreModel'



export const DEFAULT_LIST_VIEW_FIELDS = ['id']

/**
 * Composable to manage Offre entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useOffres() {
  /** List of all offres */
  const offres = ref<Offre[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_offre') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_offre', newValue)
  })

  /**
   * Handles API response by updating the offres and pagination
   * @param data Array of Offre returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: Offre[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      offres.value = offres.value.concat(data)
    } else {
      offres.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('offre', 'list')
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
      const response = await columnConfigService.updateVisibleFields('offre', 'list', fields)
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
  /** Navigate to offre detail view */
  const viewOffre = (offre: Offre) => {
    router.push({ name: "offredetailsview", params: { id: offre.getKeyValue() } })
  }

  /** Navigate to offre list view */
  const goToListView = () => {
    router.push({ path: "/offres" })
  }

  /** Navigate to offre create form */
  const goToCreateFormView = () => {
    router.push({ path: "/offres/create" })
  }

  /** Navigate to offre update form */
  const goToUpdateFormView = (offre: Offre) => {
    router.push({ name: "offreupdateview", params: { id: offre.getKeyValue() } })
  }

  /**
   * Delete a offre by id
   * @param offre Offre object to delete
   */
  const deleteOffre = async (offre: Offre) => {
    const result = await offreService.remove(offre.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Create a new offre
   * @param fullData Object containing offre and his details
   */
  const createOffre = async (fullData: { offre: Partial<OffreFormDTO>; ciblesOffres: CiblesOffreFormDTO[]; histoOffres: HistoOffreFormDTO[] })=> {
    const dto = new OffreFormDTO(fullData.offre)
    const offre: Partial<Offre> = await dto.toEntity()
    const ciblesOffres: Partial<CiblesOffre>[] = await CiblesOffreFormDTO.toEntities(fullData.ciblesOffres)
    const histoOffres: Partial<HistoOffre>[] = await HistoOffreFormDTO.toEntities(fullData.histoOffres)
    
    const { data, error, errors } = await offreService.create({ offre, ciblesOffres, histoOffres })
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  /**
  * Update an existing offre with full details
  * @param id Offre ID to update
  * @param fullData Object containing offre and its details
  */
  const updateOffre = async (
    id: number | string,
    fullData: {
      offre: Partial<OffreFormDTO>
      ; ciblesOffres: CiblesOffreFormDTO[]; histoOffres: HistoOffreFormDTO[] 
    },
  ) => {
    const dto = new OffreFormDTO(fullData.offre)
    const offre: Partial<Offre> = await dto.toEntity()

    const ciblesOffres: Partial<CiblesOffre>[] = await CiblesOffreFormDTO.toEntities(
      fullData.ciblesOffres,
    )
    const histoOffres: Partial<HistoOffre>[] = await HistoOffreFormDTO.toEntities(
      fullData.histoOffres,
    )
    
    const { data, error, errors } = await offreService.update(
    id, 
    { 
      offre
      , ciblesOffres
      , histoOffres
       
    }
   )
   if (error) {
    message.value = error
   }
   return { data, errors }
  }
  

  // API Requests
  /** Load all offres from the API */
  const loadOffres = async (
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
    } = await offreService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && offres.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await offreService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single offre by id
   * @param id Offre ID
   */
  const getOffreById = async (id: number | string) => {
    const result = await offreService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search offres with filters, pagination, and sorting
   * @param filters Partial Offre object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchOffres = async (
    unpagined: boolean = false,
    filters: Partial<Offre>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await offreService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await offreService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportOffresToCsv = async (data: Partial<Offre[]>) => {
    console.log('Exporting Offre to CSV', data)
    await offreService.exportCsv(data)
  }
  
  const getAllOffresByMembreId = async (
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
    } = await offreService.getAllByMembreId(membreId, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext() && offres.value.length > 0) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await offreService.getAllByMembreId(
          membreId,
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
    offres,
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
    exportOffresToCsv,
    loadOffres,
    searchOffres,
    getOffreById,
    viewOffre,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deleteOffre,
    createOffre,
    updateOffre,
    getAllOffresByMembreId,
    
  }
}
