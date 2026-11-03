import api from '@/services/api'
import {getFilenameFromHeaders, triggerFileDownload} from "@/utils/download.ts";
import RestResponse from '@/models/api/RestResponseModel'
import { type IPageResponse } from '@/models/api/PageResponseModel'
import {
  PaginationRequestParameter,
  RequestModel,
  SortFieldParameter,
} from '@/models/api/RequestModel'
import { VRendezVousAccepte } from '@/models/VRendezVousAccepteModel'
import type { DataResponse, PagedDataResponse } from '@/models/api/DataResponseModel'

const restApiWithPage = api<IPageResponse<VRendezVousAccepte>>()
const restApi = api<VRendezVousAccepte>()
const BASE_URL = '/vrendezvousacceptes'

/** Helper to wrap response data in VRendezVousAccepte instances */
function wrapVRendezVousAccepte(response: DataResponse<VRendezVousAccepte> | PagedDataResponse<VRendezVousAccepte>) {
  if (Array.isArray(response.data)) {
    response.data = response.data.map((r) => new VRendezVousAccepte(r))
  } else if (response.data && typeof response.data === 'object') {
    response.data = new VRendezVousAccepte(response.data)
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
  return wrapVRendezVousAccepte(RestResponse.handlePagedResponse<VRendezVousAccepte>(response)) as PagedDataResponse<VRendezVousAccepte>
}

/** POST search with pagination */
export async function search(
  data: Partial<VRendezVousAccepte>,
  pagination?: PaginationRequestParameter,
  sortFields?: SortFieldParameter[],
) {
  const request = new RequestModel(BASE_URL + '/search', pagination, sortFields)
  const response = await restApiWithPage.POST(request.buildRequestUrl(), data)
  return wrapVRendezVousAccepte(RestResponse.handlePagedResponse<VRendezVousAccepte>(response)) as PagedDataResponse<VRendezVousAccepte>
}

/** GET by ID */
export async function getById(id: number | string | undefined) {
  if (!id) throw new Error('getById: id is required')
  const response = await restApi.GET(BASE_URL + '/' + id)
  return wrapVRendezVousAccepte(RestResponse.handleDataResponse<VRendezVousAccepte>(response)) as DataResponse<VRendezVousAccepte>
}

export async function create(data: Partial<VRendezVousAccepte>){
  const response = await restApi.POST(BASE_URL, data)
  return wrapVRendezVousAccepte(RestResponse.handleDataResponse<VRendezVousAccepte>(response)) as DataResponse<VRendezVousAccepte>
}




export async function exportCsv(
  vRendezVousAcceptes: Partial<VRendezVousAccepte[]>,
): Promise<{ success: boolean; error: string | null }> {
     const url = import.meta.env.VITE_APP_API_URL+BASE_URL+'/export/csv'

     try {
     const response = await fetch(url, {
                      method: 'POST',
                      headers: {
                        'Content-Type': 'application/json',
                      },
                      body: JSON.stringify(vRendezVousAcceptes),
   })
     if (!response.ok) {
     const errorText = await response.text()
     console.error('CSV Export Failed - Server Error:', response.status, errorText)
                      throw new Error(`CSV export failed: . Status: .`)
   }
      const blob = await response.blob()
      const contentDisposition = response.headers.get('content-disposition');
      triggerFileDownload(blob,getFilenameFromHeaders(contentDisposition,"VRendezVousAccepte.csv"));
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

