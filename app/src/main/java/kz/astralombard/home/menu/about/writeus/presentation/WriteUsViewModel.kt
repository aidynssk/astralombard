package kz.astralombard.home.menu.about.writeus.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import kz.astralombard.base.Constants
import kz.astralombard.base.CoroutineViewModel
import kz.astralombard.base.data.AstraException
import kz.astralombard.base.data.Response
import kz.astralombard.home.menu.about.writeus.data.FeedbackRequest
import kz.astralombard.home.menu.about.writeus.data.FeedbackResponse
import kz.astralombard.home.menu.about.writeus.data.IWriteUsRepository

/**
 * Created by wokrey@gmail.com on 30.11.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class WriteUsViewModel(
    private val repository: IWriteUsRepository
) : CoroutineViewModel() {

    private val _feedbackLD = MutableLiveData<FeedbackResponse>()
    val feedbackLD: LiveData<FeedbackResponse> = _feedbackLD

    var subject: String = Constants.DEFAULT_STRING
    var username: String = Constants.DEFAULT_STRING
    var text: String = Constants.DEFAULT_STRING

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
                is Response.Success ->
                    _feedbackLD.value = response.result
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