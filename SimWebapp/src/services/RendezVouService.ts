import api from '@/services/api'
import {getFilenameFromHeaders, triggerFileDownload} from "@/utils/download.ts";
import RestResponse from '@/models/api/RestResponseModel'
import { type IPageResponse } from '@/models/api/PageResponseModel'
import {
  PaginationRequestParameter,
  RequestModel,
  SortFieldParameter,
} from '@/models/api/RequestModel'
import { RendezVou } from '@/models/RendezVouModel'
import type { DataResponse, PagedDataResponse } from '@/models/api/DataResponseModel'

const restApiWithPage = api<IPageResponse<RendezVou>>()
const restApi = api<RendezVou>()
const BASE_URL = '/rendezvous'

/** Helper to wrap response data in RendezVou instances */
function wrapRendezVou(response: DataResponse<RendezVou> | PagedDataResponse<RendezVou>) {
  if (Array.isArray(response.data)) {
    response.data = response.data.map((r) => new RendezVou(r))
  } else if (response.data && typeof response.data === 'object') {
    response.data = new RendezVou(response.data)
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
  return wrapRendezVou(RestResponse.handlePagedResponse<RendezVou>(response)) as PagedDataResponse<RendezVou>
}

/** POST search with pagination */
export async function search(
  data: Partial<RendezVou>,
  pagination?: PaginationRequestParameter,
  sortFields?: SortFieldParameter[],
) {
  const request = new RequestModel(BASE_URL + '/search', pagination, sortFields)
  const response = await restApiWithPage.POST(request.buildRequestUrl(), data)
  return wrapRendezVou(RestResponse.handlePagedResponse<RendezVou>(response)) as PagedDataResponse<RendezVou>
}

/** GET by ID */
export async function getById(id: number | string | undefined) {
  if (!id) throw new Error('getById: id is required')
  const response = await restApi.GET(BASE_URL + '/' + id)
  return wrapRendezVou(RestResponse.handleDataResponse<RendezVou>(response)) as DataResponse<RendezVou>
}

export async function create(data: Partial<RendezVou>){
  const response = await restApi.POST(BASE_URL, data)
  return wrapRendezVou(RestResponse.handleDataResponse<RendezVou>(response)) as DataResponse<RendezVou>
}

/** PUT update */
export async function update(id: number | string | undefined, data: Partial<RendezVou>) {
  if (!id) throw new Error("update: id is required")
  const response = await restApi.PUT(BASE_URL + '/' + id, data)
  return wrapRendezVou(RestResponse.handleDataResponse<RendezVou>(response)) as DataResponse<RendezVou>
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
  rendezVous: Partial<RendezVou[]>,
): Promise<{ success: boolean; error: string | null }> {
     const url = import.meta.env.VITE_APP_API_URL+BASE_URL+'/export/csv'

     try {
     const response = await fetch(url, {
                      method: 'POST',
                      headers: {
                        'Content-Type': 'application/json',
                      },
                      body: JSON.stringify(rendezVous),
   })
     if (!response.ok) {
     const errorText = await response.text()
     console.error('CSV Export Failed - Server Error:', response.status, errorText)
                      throw new Error(`CSV export failed: . Status: .`)
   }
      const blob = await response.blob()
      const contentDisposition = response.headers.get('content-disposition');
      triggerFileDownload(blob,getFilenameFromHeaders(contentDisposition,"RendezVou.csv"));
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
  export async function getAllByMembreId(
    id: number | string | undefined,
    pagination?: PaginationRequestParameter,
    sortFields?: SortFieldParameter[],
  ) {
    if (!id) throw new Error('getAllByMembreId: id of membre is required')
    const request = new RequestModel('/membres/' + id + BASE_URL, pagination, sortFields)
    const response = await restApiWithPage.GET(request.buildRequestUrl())
    return wrapRendezVou(
      RestResponse.handlePagedResponse<RendezVou>(response),
    ) as PagedDataResponse<RendezVou>
  }

