package kz.astralombard.home.menu.about.writeus.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import kz.astralombard.base.CoroutineViewModel
import kz.astralombard.base.Response
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

    fun leaveFeedback(
        subject: String,
        username: String,
        text: String
    ) {
        _progressBarStatusLD.value = true
        launch {
            val request = FeedbackRequest(
                subject = subject,
                username = username,
                text = text
            )
            when (val response = repository.leaveFeedback(request)) {
                is Response.Success -> _feedbackLD.value = response.result
                is Response.Error -> _errorLD.value = response.error
            }
            _progressBarStatusLD.value = false
        }
    }
}