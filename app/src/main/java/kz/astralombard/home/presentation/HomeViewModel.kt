package kz.astralombard.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.astralombard.base.CoroutineViewModel
import kz.astralombard.base.Response
import kz.astralombard.home.data.HomeRepository
import kz.astralombard.home.menu.login.data.SmsRequestModel
import kz.astralombard.home.model.LoginRequestModel

/**
 * Created by wokrey@gmail.com on 02.06.2019
 */
class HomeViewModel(
    private val repository: HomeRepository
): CoroutineViewModel() {
    private val smsLD = MutableLiveData<String>()
    private val userLoggedLD = MutableLiveData<Boolean>()

    fun onLoginButtonClicked(iin: String, phone: String) = launch(uiContext) {
        progressBarStatusLD.value = true

        val response = withContext(bg) {

            return@withContext repository.login(
                LoginRequestModel(
                    iin = iin,
                    phone_number = phone,
                    device_uuid = "4545325"
                )
            )
        }

        when (response) {
            is Response.Success -> {
                smsLD.value = response.result.message
            }
            is Response.Error -> {
                errorLD.value = response.error
            }
        }
        userLoggedLD.value?.let {
            userLoggedLD.value = it.not()
        }?:let{
            userLoggedLD.value = true
        }
        progressBarStatusLD.value = false
    }


    fun validateSms(code: String, iin: String, phone: String) = launch(uiContext) {
        progressBarStatusLD.value = true

        val response = withContext(Dispatchers.IO) {

            return@withContext repository.validate(
                SmsRequestModel(
                    iin = iin,
                    phone_number = phone,
                    code = code
                )
            )
        }
        when (response) {
            is Response.Success -> {

            }
            is Response.Error -> {
                errorLD.value = response.error
            }
        }
        userLoggedLD.value?.let {
            userLoggedLD.value = it.not()
        }?:let{
            userLoggedLD.value = true
        }
        progressBarStatusLD.value = false
    }

    //getters
    fun getSmsLD(): LiveData<String> = smsLD


    fun getUserLoggedLD(): LiveData<Boolean> = userLoggedLD

}