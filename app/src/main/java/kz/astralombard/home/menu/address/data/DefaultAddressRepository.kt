package kz.astralombard.home.menu.address.data

import kz.astralombard.base.ApiService
import kz.astralombard.base.BaseRepository
import kz.astralombard.base.Response
import kz.astralombard.home.menu.address.model.CitiesResponse

/**
 * Created by wokrey@gmail.com on 7/16/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class DefaultAddressRepository(
    val api: ApiService
) : BaseRepository(), AddressRepository {

    override suspend fun getCities(): Response<CitiesResponse>
            = makeApiRequest {api.getCities()}
}