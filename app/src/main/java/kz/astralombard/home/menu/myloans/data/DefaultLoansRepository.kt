package kz.astralombard.home.menu.myloans.data

import kz.astralombard.base.data.ApiService
import kz.astralombard.base.data.BaseRepository
import kz.astralombard.base.data.Response
import kz.astralombard.home.menu.myloans.model.Loan

/**
 * Created by wokrey@gmail.com on 7/13/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

interface LoansRepository {
    suspend fun getMyLoans(): Response<List<MyLoan>>
}

class DefaultLoansRepository(
    private val api: ApiService
) : BaseRepository(), LoansRepository {

    override suspend fun getMyLoans(): Response<List<MyLoan>>
            = makeApiRequest { api.getLoans() }
}