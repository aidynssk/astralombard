package kz.astralombard.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import kz.astralombard.base.CoroutineViewModel
import kz.astralombard.base.DataHolder
import kz.astralombard.base.Response
import kz.astralombard.home.data.HomeRepository
import kz.astralombard.home.model.GetCodeRequestModel
import kz.astralombard.home.model.GetCodeResponse

/**
 * Created by wokrey@gmail.com on 02.06.2019
 */
class HomeViewModel(
    private val repository: HomeRepository
) : CoroutineViewModel() {
    private val smsLD = MutableLiveData<GetCodeResponse>()
    private val userLoggedLD = MutableLiveData<Boolean>()

    fun onLoginButtonClicked(iin: String, phone: String) = launch {
        _progressBarStatusLD.value = true

        val response = repository.getCode(
            GetCodeRequestModel(
                username = phone,
                password = iin,
                device_uuid = "4545325"
            )
        )

        when (response) {
            is Response.Success -> {
                smsLD.value = response.result
            }
            is Response.Error -> {
                _errorLD.value = response.error
            }
        }
        _progressBarStatusLD.value = false
    }


    fun validateSms(code: String, iin: String, phone: String) = launch {
        _progressBarStatusLD.value = true

        val response = repository.validate(
            smsLD.value!!
           /* SmsRequestModel(
                iin = iin,
                phone_number = phone,
                code = code
            )*/
        )
        _progressBarStatusLD.value = false
        when (response) {
            is Response.Success -> {
                userLoggedLD.value = true
                repository.saveToken(response.result.token)
                DataHolder.token = response.result.token
            }
            is Response.Error -> {
                _errorLD.value = response.error
            }
        }
    }

    fun logoutConfirmed(){
        userLoggedLD.value = false
    }

    //getters
    fun getSmsLD(): LiveData<GetCodeResponse> = smsLD


    fun getUserLoggedLD(): LiveData<Boolean> = userLoggedLD

}