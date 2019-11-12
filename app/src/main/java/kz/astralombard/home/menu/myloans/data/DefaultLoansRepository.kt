package kz.astralombard.home.menu.myloans.data

import kz.astralombard.base.ApiService
import kz.astralombard.base.BaseRepository
import kz.astralombard.home.menu.myloans.model.Loan

/**
 * Created by wokrey@gmail.com on 7/13/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class DefaultLoansRepository(
    private val api: ApiService
) : BaseRepository(), LoansRepository {

}