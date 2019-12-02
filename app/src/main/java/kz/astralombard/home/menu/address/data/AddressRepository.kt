package kz.astralombard.home.menu.address.data

import kz.astralombard.base.PermisionStatus
import kz.astralombard.base.data.Response
import kz.astralombard.home.menu.address.model.City
import kz.astralombard.home.menu.address.model.Point

/**
 * Created by wokrey@gmail.com on 7/16/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
interface AddressRepository {

    suspend fun getCities(): Response<List<City>>

    suspend fun getAddresses(
        lat: String,
        long: String,
        id: String
    ): Response<List<Point>>

    fun saveLocationPermissionStatus(status: PermisionStatus)

    fun saveCity(city: City)

    fun getSavedCity(): City?
}