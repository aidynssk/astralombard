package kz.astralombard.home.model

data class GetCodeResponse(
    val username: String,
    val password: String,
    val code: String
)

data class ValidateCodeRequest(
    val username: String,
    val password: String,
    val code: String,
    val hash: String
)