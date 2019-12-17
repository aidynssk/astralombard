package kz.astralombard.home.menu.myloans.model

import kz.astralombard.base.DataHolder

/**
 * Created by wokrey@gmail.com on 7/13/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
data class Loan(
    val a: String=""
)

data class MyLoanRequest(
    val username: String,
    val password: String,
    val lang: String = DataHolder.currentLang
)