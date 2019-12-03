package kz.astralombard.home.data

import android.content.SharedPreferences
import kz.astralombard.base.Constants
import kz.astralombard.base.data.ApiService
import kz.astralombard.base.data.BaseRepository
import kz.astralombard.base.data.Response
import kz.astralombard.base.SharedPrefKeys
import kz.astralombard.home.menu.login.data.SmsValidateResponse
import kz.astralombard.home.menu.myloans.model.MyLoanRequest
import kz.astralombard.home.menu.profile.model.Profile
import kz.astralombard.home.model.GetCodeRequestModel
import kz.astralombard.home.model.GetCodeResponse

/**
 * Created by wokrey@gmail.com on 02.06.2019
 */
class HomeRepositoryImpl(
    private val api: ApiService,
    private val pref: SharedPreferences
) : BaseRepository(), HomeRepository {
    override suspend fun getCode(getCodeRequest: GetCodeRequestModel): Response<GetCodeResponse> =
        makeApiRequest { api.getCode(getCodeRequest) }

    override suspend fun validate(validateRequest: GetCodeResponse): Response<SmsValidateResponse> =
        makeApiRequest { api.validate(validateRequest) }

    override suspend fun getProfileData(myLoanRequest: MyLoanRequest): Response<Profile>  =
        makeApiRequest { api.getProfile(myLoanRequest = myLoanRequest) }

    override fun saveToken(token: String) = pref.edit()
        .putString(SharedPrefKeys.USER_TOKEN, token)
        .apply()

    override fun saveUsernameAndIIN(username: String, iin: String) = pref.edit()
        .putString(SharedPrefKeys.USER_USERNAME, username)
        .putString(SharedPrefKeys.USER_IIN, iin)
        .apply()

    override fun getToken() = pref.getString(SharedPrefKeys.USER_TOKEN, Constants.DEFAULT_STRING)!!

    override suspend fun getIIN() = pref.getString(SharedPrefKeys.USER_IIN, Constants.DEFAULT_STRING)!!

    override suspend fun getUsername() = pref.getString(SharedPrefKeys.USER_USERNAME, Constants.DEFAULT_STRING)!!
}