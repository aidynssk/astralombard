package kz.astralombard.home.menu.address.data

import android.content.SharedPreferences
import com.google.gson.Gson
import kz.astralombard.base.*
import kz.astralombard.base.data.ApiService
import kz.astralombard.base.data.BaseRepository
import kz.astralombard.base.data.Response
import kz.astralombard.home.menu.address.model.City
import kz.astralombard.home.menu.address.model.Point

/**
 * Created by wokrey@gmail.com on 7/16/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class DefaultAddressRepository(
    val api: ApiService,
    val pref: SharedPreferences
) : BaseRepository(), AddressRepository {

    override suspend fun getCities(): Response<List<City>> = makeApiRequest { api.getCities() }

    override suspend fun getAddresses(
        lat: String,
        long: String,
        id: String
    ): Response<List<Point>> =
        makeApiRequest {
            api.getAddresses(
                id = id,
                lat = lat,
                long = long
            )
        }

    override fun saveLocationPermissionStatus(status: PermisionStatus) = pref.edit()
        .putInt(SharedPrefKeys.LOCATION_PERMISSION, status.value)
        .apply()

    override fun saveCity(city: City) {
        val cityJson = Gson().toJson(city)
        city.chosen = true
        pref.edit()
            .putString(SharedPrefKeys.SAVED_CITY, cityJson)
            .apply()
    }

    override fun getSavedCity() =
        pref.getString(SharedPrefKeys.SAVED_CITY, null)?.let {
            Gson().fromJson(it, City::class.java)
        }
}