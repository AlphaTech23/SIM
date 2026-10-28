import { ref, watch } from 'vue'
import { Secteur, SecteurFormDTO } from '@/models/SecteurModel'
import * as secteurService from '@/services/SecteurService'
import * as columnConfigService from '@/services/ColumnConfigService'
import { useLoading } from './useLoading'
import { PaginationRequestParameter, SortFieldParameter } from '@/models/api/RequestModel'
import { PaginationData } from '@/models/api/PageResponseModel'
import { useRouter } from 'vue-router'
import { Membre, MembreFormDTO } from '@/models/MembreModel'



export const DEFAULT_LIST_VIEW_FIELDS = ['id']

/**
 * Composable to manage Secteur entities including CRUD operations,
 * search, pagination, foreign key loading, and navigation.
 */
export function useSecteurs() {
  /** List of all secteurs */
  const secteurs = ref<Secteur[]>([])

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
  const savedLayoutMode = localStorage.getItem('layoutMode_secteur') as 'list' | 'card' | null
  const layoutMode = ref<'list' | 'card'>(savedLayoutMode || 'list')

  watch(layoutMode, (newValue) => {
    localStorage.setItem('layoutMode_secteur', newValue)
  })

  /**
   * Handles API response by updating the secteurs and pagination
   * @param data Array of Secteur returned by the API
   * @param err Optional error message
   * @param pagination Optional pagination data
   */
  const handleResponse = (
    data: Secteur[],
    err: string | undefined,
    pagination?: PaginationData,
    concatData: boolean = false,
  ) => {
    if (err) {
      message.value = err
    }
    if (concatData) {
      secteurs.value = secteurs.value.concat(data)
    } else {
      secteurs.value = data
    }
    if (pagination !== undefined) {
      paginationData.value = new PaginationData(pagination)
    }
  }

  const loadColumnConfig = async () => {
    try {
      const response = await columnConfigService.getVisibleFields('secteur', 'list')
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
      const response = await columnConfigService.updateVisibleFields('secteur', 'list', fields)
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
  /** Navigate to secteur detail view */
  const viewSecteur = (secteur: Secteur) => {
    router.push({ name: "secteurdetailsview", params: { id: secteur.getKeyValue() } })
  }

  /** Navigate to secteur list view */
  const goToListView = () => {
    router.push({ path: "/secteurs" })
  }

  /** Navigate to secteur create form */
  const goToCreateFormView = () => {
    router.push({ path: "/secteurs/create" })
  }

  /** Navigate to secteur update form */
  const goToUpdateFormView = (secteur: Secteur) => {
    router.push({ name: "secteurupdateview", params: { id: secteur.getKeyValue() } })
  }

  /**
   * Delete a secteur by id
   * @param secteur Secteur object to delete
   */
  const deleteSecteur = async (secteur: Secteur) => {
    const result = await secteurService.remove(secteur.getKeyValue())
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Create a new secteur
   * @param fullData Object containing secteur and his details
   */
  const createSecteur = async (fullData: { secteur: Partial<SecteurFormDTO>; membres: MembreFormDTO[] })=> {
    const dto = new SecteurFormDTO(fullData.secteur)
    const secteur: Partial<Secteur> = await dto.toEntity()
    const membres: Partial<Membre>[] = await MembreFormDTO.toEntities(fullData.membres)
    
    const { data, error, errors } = await secteurService.create({ secteur, membres })
    if (error) {
      message.value = error
    }
    return { data, errors }
  }
  
  /**
  * Update an existing secteur with full details
  * @param id Secteur ID to update
  * @param fullData Object containing secteur and its details
  */
  const updateSecteur = async (
    id: number | string,
    fullData: {
      secteur: Partial<SecteurFormDTO>
      ; membres: MembreFormDTO[] 
    },
  ) => {
    const dto = new SecteurFormDTO(fullData.secteur)
    const secteur: Partial<Secteur> = await dto.toEntity()

    const membres: Partial<Membre>[] = await MembreFormDTO.toEntities(
      fullData.membres,
    )
    
    const { data, error, errors } = await secteurService.update(
    id, 
    { 
      secteur
      , membres
       
    }
   )
   if (error) {
    message.value = error
   }
   return { data, errors }
  }
  

  // API Requests
  /** Load all secteurs from the API */
  const loadSecteurs = async (
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
    } = await secteurService.getAll(pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined && secteurs.value.length > 0) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await secteurService.getAll(paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }

  /**
   * Get a single secteur by id
   * @param id Secteur ID
   */
  const getSecteurById = async (id: number | string) => {
    const result = await secteurService.getById(id)
    if (result.error) {
      message.value = result.error
    }
    return result
  }

  /**
   * Search secteurs with filters, pagination, and sorting
   * @param filters Partial Secteur object containing search filters
   * @param pagination Pagination request parameters
   * @param sortFields Array of fields to sort
   */
  const searchSecteurs = async (
    unpagined: boolean = false,
    filters: Partial<Secteur>,
    pagination: PaginationRequestParameter,
    sortFields: SortFieldParameter[],
  ) => {
    message.value = null
    startLoading()
    const {
      data,
      error: err,
      pagination: paginationresult,
    } = await secteurService.search(filters, pagination, sortFields)
    handleResponse(data, err, paginationresult)

    if (unpagined) {
      while (paginationData.value.hasNext()) {
        paginationData.value.nextPage()
        const {
          data: nextData,
          error: nextErr,
          pagination: nextPagination,
        } = await secteurService.search(filters, paginationData.value.toParameter(), sortFields)
        handleResponse(nextData, nextErr, nextPagination, true)
      }
    }
    stopLoading()
  }
  const exportSecteursToCsv = async (data: Partial<Secteur[]>) => {
    console.log('Exporting Secteur to CSV', data)
    await secteurService.exportCsv(data)
  }
  
  /** Getter for pagination data */
  const getPaginationData = () => paginationData.value

  return {
    // state
    secteurs,
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
    exportSecteursToCsv,
    loadSecteurs,
    searchSecteurs,
    getSecteurById,
    viewSecteur,
    goToListView,
    goToCreateFormView,
    goToUpdateFormView,
    deleteSecteur,
    createSecteur,
    updateSecteur,
  }
}
