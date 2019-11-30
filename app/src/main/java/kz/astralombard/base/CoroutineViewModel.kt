package kz.astralombard.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

abstract class CoroutineViewModel(
    protected val uiContext: CoroutineContext = Dispatchers.Main,
    protected val bg: CoroutineContext = Dispatchers.IO
) : ViewModel(), CoroutineScope {

    protected val _progressBarStatusLD = MutableLiveData<Boolean>()
    val progressBarStatusLD: LiveData<Boolean> = _progressBarStatusLD

    protected val _errorLD = MutableLiveData<Exception>()
    val errorLD: LiveData<Exception> = _errorLD
    private val job = Job()

    override val coroutineContext: CoroutineContext by lazy {
        uiContext + job
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }
}