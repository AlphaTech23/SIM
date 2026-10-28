import api from '@/services/api'
import {getFilenameFromHeaders, triggerFileDownload} from "@/utils/download.ts";
import RestResponse from '@/models/api/RestResponseModel'
import { type IPageResponse } from '@/models/api/PageResponseModel'
import {
  PaginationRequestParameter,
  RequestModel,
  SortFieldParameter,
} from '@/models/api/RequestModel'
import { StatutRendezVou } from '@/models/StatutRendezVouModel'
import type { DataResponse, PagedDataResponse } from '@/models/api/DataResponseModel'

const restApiWithPage = api<IPageResponse<StatutRendezVou>>()
const restApi = api<StatutRendezVou>()
const BASE_URL = '/statutrendezvous'

/** Helper to wrap response data in StatutRendezVou instances */
function wrapStatutRendezVou(response: DataResponse<StatutRendezVou> | PagedDataResponse<StatutRendezVou>) {
  if (Array.isArray(response.data)) {
    response.data = response.data.map((r) => new StatutRendezVou(r))
  } else if (response.data && typeof response.data === 'object') {
    response.data = new StatutRendezVou(response.data)
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
  return wrapStatutRendezVou(RestResponse.handlePagedResponse<StatutRendezVou>(response)) as PagedDataResponse<StatutRendezVou>
}

/** POST search with pagination */
export async function search(
  data: Partial<StatutRendezVou>,
  pagination?: PaginationRequestParameter,
  sortFields?: SortFieldParameter[],
) {
  const request = new RequestModel(BASE_URL + '/search', pagination, sortFields)
  const response = await restApiWithPage.POST(request.buildRequestUrl(), data)
  return wrapStatutRendezVou(RestResponse.handlePagedResponse<StatutRendezVou>(response)) as PagedDataResponse<StatutRendezVou>
}

/** GET by ID */
export async function getById(id: number | string | undefined) {
  if (!id) throw new Error('getById: id is required')
  const response = await restApi.GET(BASE_URL + '/' + id)
  return wrapStatutRendezVou(RestResponse.handleDataResponse<StatutRendezVou>(response)) as DataResponse<StatutRendezVou>
}

export async function create(data: Partial<StatutRendezVou>){
  const response = await restApi.POST(BASE_URL, data)
  return wrapStatutRendezVou(RestResponse.handleDataResponse<StatutRendezVou>(response)) as DataResponse<StatutRendezVou>
}

/** PUT update */
export async function update(id: number | string | undefined, data: Partial<StatutRendezVou>) {
  if (!id) throw new Error("update: id is required")
  const response = await restApi.PUT(BASE_URL + '/' + id, data)
  return wrapStatutRendezVou(RestResponse.handleDataResponse<StatutRendezVou>(response)) as DataResponse<StatutRendezVou>
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
  statutRendezVous: Partial<StatutRendezVou[]>,
): Promise<{ success: boolean; error: string | null }> {
     const url = import.meta.env.VITE_APP_API_URL+BASE_URL+'/export/csv'

     try {
     const response = await fetch(url, {
                      method: 'POST',
                      headers: {
                        'Content-Type': 'application/json',
                      },
                      body: JSON.stringify(statutRendezVous),
   })
     if (!response.ok) {
     const errorText = await response.text()
     console.error('CSV Export Failed - Server Error:', response.status, errorText)
                      throw new Error(`CSV export failed: . Status: .`)
   }
      const blob = await response.blob()
      const contentDisposition = response.headers.get('content-disposition');
      triggerFileDownload(blob,getFilenameFromHeaders(contentDisposition,"StatutRendezVou.csv"));
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
  export async function getAllByStatutId(
    id: number | string | undefined,
    pagination?: PaginationRequestParameter,
    sortFields?: SortFieldParameter[],
  ) {
    if (!id) throw new Error('getAllByStatutId: id of statut is required')
    const request = new RequestModel('/statuts/' + id + BASE_URL, pagination, sortFields)
    const response = await restApiWithPage.GET(request.buildRequestUrl())
    return wrapStatutRendezVou(
      RestResponse.handlePagedResponse<StatutRendezVou>(response),
    ) as PagedDataResponse<StatutRendezVou>
  }

