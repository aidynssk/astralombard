package kz.astralombard.home.menu.address.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import kz.astralombard.base.CoroutineViewModel
import kz.astralombard.base.PermisionStatus
import kz.astralombard.base.data.Response
import kz.astralombard.home.menu.address.data.AddressRepository
import kz.astralombard.home.menu.address.model.City
import kz.astralombard.home.menu.address.model.Point

/**
 * Created by wokrey@gmail.com on 7/16/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class AddressViewModel(
    private val repository: AddressRepository
) : CoroutineViewModel() {

    private val _citiesLD = MutableLiveData<MutableList<City>>()
    val citiesLD: LiveData<MutableList<City>> = _citiesLD

    private val _addresses = MutableLiveData<List<Point>>()
    val addresses: LiveData<List<Point>> = _addresses

    fun loadCities() = launch{
        if (_citiesLD.value != null){
            return@launch
        }
        _progressBarStatusLD.value = true
        when(val response = repository.getCities()){
            is Response.Success ->
                _citiesLD.value = response.result.toMutableList()
            is Response.Error ->
                _errorLD.value = response.error
        }
        _progressBarStatusLD.value = false
    }

    fun getAddresses(
        lat: String,
        long: String,
        id: String,
        showDistance: Boolean
    ) = launch {
        when (val response = repository.getAddresses(lat, long, id)) {
            is Response.Success -> {
                val result = response.result.apply {
                    forEach {
                        it.showDistance = showDistance
                    }
                }
                _addresses.value = if (showDistance){
                    result.sortedBy { it.distance.toInt() }
                } else {
                    result
                }
            }
            is Response.Error ->
                _errorLD.value = response.error
        }
    }

    fun saveLocationPermissionStatus(status: PermisionStatus)
            = repository.saveLocationPermissionStatus(status)

    fun saveCity(position: Int){
        if (_citiesLD.value.isNullOrEmpty()){
            Log.e(javaClass.toString(), "Null or empty cities", KotlinNullPointerException())
            return
        }
        repository.saveCity(_citiesLD.value!![position])
    }

    fun onCitySelected(position: Int, lat: String, long: String, showDistance: Boolean){
        _citiesLD.value?.getOrNull(position)?.let {
            getAddresses(
                lat = lat,
                long = long,
                id = it.id.toString(),
                showDistance = showDistance
            )
            repository.saveCity(it)
        }
    }
    fun getSavedCity() = repository.getSavedCity()
}