package kz.astralombard.home.model

import kz.astralombard.base.DataHolder

data class GetCodeResponse(
    val username: String,
    val password: String,
    val code: String
)

data class ValidateCodeRequest(
    val username: String,
    val password: String,
    val code: String,
    val hash: String,
    val lang: String = DataHolder.currentLang
)