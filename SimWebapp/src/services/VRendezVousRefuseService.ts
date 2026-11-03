import api from '@/services/api'
import {getFilenameFromHeaders, triggerFileDownload} from "@/utils/download.ts";
import RestResponse from '@/models/api/RestResponseModel'
import { type IPageResponse } from '@/models/api/PageResponseModel'
import {
  PaginationRequestParameter,
  RequestModel,
  SortFieldParameter,
} from '@/models/api/RequestModel'
import { VRendezVousRefuse } from '@/models/VRendezVousRefuseModel'
import type { DataResponse, PagedDataResponse } from '@/models/api/DataResponseModel'

const restApiWithPage = api<IPageResponse<VRendezVousRefuse>>()
const restApi = api<VRendezVousRefuse>()
const BASE_URL = '/vrendezvousrefuses'

/** Helper to wrap response data in VRendezVousRefuse instances */
function wrapVRendezVousRefuse(response: DataResponse<VRendezVousRefuse> | PagedDataResponse<VRendezVousRefuse>) {
  if (Array.isArray(response.data)) {
    response.data = response.data.map((r) => new VRendezVousRefuse(r))
  } else if (response.data && typeof response.data === 'object') {
    response.data = new VRendezVousRefuse(response.data)
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
  return wrapVRendezVousRefuse(RestResponse.handlePagedResponse<VRendezVousRefuse>(response)) as PagedDataResponse<VRendezVousRefuse>
}

/** POST search with pagination */
export async function search(
  data: Partial<VRendezVousRefuse>,
  pagination?: PaginationRequestParameter,
  sortFields?: SortFieldParameter[],
) {
  const request = new RequestModel(BASE_URL + '/search', pagination, sortFields)
  const response = await restApiWithPage.POST(request.buildRequestUrl(), data)
  return wrapVRendezVousRefuse(RestResponse.handlePagedResponse<VRendezVousRefuse>(response)) as PagedDataResponse<VRendezVousRefuse>
}

/** GET by ID */
export async function getById(id: number | string | undefined) {
  if (!id) throw new Error('getById: id is required')
  const response = await restApi.GET(BASE_URL + '/' + id)
  return wrapVRendezVousRefuse(RestResponse.handleDataResponse<VRendezVousRefuse>(response)) as DataResponse<VRendezVousRefuse>
}

export async function create(data: Partial<VRendezVousRefuse>){
  const response = await restApi.POST(BASE_URL, data)
  return wrapVRendezVousRefuse(RestResponse.handleDataResponse<VRendezVousRefuse>(response)) as DataResponse<VRendezVousRefuse>
}




export async function exportCsv(
  vRendezVousRefuses: Partial<VRendezVousRefuse[]>,
): Promise<{ success: boolean; error: string | null }> {
     const url = import.meta.env.VITE_APP_API_URL+BASE_URL+'/export/csv'

     try {
     const response = await fetch(url, {
                      method: 'POST',
                      headers: {
                        'Content-Type': 'application/json',
                      },
                      body: JSON.stringify(vRendezVousRefuses),
   })
     if (!response.ok) {
     const errorText = await response.text()
     console.error('CSV Export Failed - Server Error:', response.status, errorText)
                      throw new Error(`CSV export failed: . Status: .`)
   }
      const blob = await response.blob()
      const contentDisposition = response.headers.get('content-disposition');
      triggerFileDownload(blob,getFilenameFromHeaders(contentDisposition,"VRendezVousRefuse.csv"));
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

