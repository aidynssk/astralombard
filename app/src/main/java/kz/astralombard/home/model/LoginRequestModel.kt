package kz.astralombard.home.model

data class LoginRequestModel
    (
    val phone_number: String,
    val device_uuid: String,
    val iin: String
)