import api from '@/services/api'
import {getFilenameFromHeaders, triggerFileDownload} from "@/utils/download.ts";
import RestResponse from '@/models/api/RestResponseModel'
import { type IPageResponse } from '@/models/api/PageResponseModel'
import {
  PaginationRequestParameter,
  RequestModel,
  SortFieldParameter,
} from '@/models/api/RequestModel'
import { HistoOffre } from '@/models/HistoOffreModel'
import type { DataResponse, PagedDataResponse } from '@/models/api/DataResponseModel'

const restApiWithPage = api<IPageResponse<HistoOffre>>()
const restApi = api<HistoOffre>()
const BASE_URL = '/histooffres'

/** Helper to wrap response data in HistoOffre instances */
function wrapHistoOffre(response: DataResponse<HistoOffre> | PagedDataResponse<HistoOffre>) {
  if (Array.isArray(response.data)) {
    response.data = response.data.map((r) => new HistoOffre(r))
  } else if (response.data && typeof response.data === 'object') {
    response.data = new HistoOffre(response.data)
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
  return wrapHistoOffre(RestResponse.handlePagedResponse<HistoOffre>(response)) as PagedDataResponse<HistoOffre>
}

/** POST search with pagination */
export async function search(
  data: Partial<HistoOffre>,
  pagination?: PaginationRequestParameter,
  sortFields?: SortFieldParameter[],
) {
  const request = new RequestModel(BASE_URL + '/search', pagination, sortFields)
  const response = await restApiWithPage.POST(request.buildRequestUrl(), data)
  return wrapHistoOffre(RestResponse.handlePagedResponse<HistoOffre>(response)) as PagedDataResponse<HistoOffre>
}

/** GET by ID */
export async function getById(id: number | string | undefined) {
  if (!id) throw new Error('getById: id is required')
  const response = await restApi.GET(BASE_URL + '/' + id)
  return wrapHistoOffre(RestResponse.handleDataResponse<HistoOffre>(response)) as DataResponse<HistoOffre>
}

export async function create(data: Partial<HistoOffre>){
  const response = await restApi.POST(BASE_URL, data)
  return wrapHistoOffre(RestResponse.handleDataResponse<HistoOffre>(response)) as DataResponse<HistoOffre>
}

/** PUT update */
export async function update(id: number | string | undefined, data: Partial<HistoOffre>) {
  if (!id) throw new Error("update: id is required")
  const response = await restApi.PUT(BASE_URL + '/' + id, data)
  return wrapHistoOffre(RestResponse.handleDataResponse<HistoOffre>(response)) as DataResponse<HistoOffre>
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
  histoOffres: Partial<HistoOffre[]>,
): Promise<{ success: boolean; error: string | null }> {
     const url = import.meta.env.VITE_APP_API_URL+BASE_URL+'/export/csv'

     try {
     const response = await fetch(url, {
                      method: 'POST',
                      headers: {
                        'Content-Type': 'application/json',
                      },
                      body: JSON.stringify(histoOffres),
   })
     if (!response.ok) {
     const errorText = await response.text()
     console.error('CSV Export Failed - Server Error:', response.status, errorText)
                      throw new Error(`CSV export failed: . Status: .`)
   }
      const blob = await response.blob()
      const contentDisposition = response.headers.get('content-disposition');
      triggerFileDownload(blob,getFilenameFromHeaders(contentDisposition,"HistoOffre.csv"));
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
  export async function getAllByOffreId(
    id: number | string | undefined,
    pagination?: PaginationRequestParameter,
    sortFields?: SortFieldParameter[],
  ) {
    if (!id) throw new Error('getAllByOffreId: id of offre is required')
    const request = new RequestModel('/offres/' + id + BASE_URL, pagination, sortFields)
    const response = await restApiWithPage.GET(request.buildRequestUrl())
    return wrapHistoOffre(
      RestResponse.handlePagedResponse<HistoOffre>(response),
    ) as PagedDataResponse<HistoOffre>
  }

