package kz.astralombard.base

import kotlinx.coroutines.Deferred
import kz.astralombard.home.menu.address.model.CitiesResponse
import kz.astralombard.home.menu.login.data.SmsRequestModel
import kz.astralombard.home.menu.login.data.SmsResponse
import kz.astralombard.home.model.LoginRequestModel
import kz.astralombard.home.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by wokrey@gmail.com on 02.06.2019
 */
interface ApiService {
    /**
     * add single slash for POST method endpoint*/
//        @Headers("Content-Type:application/x-www-form-urlencoded")
//    @Headers("Accept: application/json")
    @POST("auth/login/")
    suspend fun login(@Body loginBody: LoginRequestModel): LoginResponse

    @Headers("Content-Type:application/json")
    @POST("auth/validate-code/")
    suspend fun validate(@Body validateBody: SmsRequestModel): SmsResponse

    @Headers("Content-Type:application/json")
    @GET("cities")
    suspend fun getCities(): CitiesResponse
}