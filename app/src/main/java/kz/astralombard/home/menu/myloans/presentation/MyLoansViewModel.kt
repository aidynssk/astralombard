package kz.astralombard.home.menu.myloans.presentation

import kotlinx.coroutines.launch
import kz.astralombard.base.CoroutineViewModel
import kz.astralombard.base.data.Response
import kz.astralombard.home.menu.myloans.data.LoansRepository
import kz.astralombard.home.menu.myloans.model.Loan

/**
 * Created by wokrey@gmail.com on 7/13/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class MyLoansViewModel(
    private val repository: LoansRepository
): CoroutineViewModel() {

    var openLoans: List<Loan> = arrayListOf(
        Loan(),
        Loan(),
        Loan()
    )

    fun getMyLoans(){
        _progressBarStatusLD.value = true
        launch {
            val response = repository.getMyLoans()
            when(response){
                is Response.Success ->{

                }
                is Response.Error ->{
                    _errorLD.value = response.error
                }
            }
            _progressBarStatusLD.value = false
        }
    }
}