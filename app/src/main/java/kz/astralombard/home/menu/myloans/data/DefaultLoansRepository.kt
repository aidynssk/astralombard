package kz.astralombard.home.menu.myloans.data

import android.content.SharedPreferences
import kz.astralombard.base.Constants
import kz.astralombard.base.SharedPrefKeys
import kz.astralombard.base.data.ApiService
import kz.astralombard.base.data.BaseRepository
import kz.astralombard.base.data.Response
import kz.astralombard.home.menu.myloans.model.Loan
import kz.astralombard.home.menu.myloans.model.MyLoanRequest

/**
 * Created by wokrey@gmail.com on 7/13/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

interface LoansRepository {
    suspend fun getMyLoans(myLoanRequest: MyLoanRequest): Response<List<MyLoan>>
    suspend fun getUsername(): String
    suspend fun getIIN(): String
}

class DefaultLoansRepository(
    private val api: ApiService,
    private val prefs: SharedPreferences
) : BaseRepository(), LoansRepository {

    override suspend fun getMyLoans(myLoanRequest: MyLoanRequest): Response<List<MyLoan>>
            = makeApiRequest { api.getLoans(myLoanRequest = myLoanRequest) }

    override suspend fun getIIN() = prefs.getString(SharedPrefKeys.USER_IIN, Constants.DEFAULT_STRING)!!

    override suspend fun getUsername() = prefs.getString(SharedPrefKeys.USER_USERNAME, Constants.DEFAULT_STRING)!!
}