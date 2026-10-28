import api from '@/services/api'
import {getFilenameFromHeaders, triggerFileDownload} from "@/utils/download.ts";
import RestResponse from '@/models/api/RestResponseModel'
import { type IPageResponse } from '@/models/api/PageResponseModel'
import {
  PaginationRequestParameter,
  RequestModel,
  SortFieldParameter,
} from '@/models/api/RequestModel'
import { ActivitesMembre } from '@/models/ActivitesMembreModel'
import type { DataResponse, PagedDataResponse } from '@/models/api/DataResponseModel'

const restApiWithPage = api<IPageResponse<ActivitesMembre>>()
const restApi = api<ActivitesMembre>()
const BASE_URL = '/activitesmembres'

/** Helper to wrap response data in ActivitesMembre instances */
function wrapActivitesMembre(response: DataResponse<ActivitesMembre> | PagedDataResponse<ActivitesMembre>) {
  if (Array.isArray(response.data)) {
    response.data = response.data.map((r) => new ActivitesMembre(r))
  } else if (response.data && typeof response.data === 'object') {
    response.data = new ActivitesMembre(response.data)
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
  return wrapActivitesMembre(RestResponse.handlePagedResponse<ActivitesMembre>(response)) as PagedDataResponse<ActivitesMembre>
}

/** POST search with pagination */
export async function search(
  data: Partial<ActivitesMembre>,
  pagination?: PaginationRequestParameter,
  sortFields?: SortFieldParameter[],
) {
  const request = new RequestModel(BASE_URL + '/search', pagination, sortFields)
  const response = await restApiWithPage.POST(request.buildRequestUrl(), data)
  return wrapActivitesMembre(RestResponse.handlePagedResponse<ActivitesMembre>(response)) as PagedDataResponse<ActivitesMembre>
}

/** GET by ID */
export async function getById(id: number | string | undefined) {
  if (!id) throw new Error('getById: id is required')
  const response = await restApi.GET(BASE_URL + '/' + id)
  return wrapActivitesMembre(RestResponse.handleDataResponse<ActivitesMembre>(response)) as DataResponse<ActivitesMembre>
}

export async function create(data: Partial<ActivitesMembre>){
  const response = await restApi.POST(BASE_URL, data)
  return wrapActivitesMembre(RestResponse.handleDataResponse<ActivitesMembre>(response)) as DataResponse<ActivitesMembre>
}

/** PUT update */
export async function update(id: number | string | undefined, data: Partial<ActivitesMembre>) {
  if (!id) throw new Error("update: id is required")
  const response = await restApi.PUT(BASE_URL + '/' + id, data)
  return wrapActivitesMembre(RestResponse.handleDataResponse<ActivitesMembre>(response)) as DataResponse<ActivitesMembre>
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
  activitesMembres: Partial<ActivitesMembre[]>,
): Promise<{ success: boolean; error: string | null }> {
     const url = import.meta.env.VITE_APP_API_URL+BASE_URL+'/export/csv'

     try {
     const response = await fetch(url, {
                      method: 'POST',
                      headers: {
                        'Content-Type': 'application/json',
                      },
                      body: JSON.stringify(activitesMembres),
   })
     if (!response.ok) {
     const errorText = await response.text()
     console.error('CSV Export Failed - Server Error:', response.status, errorText)
                      throw new Error(`CSV export failed: . Status: .`)
   }
      const blob = await response.blob()
      const contentDisposition = response.headers.get('content-disposition');
      triggerFileDownload(blob,getFilenameFromHeaders(contentDisposition,"ActivitesMembre.csv"));
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
    return wrapActivitesMembre(
      RestResponse.handlePagedResponse<ActivitesMembre>(response),
    ) as PagedDataResponse<ActivitesMembre>
  }export async function getAllByActiviteId(
    id: number | string | undefined,
    pagination?: PaginationRequestParameter,
    sortFields?: SortFieldParameter[],
  ) {
    if (!id) throw new Error('getAllByActiviteId: id of activite is required')
    const request = new RequestModel('/activites/' + id + BASE_URL, pagination, sortFields)
    const response = await restApiWithPage.GET(request.buildRequestUrl())
    return wrapActivitesMembre(
      RestResponse.handlePagedResponse<ActivitesMembre>(response),
    ) as PagedDataResponse<ActivitesMembre>
  }

