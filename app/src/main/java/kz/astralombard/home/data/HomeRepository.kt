package kz.astralombard.home.data

import kz.astralombard.base.data.Response
import kz.astralombard.home.menu.login.data.SmsValidateResponse
import kz.astralombard.home.menu.myloans.model.MyLoanRequest
import kz.astralombard.home.menu.profile.model.Profile
import kz.astralombard.home.model.GetCodeRequestModel
import kz.astralombard.home.model.GetCodeResponse
import kz.astralombard.home.model.ValidateCodeRequest

/**
 * Created by wokrey@gmail.com on 02.06.2019
 */
interface HomeRepository {

    suspend fun getCode(getCodeRequest: GetCodeRequestModel): Response<GetCodeResponse>

    suspend fun validate(validateRequest: ValidateCodeRequest): Response<SmsValidateResponse>

    suspend fun getProfileData(myLoanRequest: MyLoanRequest): Response<Profile>

    fun saveToken(token: String)

    fun saveUsernameAndIIN(username: String, iin: String)

    fun getToken(): String

    suspend fun getUsername(): String
    suspend fun getIIN(): String

}