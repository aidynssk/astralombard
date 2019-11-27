package kz.astralombard.home.menu.address.data

import kz.astralombard.base.Response
import kz.astralombard.home.menu.address.model.CitiesResponse

/**
 * Created by wokrey@gmail.com on 7/16/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
interface AddressRepository {

    suspend fun getCities(): Response<CitiesResponse>

//    suspend fun getAddresses(): Response<Any>
}