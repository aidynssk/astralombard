package kz.astralombard.base

import kotlinx.coroutines.Deferred
import kz.astralombard.home.menu.address.model.CitiesResponse
import kz.astralombard.home.menu.login.data.SmsRequestModel
import kz.astralombard.home.menu.login.data.SmsResponse
import kz.astralombard.home.model.LoginRequestModel
import kz.astralombard.home.model.LoginResponse
import retrofit2.http.*

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

    @Headers("Content-Type:application/json")
    @GET("cities/1?latitude=43.212238&longitude=76.8982445")
    suspend fun getAddresses(
        /*@Path("id") id: String,
        @Field("latitude") lat: String,
        @Field("latitude") long: String*/
    ): CitiesResponse
}