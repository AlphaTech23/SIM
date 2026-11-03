import api from '@/services/api'
import {getFilenameFromHeaders, triggerFileDownload} from "@/utils/download.ts";
import RestResponse from '@/models/api/RestResponseModel'
import { type IPageResponse } from '@/models/api/PageResponseModel'
import {
  PaginationRequestParameter,
  RequestModel,
  SortFieldParameter,
} from '@/models/api/RequestModel'
import { StatutOffre } from '@/models/StatutOffreModel'
import type { DataResponse, PagedDataResponse } from '@/models/api/DataResponseModel'

const restApiWithPage = api<IPageResponse<StatutOffre>>()
const restApi = api<StatutOffre>()
const BASE_URL = '/statutoffres'

/** Helper to wrap response data in StatutOffre instances */
function wrapStatutOffre(response: DataResponse<StatutOffre> | PagedDataResponse<StatutOffre>) {
  if (Array.isArray(response.data)) {
    response.data = response.data.map((r) => new StatutOffre(r))
  } else if (response.data && typeof response.data === 'object') {
    response.data = new StatutOffre(response.data)
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
  return wrapStatutOffre(RestResponse.handlePagedResponse<StatutOffre>(response)) as PagedDataResponse<StatutOffre>
}

/** POST search with pagination */
export async function search(
  data: Partial<StatutOffre>,
  pagination?: PaginationRequestParameter,
  sortFields?: SortFieldParameter[],
) {
  const request = new RequestModel(BASE_URL + '/search', pagination, sortFields)
  const response = await restApiWithPage.POST(request.buildRequestUrl(), data)
  return wrapStatutOffre(RestResponse.handlePagedResponse<StatutOffre>(response)) as PagedDataResponse<StatutOffre>
}

/** GET by ID */
export async function getById(id: number | string | undefined) {
  if (!id) throw new Error('getById: id is required')
  const response = await restApi.GET(BASE_URL + '/' + id)
  return wrapStatutOffre(RestResponse.handleDataResponse<StatutOffre>(response)) as DataResponse<StatutOffre>
}

export async function create(data: Partial<StatutOffre>){
  const response = await restApi.POST(BASE_URL, data)
  return wrapStatutOffre(RestResponse.handleDataResponse<StatutOffre>(response)) as DataResponse<StatutOffre>
}

/** PUT update */
export async function update(id: number | string | undefined, data: Partial<StatutOffre>) {
  if (!id) throw new Error("update: id is required")
  const response = await restApi.PUT(BASE_URL + '/' + id, data)
  return wrapStatutOffre(RestResponse.handleDataResponse<StatutOffre>(response)) as DataResponse<StatutOffre>
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
  statutOffres: Partial<StatutOffre[]>,
): Promise<{ success: boolean; error: string | null }> {
     const url = import.meta.env.VITE_APP_API_URL+BASE_URL+'/export/csv'

     try {
     const response = await fetch(url, {
                      method: 'POST',
                      headers: {
                        'Content-Type': 'application/json',
                      },
                      body: JSON.stringify(statutOffres),
   })
     if (!response.ok) {
     const errorText = await response.text()
     console.error('CSV Export Failed - Server Error:', response.status, errorText)
                      throw new Error(`CSV export failed: . Status: .`)
   }
      const blob = await response.blob()
      const contentDisposition = response.headers.get('content-disposition');
      triggerFileDownload(blob,getFilenameFromHeaders(contentDisposition,"StatutOffre.csv"));
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

