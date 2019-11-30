package kz.astralombard.home.menu.myloans.data

import kz.astralombard.base.data.ApiService
import kz.astralombard.base.data.BaseRepository

/**
 * Created by wokrey@gmail.com on 7/13/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class DefaultLoansRepository(
    private val api: ApiService
) : BaseRepository(), LoansRepository {

}