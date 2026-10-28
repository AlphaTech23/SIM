import api from '@/services/api'
import {getFilenameFromHeaders, triggerFileDownload} from "@/utils/download.ts";
import RestResponse from '@/models/api/RestResponseModel'
import { type IPageResponse } from '@/models/api/PageResponseModel'
import {
  PaginationRequestParameter,
  RequestModel,
  SortFieldParameter,
} from '@/models/api/RequestModel'
import { Membre } from '@/models/MembreModel'
import type { DataResponse, PagedDataResponse } from '@/models/api/DataResponseModel'
import type { ActivitesMembre } from '@/models/ActivitesMembreModel'
import type { RendezVou } from '@/models/RendezVouModel'
import type { SujetsMembre } from '@/models/SujetsMembreModel'


const restApiWithPage = api<IPageResponse<Membre>>()
const restApi = api<Membre>()
const BASE_URL = '/membres'

/** Helper to wrap response data in Membre instances */
function wrapMembre(response: DataResponse<Membre> | PagedDataResponse<Membre>) {
  if (Array.isArray(response.data)) {
    response.data = response.data.map((r) => new Membre(r))
  } else if (response.data && typeof response.data === 'object') {
    response.data = new Membre(response.data)
  }
  return response
}

/** GET all with pagination */
export async function getAll(
  pagination?: PaginationRequestParameter,
  sortFields?: SortFieldParameter[],
) {
  const request = new RequestModel(BASE_URL, pagination, sortFields)
  const response = await restApiWithPage.GET(request.buildRequestUrl())
  return wrapMembre(RestResponse.handlePagedResponse<Membre>(response)) as PagedDataResponse<Membre>
}

/** POST search with pagination */
export async function search(
  data: Partial<Membre>,
  pagination?: PaginationRequestParameter,
  sortFields?: SortFieldParameter[],
) {
  const request = new RequestModel(BASE_URL + '/search', pagination, sortFields)
  const response = await restApiWithPage.POST(request.buildRequestUrl(), data)
  return wrapMembre(RestResponse.handlePagedResponse<Membre>(response)) as PagedDataResponse<Membre>
}

/** GET by ID */
export async function getById(id: number | string | undefined) {
  if (!id) throw new Error('getById: id is required')
  const response = await restApi.GET(BASE_URL + '/' + id)
  return wrapMembre(RestResponse.handleDataResponse<Membre>(response)) as DataResponse<Membre>
}


/** POST create */
export async function create(data: { membre: Partial<Membre>; activitesMembres: Partial<ActivitesMembre>[]; rendezVous: Partial<RendezVou>[]; sujetsMembres: Partial<SujetsMembre>[] }){
  const response = await restApi.POST(BASE_URL, data)
  return wrapMembre(RestResponse.handleDataResponse<Membre>(response)) as DataResponse<Membre>
}


/** PUT update */
export async function update(
  id: number | string | undefined,
  data: {
    membre: Partial<Membre>
    activitesMembres: Partial<ActivitesMembre>[]
    rendezVous: Partial<RendezVou>[]
    sujetsMembres: Partial<SujetsMembre>[]
     
  }
) {
  if (!id) throw new Error('update: id is required')
  const response = await restApi.PUT(BASE_URL + '/' + id, data)
  return wrapMembre(RestResponse.handleDataResponse<Membre>(response)) as DataResponse<Membre>
}


/** DELETE remove */
export async function remove(id: number | string | undefined) {
  if (!id) throw new Error("remove: id is required")
  const response = await restApi.DELETE(BASE_URL + '/' + id)
  return {
    success: response.returnCode === 1,
    error: response.returnCode !== 1 ? response.message : null,
  };
}
export async function exportCsv(
  membres: Partial<Membre[]>,
): Promise<{ success: boolean; error: string | null }> {
     const url = import.meta.env.VITE_APP_API_URL+BASE_URL+'/export/csv'

     try {
     const response = await fetch(url, {
                      method: 'POST',
                      headers: {
                        'Content-Type': 'application/json',
                      },
                      body: JSON.stringify(membres),
   })
     if (!response.ok) {
     const errorText = await response.text()
     console.error('CSV Export Failed - Server Error:', response.status, errorText)
                      throw new Error(`CSV export failed: . Status: .`)
   }
      const blob = await response.blob()
      const contentDisposition = response.headers.get('content-disposition');
      triggerFileDownload(blob,getFilenameFromHeaders(contentDisposition,"Membre.csv"));
      return {
        success: true,
        error: null,
   }

    } catch (error) {
      console.error('Error during CSV export:', error)
      return {
        success: false,
        error: error instanceof Error ? error.message : 'An unknown error occurred during export.',
      }
    }
  }
  export async function getAllBySecteurId(
    id: number | string | undefined,
    pagination?: PaginationRequestParameter,
    sortFields?: SortFieldParameter[],
  ) {
    if (!id) throw new Error('getAllBySecteurId: id of secteur is required')
    const request = new RequestModel('/secteurs/' + id + BASE_URL, pagination, sortFields)
    const response = await restApiWithPage.GET(request.buildRequestUrl())
    return wrapMembre(
      RestResponse.handlePagedResponse<Membre>(response),
    ) as PagedDataResponse<Membre>
  }

