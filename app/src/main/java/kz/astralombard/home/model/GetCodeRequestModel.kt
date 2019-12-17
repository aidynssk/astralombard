package kz.astralombard.home.model

import kz.astralombard.base.DataHolder

data class GetCodeRequestModel(
    val username: String,
    val hash: String,
    val password: String,
    val lang: String = DataHolder.currentLang
)