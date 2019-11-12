package kz.astralombard.home.data

import kz.astralombard.base.ApiService
import kz.astralombard.base.Response
import kz.astralombard.home.menu.login.data.SmsRequestModel
import kz.astralombard.home.menu.login.data.SmsResponse
import kz.astralombard.home.model.LoginRequestModel
import kz.astralombard.home.model.LoginResponse

/**
 * Created by wokrey@gmail.com on 02.06.2019
 */
class HomeRepositoryImpl(
    private val api: ApiService
): HomeRepository {
    override suspend fun login(loginRequest: LoginRequestModel): Response<LoginResponse> = try {
        val response = api.login(loginRequest).await()
        Response.Success(response)
    } catch (error: Exception) {
        Response.Error(error)
    }

    override suspend fun validate(validateRequest: SmsRequestModel): Response<SmsResponse> = try {
        val response = api.validate(validateRequest).await()
        Response.Success(response)
    } catch (error: Exception) {
        Response.Error(error)
    }
}