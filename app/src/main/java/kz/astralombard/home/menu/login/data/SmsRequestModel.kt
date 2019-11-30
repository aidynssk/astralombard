package kz.astralombard.home.menu.login.data

import java.util.*

/**
 * Created by wokrey@gmail.com on 7/30/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
data class SmsRequestModel(
    val phone_number: String,
    val iin: String,
    val code: String
)

data class SmsValidateResponse(
    val expiry: Date,
    val token: String
)