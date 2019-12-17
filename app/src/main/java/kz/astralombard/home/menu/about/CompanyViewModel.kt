package kz.astralombard.home.menu.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import kz.astralombard.base.Constants
import kz.astralombard.base.CoroutineViewModel
import kz.astralombard.base.data.AstraException
import kz.astralombard.base.data.Response
import kz.astralombard.home.menu.about.data.*

/**
 * Created by wokrey@gmail.com on 30.11.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class CompanyViewModel(
    private val repository: ICompanyRepository
) : CoroutineViewModel() {

    private val _feedbackLD = MutableLiveData<Boolean>()
    val feedbackLD: LiveData<Boolean> = _feedbackLD
    var subject: String = Constants.DEFAULT_STRING
    var username: String = Constants.DEFAULT_STRING
    var text: String = Constants.DEFAULT_STRING

    private val _aboutCompanyLD = MutableLiveData<String>()
    val aboutCompanyLD: LiveData<String> = _aboutCompanyLD

    private val _newsLD = MutableLiveData<List<News>>()
    val newsLD: LiveData<List<News>> = _newsLD

    fun getAboutCompany(language: String){
        _progressBarStatusLD.value = true
        launch {
            when(val response = repository.getAboutCompanyText(language)){
                is Response.Success ->
                    _aboutCompanyLD.value = response.result.text ?: "kdhkadfvfk"
                is Response.Error ->
                    _errorLD.value = response.error
            }
            _progressBarStatusLD.value = false
        }
    }

    fun getNews(){
        _progressBarStatusLD.value = true
        launch {
            when(val response = repository.getNews()){
                is Response.Success ->
                    _newsLD.value = response.result
                is Response.Error ->
                    _errorLD.value = response.error
            }
            _progressBarStatusLD.value = false
        }
    }
    fun leaveFeedback(
       /* subject: String,
        username: String,
        text: String*/
    ) {
        if (!validateRequest()){
            return
        }
        _progressBarStatusLD.value = true
        launch {
            val request = FeedbackRequest(
                subject = subject,
                username = username,
                text = text
            )
            when (val response = repository.leaveFeedback(request)) {
                is Response.Success -> {
                    _feedbackLD.value = true
                }
                is Response.Error ->
                    _errorLD.value = response.error
            }
            _progressBarStatusLD.value = false
        }
    }

    private fun validateRequest(): Boolean{
        val errorMessage: String = when {
            subject.isBlank() ->
                "Не указана тема сообщения"
            username.isBlank() ->
                "Не указан телефон"
            text.isBlank() ->
                "Заполните текст сообщения"
            else ->
                return true
        }
        _errorLD.value = AstraException(errorMessage)
        return false
    }
}