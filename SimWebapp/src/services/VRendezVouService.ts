import api from '@/services/api'
import {getFilenameFromHeaders, triggerFileDownload} from "@/utils/download.ts";
import RestResponse from '@/models/api/RestResponseModel'
import { type IPageResponse } from '@/models/api/PageResponseModel'
import {
  PaginationRequestParameter,
  RequestModel,
  SortFieldParameter,
} from '@/models/api/RequestModel'
import { VRendezVou } from '@/models/VRendezVouModel'
import type { DataResponse, PagedDataResponse } from '@/models/api/DataResponseModel'

const restApiWithPage = api<IPageResponse<VRendezVou>>()
const restApi = api<VRendezVou>()
const BASE_URL = '/vrendezvous'

/** Helper to wrap response data in VRendezVou instances */
function wrapVRendezVou(response: DataResponse<VRendezVou> | PagedDataResponse<VRendezVou>) {
  if (Array.isArray(response.data)) {
    response.data = response.data.map((r) => new VRendezVou(r))
  } else if (response.data && typeof response.data === 'object') {
    response.data = new VRendezVou(response.data)
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
  return wrapVRendezVou(RestResponse.handlePagedResponse<VRendezVou>(response)) as PagedDataResponse<VRendezVou>
}

/** POST search with pagination */
export async function search(
  data: Partial<VRendezVou>,
  pagination?: PaginationRequestParameter,
  sortFields?: SortFieldParameter[],
) {
  const request = new RequestModel(BASE_URL + '/search', pagination, sortFields)
  const response = await restApiWithPage.POST(request.buildRequestUrl(), data)
  return wrapVRendezVou(RestResponse.handlePagedResponse<VRendezVou>(response)) as PagedDataResponse<VRendezVou>
}

/** GET by ID */
export async function getById(id: number | string | undefined) {
  if (!id) throw new Error('getById: id is required')
  const response = await restApi.GET(BASE_URL + '/' + id)
  return wrapVRendezVou(RestResponse.handleDataResponse<VRendezVou>(response)) as DataResponse<VRendezVou>
}

export async function create(data: Partial<VRendezVou>){
  const response = await restApi.POST(BASE_URL, data)
  return wrapVRendezVou(RestResponse.handleDataResponse<VRendezVou>(response)) as DataResponse<VRendezVou>
}




export async function exportCsv(
  vRendezVous: Partial<VRendezVou[]>,
): Promise<{ success: boolean; error: string | null }> {
     const url = import.meta.env.VITE_APP_API_URL+BASE_URL+'/export/csv'

     try {
     const response = await fetch(url, {
                      method: 'POST',
                      headers: {
                        'Content-Type': 'application/json',
                      },
                      body: JSON.stringify(vRendezVous),
   })
     if (!response.ok) {
     const errorText = await response.text()
     console.error('CSV Export Failed - Server Error:', response.status, errorText)
                      throw new Error(`CSV export failed: . Status: .`)
   }
      const blob = await response.blob()
      const contentDisposition = response.headers.get('content-disposition');
      triggerFileDownload(blob,getFilenameFromHeaders(contentDisposition,"VRendezVou.csv"));
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

