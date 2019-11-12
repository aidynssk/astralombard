package kz.astralombard.home.menu.address.presentation

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.astralombard.base.CoroutineViewModel
import kz.astralombard.base.Response
import kz.astralombard.home.menu.address.data.AddressRepository
import kz.astralombard.home.menu.address.model.City
import kz.astralombard.home.menu.myloans.model.Loan

/**
 * Created by wokrey@gmail.com on 7/16/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class AddressViewModel(
    private val repository: AddressRepository
) : CoroutineViewModel() {

    private val citiesLD = MutableLiveData<List<City>>()

    val openLoans: List<Loan> = arrayListOf(
        Loan(),
        Loan(),
        Loan(),
        Loan(),
        Loan()
    )

    fun loadCities() = launch{
        progressBarStatusLD.value = true
        when(val response = repository.getCities()){
            is Response.Success -> {
                citiesLD.value = response.result.data
            }
            is Response.Error -> {
                errorLD.value = response.error
            }
        }
    }

    fun getCitiesLD() = citiesLD
}