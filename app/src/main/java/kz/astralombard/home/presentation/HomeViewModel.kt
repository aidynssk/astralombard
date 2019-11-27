package kz.astralombard.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import kz.astralombard.base.CoroutineViewModel
import kz.astralombard.base.Response
import kz.astralombard.home.data.HomeRepository
import kz.astralombard.home.menu.login.data.SmsRequestModel
import kz.astralombard.home.model.LoginRequestModel
import java.lang.Exception

/**
 * Created by wokrey@gmail.com on 02.06.2019
 */
class HomeViewModel(
    private val repository: HomeRepository
) : CoroutineViewModel() {
    private val smsLD = MutableLiveData<String>()
    private val userLoggedLD = MutableLiveData<Boolean>()

    fun onLoginButtonClicked(iin: String, phone: String) = launch {
        progressBarStatusLD.value = true

        val response = repository.login(
            LoginRequestModel(
                iin = iin,
                phone_number = phone,
                device_uuid = "4545325"
            )
        )

        when (response) {
            is Response.Success -> {
                smsLD.value = response.result.message
            }
            is Response.Error -> {
                errorLD.value = response.error
            }
        }
        progressBarStatusLD.value = false
    }


    fun validateSms(code: String, iin: String, phone: String) = launch {
        progressBarStatusLD.value = true

        val response = repository.validate(
            SmsRequestModel(
                iin = iin,
                phone_number = phone,
                code = code
            )
        )
        when (response) {
            is Response.Success -> run{
                if (response.result.message.contains("invalid")){
                    errorLD.value = Exception()
                    return@run
                }
                userLoggedLD.value = true
            }
            is Response.Error -> {
                errorLD.value = response.error
            }
        }
        progressBarStatusLD.value = false
    }

    fun logoutConfirmed(){
        userLoggedLD.value = false
    }

    //getters
    fun getSmsLD(): LiveData<String> = smsLD


    fun getUserLoggedLD(): LiveData<Boolean> = userLoggedLD

}