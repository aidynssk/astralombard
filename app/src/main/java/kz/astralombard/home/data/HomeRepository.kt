package kz.astralombard.home.data

import kz.astralombard.base.data.Response
import kz.astralombard.home.menu.login.data.SmsValidateResponse
import kz.astralombard.home.model.GetCodeRequestModel
import kz.astralombard.home.model.GetCodeResponse

/**
 * Created by wokrey@gmail.com on 02.06.2019
 */
interface HomeRepository {

    suspend fun getCode(getCodeRequest: GetCodeRequestModel): Response<GetCodeResponse>

    suspend fun validate(validateRequest: GetCodeResponse): Response<SmsValidateResponse>

    fun saveToken(token: String)

    fun getToken(): String

}