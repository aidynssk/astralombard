package kz.astralombard.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class CoroutineViewModel(
    protected val uiContext: CoroutineContext = Dispatchers.Main,
    protected val bg: CoroutineContext = Dispatchers.IO
) : ViewModel(), CoroutineScope {

    protected val progressBarStatusLD = MutableLiveData<Boolean>()
    protected val errorLD = MutableLiveData<Exception>()
    private val job = Job()

    override val coroutineContext: CoroutineContext by lazy {
        uiContext + job
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    //getters
    fun getErrorLD(): LiveData<Exception> = errorLD

    fun getProgressBarStatusLD(): LiveData<Boolean> = progressBarStatusLD
}