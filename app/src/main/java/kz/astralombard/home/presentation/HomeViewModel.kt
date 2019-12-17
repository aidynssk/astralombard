package kz.astralombard.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import kz.astralombard.base.Constants
import kz.astralombard.base.CoroutineViewModel
import kz.astralombard.base.DataHolder
import kz.astralombard.base.data.Response
import kz.astralombard.home.data.HomeRepository
import kz.astralombard.home.menu.myloans.model.MyLoanRequest
import kz.astralombard.home.menu.profile.model.Profile
import kz.astralombard.home.model.GetCodeRequestModel
import kz.astralombard.home.model.GetCodeResponse
import kz.astralombard.home.model.ValidateCodeRequest

/**
 * Created by wokrey@gmail.com on 02.06.2019
 */
class HomeViewModel(
    private val repository: HomeRepository
) : CoroutineViewModel() {
    private val smsLD = MutableLiveData<GetCodeResponse>()
    private val userLoggedLD = MutableLiveData<Boolean>()

    private val _userHasPinLD = MutableLiveData<Boolean?>()
    val userHasPinLD: LiveData<Boolean?> = _userHasPinLD

    private val _profileLD = MutableLiveData<Profile>()
    val profileLD: LiveData<Profile> = _profileLD

    init {
        DataHolder.token = repository.getToken()
        checkPin(!DataHolder.token.isNullOrBlank())
        userLoggedLD.value = !DataHolder.token.isNullOrBlank()
    }

    fun onLoginButtonClicked(iin: String, phone: String) = launch {
        _progressBarStatusLD.value = true

        val response = repository.getCode(
            GetCodeRequestModel(
                username = phone,
                password = iin,
                hash = "454532523"
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
            ValidateCodeRequest(
                 password = iin,
                 username = phone,
                 code = code,
                hash = "454532523"
             )
        )
        _progressBarStatusLD.value = false
        when (response) {
            is Response.Success -> {
                checkPin(true)
                userLoggedLD.value = true
                repository.saveToken(response.result.token)
                repository.saveUsernameAndIIN(username = phone, iin = iin)
                DataHolder.token = response.result.token
            }
            is Response.Error -> {
                _errorLD.value = response.error
            }
        }
    }

    fun loadProfileData() {
        _progressBarStatusLD.value = true
        launch {
            val myLoanRequest = MyLoanRequest(
                username = repository.getUsername(),
                password = repository.getIIN()
            )
            val response = repository.getProfileData(myLoanRequest)
            when (response) {
                is Response.Success -> {
                    _profileLD.value = response.result
                }
                is Response.Error -> {
                    _errorLD.value = response.error
                }
            }
            _progressBarStatusLD.value = false
        }
    }

        fun logoutConfirmed() {
            repository.saveToken(Constants.DEFAULT_STRING)
            repository.clearPin()
            userLoggedLD.value = false
        }

    fun onLanguageChosen(languageType: String) = repository.saveLang(languageType)

    fun getUsername() = repository.getUsername()

    fun getSavedLang() = repository.getLang()

    private fun checkPin(isLogged: Boolean){
        if (!isLogged) return

        _userHasPinLD.value = !repository.getPin().isNullOrBlank()
    }

        //getters
        fun getSmsLD(): LiveData<GetCodeResponse> = smsLD

        fun getUserLoggedLD(): LiveData<Boolean> = userLoggedLD
    }