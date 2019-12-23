package kz.astralombard.base.data

import kz.astralombard.base.Constants
import kz.astralombard.base.DataHolder
import kz.astralombard.home.menu.about.data.AboutCompanyResponse
import kz.astralombard.home.menu.about.data.FeedbackRequest
import kz.astralombard.home.menu.about.data.News
import kz.astralombard.home.menu.address.model.City
import kz.astralombard.home.menu.address.model.Point
import kz.astralombard.home.menu.login.data.SmsValidateResponse
import kz.astralombard.home.menu.myloans.data.MyLoan
import kz.astralombard.home.menu.myloans.data.ProlongateRequest
import kz.astralombard.home.menu.myloans.data.ProlongateResponse
import kz.astralombard.home.menu.myloans.model.MyLoanRequest
import kz.astralombard.home.menu.profile.model.Profile
import kz.astralombard.home.model.GetCodeRequestModel
import kz.astralombard.home.model.GetCodeResponse
import kz.astralombard.home.model.ValidateCodeRequest
import retrofit2.http.*

/**
 * Created by wokrey@gmail.com on 02.06.2019
 */
interface ApiService {
    /**
     * add single slash for POST method endpoint*/
    @POST("auth/get-otp/")
    suspend fun getCode(
        @Query("lang") lang: String,
        @Body loginBody: GetCodeRequestModel
    ): GetCodeResponse

    @POST("auth/login/")
    suspend fun validate(
        @Query("lang") lang: String,
        @Body validateBody: ValidateCodeRequest
    ): SmsValidateResponse

    @GET("cities")
    suspend fun getCities(): List<City>

    @GET("cities/{id}")
    suspend fun getAddresses(
        @Path("id") id: String,
        @Query("latitude") lat: String,
        @Query("longitude") long: String
    ): List<Point>

    @POST("feedback/")
    suspend fun leaveFeedback(
        @Query("lang") lang: String,
        @Body request: FeedbackRequest
    )

    @GET("news")
    suspend fun getNews(): List<News>

    @GET("about")
    suspend fun getAbout(
        @Query("lang") language: String
    ): AboutCompanyResponse

    @POST("loads/")
    suspend fun getLoans(
        @Header(Constants.AUTH_HEADER) token: String = DataHolder.token!!,
        @Query("lang") lang: String,
        @Body myLoanRequest: MyLoanRequest
    ): List<MyLoan>

    @POST("auth/profile/")
    suspend fun getProfile(
        @Header(Constants.AUTH_HEADER) token: String = DataHolder.token!!,
        @Query("lang") lang: String,
        @Body myLoanRequest: MyLoanRequest
    ): Profile

    @POST("loads/prolongate/")
    suspend fun getProlongate(
        @Header(Constants.AUTH_HEADER) token: String = DataHolder.token!!,
        @Query("lang") lang: String,
        @Body request: ProlongateRequest
    ): ProlongateResponse
}