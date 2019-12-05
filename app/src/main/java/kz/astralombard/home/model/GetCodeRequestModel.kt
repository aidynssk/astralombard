package kz.astralombard.home.model

data class GetCodeRequestModel(
    val username: String,
    val hash: String,
    val password: String
)