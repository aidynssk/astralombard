package kz.astralombard.home.data

import kz.astralombard.base.Response
import kz.astralombard.home.menu.login.data.SmsRequestModel
import kz.astralombard.home.menu.login.data.SmsResponse
import kz.astralombard.home.model.LoginRequestModel
import kz.astralombard.home.model.LoginResponse

/**
 * Created by wokrey@gmail.com on 02.06.2019
 */
interface HomeRepository {

    suspend fun login(loginRequest: LoginRequestModel): Response<LoginResponse>

    suspend fun validate(validateRequest: SmsRequestModel): Response<SmsResponse>

}