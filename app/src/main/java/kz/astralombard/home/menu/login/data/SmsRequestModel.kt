package kz.astralombard.home.menu.login.data

/**
 * Created by wokrey@gmail.com on 7/30/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
data class SmsRequestModel(
    val phone_number: String,
    val iin: String,
    val code: String
)

data class SmsResponse(
    val message: String,
    val status: String? = null
)