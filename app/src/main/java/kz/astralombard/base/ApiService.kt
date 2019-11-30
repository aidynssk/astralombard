package kz.astralombard.base

import kz.astralombard.home.menu.about.writeus.data.FeedbackRequest
import kz.astralombard.home.menu.about.writeus.data.FeedbackResponse
import kz.astralombard.home.menu.address.model.City
import kz.astralombard.home.menu.address.model.Point
import kz.astralombard.home.menu.login.data.SmsValidateResponse
import kz.astralombard.home.model.GetCodeRequestModel
import kz.astralombard.home.model.GetCodeResponse
import retrofit2.http.*

/**
 * Created by wokrey@gmail.com on 02.06.2019
 */
interface ApiService {
    /**
     * add single slash for POST method endpoint*/
//        @Headers("Content-Type:application/x-www-form-urlencoded")
//    @Headers("Accept: application/json")
    @POST("auth/get-otp/")
    suspend fun getCode(@Body loginBody: GetCodeRequestModel): GetCodeResponse

    @Headers("Content-Type:application/json")
    @POST("auth/login/")
    suspend fun validate(@Body validateBody: GetCodeResponse): SmsValidateResponse

    @Headers("Content-Type:application/json")
    @GET("cities")
    suspend fun getCities(): List<City>

    @Headers("Content-Type:application/json")
    @GET("cities/{id}")
    suspend fun getAddresses(
        @Path("id") id: String,
        @Query("latitude") lat: String,
        @Query("latitude") long: String
    ): List<Point>

    @Headers("Content-Type:application/json")
    @POST("feedback/")
    suspend fun leaveFeedback(request: FeedbackRequest): FeedbackResponse
}