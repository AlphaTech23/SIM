import api from '@/services/api'
import {getFilenameFromHeaders, triggerFileDownload} from "@/utils/download.ts";
import RestResponse from '@/models/api/RestResponseModel'
import { type IPageResponse } from '@/models/api/PageResponseModel'
import {
  PaginationRequestParameter,
  RequestModel,
  SortFieldParameter,
} from '@/models/api/RequestModel'
import { VRendezVousEnAttente } from '@/models/VRendezVousEnAttenteModel'
import type { DataResponse, PagedDataResponse } from '@/models/api/DataResponseModel'

const restApiWithPage = api<IPageResponse<VRendezVousEnAttente>>()
const restApi = api<VRendezVousEnAttente>()
const BASE_URL = '/vrendezvousenattentes'

/** Helper to wrap response data in VRendezVousEnAttente instances */
function wrapVRendezVousEnAttente(response: DataResponse<VRendezVousEnAttente> | PagedDataResponse<VRendezVousEnAttente>) {
  if (Array.isArray(response.data)) {
    response.data = response.data.map((r) => new VRendezVousEnAttente(r))
  } else if (response.data && typeof response.data === 'object') {
    response.data = new VRendezVousEnAttente(response.data)
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
  return wrapVRendezVousEnAttente(RestResponse.handlePagedResponse<VRendezVousEnAttente>(response)) as PagedDataResponse<VRendezVousEnAttente>
}

/** POST search with pagination */
export async function search(
  data: Partial<VRendezVousEnAttente>,
  pagination?: PaginationRequestParameter,
  sortFields?: SortFieldParameter[],
) {
  const request = new RequestModel(BASE_URL + '/search', pagination, sortFields)
  const response = await restApiWithPage.POST(request.buildRequestUrl(), data)
  return wrapVRendezVousEnAttente(RestResponse.handlePagedResponse<VRendezVousEnAttente>(response)) as PagedDataResponse<VRendezVousEnAttente>
}

/** GET by ID */
export async function getById(id: number | string | undefined) {
  if (!id) throw new Error('getById: id is required')
  const response = await restApi.GET(BASE_URL + '/' + id)
  return wrapVRendezVousEnAttente(RestResponse.handleDataResponse<VRendezVousEnAttente>(response)) as DataResponse<VRendezVousEnAttente>
}

export async function create(data: Partial<VRendezVousEnAttente>){
  const response = await restApi.POST(BASE_URL, data)
  return wrapVRendezVousEnAttente(RestResponse.handleDataResponse<VRendezVousEnAttente>(response)) as DataResponse<VRendezVousEnAttente>
}




export async function exportCsv(
  vRendezVousEnAttentes: Partial<VRendezVousEnAttente[]>,
): Promise<{ success: boolean; error: string | null }> {
     const url = import.meta.env.VITE_APP_API_URL+BASE_URL+'/export/csv'

     try {
     const response = await fetch(url, {
                      method: 'POST',
                      headers: {
                        'Content-Type': 'application/json',
                      },
                      body: JSON.stringify(vRendezVousEnAttentes),
   })
     if (!response.ok) {
     const errorText = await response.text()
     console.error('CSV Export Failed - Server Error:', response.status, errorText)
                      throw new Error(`CSV export failed: . Status: .`)
   }
      const blob = await response.blob()
      const contentDisposition = response.headers.get('content-disposition');
      triggerFileDownload(blob,getFilenameFromHeaders(contentDisposition,"VRendezVousEnAttente.csv"));
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

