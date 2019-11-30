package kz.astralombard.home.model

data class GetCodeRequestModel(
    val username: String,
    val device_uuid: String,
    val password: String
)