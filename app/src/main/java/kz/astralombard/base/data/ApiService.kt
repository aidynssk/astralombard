package kz.astralombard.base.data

import kz.astralombard.home.menu.about.data.AboutCompanyResponse
import kz.astralombard.home.menu.about.data.FeedbackRequest
import kz.astralombard.home.menu.about.data.FeedbackResponse
import kz.astralombard.home.menu.about.data.News
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
    @POST("auth/get-otp/")
    suspend fun getCode(@Body loginBody: GetCodeRequestModel): GetCodeResponse

    @POST("auth/login/")
    suspend fun validate(@Body validateBody: GetCodeResponse): SmsValidateResponse

    @GET("cities")
    suspend fun getCities(): List<City>

    @GET("cities/{id}")
    suspend fun getAddresses(
        @Path("id") id: String,
        @Query("latitude") lat: String,
        @Query("latitude") long: String
    ): List<Point>

    @POST("feedback/")
    suspend fun leaveFeedback(@Body request: FeedbackRequest): FeedbackResponse

    @GET("news")
    suspend fun getNews(): List<News>

    @GET("about")
    suspend fun getAbout(): AboutCompanyResponse
}