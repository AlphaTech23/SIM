import api from '@/services/api.ts'
import type { IUser } from '@/models/auth/UserModel.ts'
import { RequestModel } from '@/models/api/RequestModel.ts'
import type { DataResponse } from '@/models/api/DataResponseModel.ts'
import { User } from '@/models/auth/UserModel.ts'
import RestResponse from '@/models/api/RestResponseModel.ts'
import type { RegisterDTO } from '@/models/auth/RegisterDTO.ts'
import type { ChangePasswordDTO } from '@/models/auth/ChangePasswordDTO.ts'
import type { UpdateProfileDTO } from '@/models/auth/UpdateProfileDTO.ts'

const BASE_URL = '/auth'
const userApi = api<IUser>()
const stringApi = api<string>()

function wrapUser(response: DataResponse<User>) {
  response.data = new User(response.data)
  return response
}

export async function currentUser() {
  const request = new RequestModel(BASE_URL + '/current')
  const response = await userApi.GET(request.buildRequestUrl([], false))
  return wrapUser(RestResponse.handleDataResponse(response))
}

export async function login(username: string, password: string) {
  const request = new RequestModel(BASE_URL + '/login')
  const response = await stringApi.POST(request.buildRequestUrl([], false), { username, password })
  return RestResponse.handleDataResponse(response)
}

export async function logout() {
  const request = new RequestModel(BASE_URL + '/logout')
  const response = await stringApi.POST(request.buildRequestUrl([], false))
  return RestResponse.handleDataResponse(response)
}

export async function register(data: RegisterDTO) {
  const request = new RequestModel(BASE_URL + '/register')
  const response = await stringApi.POST(request.buildRequestUrl([], false), data)
  return RestResponse.handleDataResponse(response)
}

export async function changePassword(data: ChangePasswordDTO) {
  const request = new RequestModel(BASE_URL + '/change-password')
  const response = await stringApi.POST(request.buildRequestUrl([], false), data)
  return RestResponse.handleDataResponse(response)
}

export async function updateProfile(data: UpdateProfileDTO): Promise<DataResponse<IUser>> {
  const request = new RequestModel(BASE_URL + '/update')
  const response = await userApi.PATCH(request.buildRequestUrl([], false), data)
  return RestResponse.handleDataResponse(response)
}
