package kz.astralombard.home.menu.address.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import kz.astralombard.base.CoroutineViewModel
import kz.astralombard.base.PermisionStatus
import kz.astralombard.base.Response
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

    private val _citiesLD = MutableLiveData<List<City>>()
    val citiesLD: LiveData<List<City>> = _citiesLD

    private val _addresses = MutableLiveData<List<Point>>()
    val addresses: LiveData<List<Point>> = _addresses

    fun loadCities() = launch{
        if (_citiesLD.value != null){
            return@launch
        }
        _progressBarStatusLD.value = true
        when(val response = repository.getCities()){
            is Response.Success ->
                _citiesLD.value = response.result
            is Response.Error ->
                errorLD.value = response.error
        }
        _progressBarStatusLD.value = false
    }

    fun getAddresses(
        lat: String,
        long: String,
        id: String
    ) = launch {
        when (val response = repository.getAddresses(lat, long, id)) {
            is Response.Success ->
                _addresses.value = response.result
            is Response.Error ->
                errorLD.value = response.error
        }
    }

    fun saveLocationPermissionStatus(status: PermisionStatus)
            = repository.saveLocationPermissionStatus(status)
}