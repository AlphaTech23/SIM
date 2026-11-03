import api from '@/services/api'
import {getFilenameFromHeaders, triggerFileDownload} from "@/utils/download.ts";
import RestResponse from '@/models/api/RestResponseModel'
import { type IPageResponse } from '@/models/api/PageResponseModel'
import {
  PaginationRequestParameter,
  RequestModel,
  SortFieldParameter,
} from '@/models/api/RequestModel'
import { VRendezVousAnnule } from '@/models/VRendezVousAnnuleModel'
import type { DataResponse, PagedDataResponse } from '@/models/api/DataResponseModel'

const restApiWithPage = api<IPageResponse<VRendezVousAnnule>>()
const restApi = api<VRendezVousAnnule>()
const BASE_URL = '/vrendezvousannules'

/** Helper to wrap response data in VRendezVousAnnule instances */
function wrapVRendezVousAnnule(response: DataResponse<VRendezVousAnnule> | PagedDataResponse<VRendezVousAnnule>) {
  if (Array.isArray(response.data)) {
    response.data = response.data.map((r) => new VRendezVousAnnule(r))
  } else if (response.data && typeof response.data === 'object') {
    response.data = new VRendezVousAnnule(response.data)
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
  return wrapVRendezVousAnnule(RestResponse.handlePagedResponse<VRendezVousAnnule>(response)) as PagedDataResponse<VRendezVousAnnule>
}

/** POST search with pagination */
export async function search(
  data: Partial<VRendezVousAnnule>,
  pagination?: PaginationRequestParameter,
  sortFields?: SortFieldParameter[],
) {
  const request = new RequestModel(BASE_URL + '/search', pagination, sortFields)
  const response = await restApiWithPage.POST(request.buildRequestUrl(), data)
  return wrapVRendezVousAnnule(RestResponse.handlePagedResponse<VRendezVousAnnule>(response)) as PagedDataResponse<VRendezVousAnnule>
}

/** GET by ID */
export async function getById(id: number | string | undefined) {
  if (!id) throw new Error('getById: id is required')
  const response = await restApi.GET(BASE_URL + '/' + id)
  return wrapVRendezVousAnnule(RestResponse.handleDataResponse<VRendezVousAnnule>(response)) as DataResponse<VRendezVousAnnule>
}

export async function create(data: Partial<VRendezVousAnnule>){
  const response = await restApi.POST(BASE_URL, data)
  return wrapVRendezVousAnnule(RestResponse.handleDataResponse<VRendezVousAnnule>(response)) as DataResponse<VRendezVousAnnule>
}




export async function exportCsv(
  vRendezVousAnnules: Partial<VRendezVousAnnule[]>,
): Promise<{ success: boolean; error: string | null }> {
     const url = import.meta.env.VITE_APP_API_URL+BASE_URL+'/export/csv'

     try {
     const response = await fetch(url, {
                      method: 'POST',
                      headers: {
                        'Content-Type': 'application/json',
                      },
                      body: JSON.stringify(vRendezVousAnnules),
   })
     if (!response.ok) {
     const errorText = await response.text()
     console.error('CSV Export Failed - Server Error:', response.status, errorText)
                      throw new Error(`CSV export failed: . Status: .`)
   }
      const blob = await response.blob()
      const contentDisposition = response.headers.get('content-disposition');
      triggerFileDownload(blob,getFilenameFromHeaders(contentDisposition,"VRendezVousAnnule.csv"));
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

