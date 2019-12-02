package kz.astralombard.home.data

import android.content.SharedPreferences
import kz.astralombard.base.Constants
import kz.astralombard.base.data.ApiService
import kz.astralombard.base.data.BaseRepository
import kz.astralombard.base.data.Response
import kz.astralombard.base.SharedPrefKeys
import kz.astralombard.home.menu.login.data.SmsValidateResponse
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

    override fun saveToken(token: String) = pref.edit()
        .putString(SharedPrefKeys.USER_TOKEN, token)
        .apply()

    override fun getToken() = pref.getString(SharedPrefKeys.USER_TOKEN, Constants.DEFAULT_STRING)!!
}