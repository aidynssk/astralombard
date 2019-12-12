package kz.astralombard.home.menu.myloans.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import kz.astralombard.base.CoroutineViewModel
import kz.astralombard.base.data.Response
import kz.astralombard.home.menu.myloans.data.Item
import kz.astralombard.home.menu.myloans.data.LoansRepository
import kz.astralombard.home.menu.myloans.data.MyLoan
import kz.astralombard.home.menu.myloans.model.Loan
import kz.astralombard.home.menu.myloans.model.MyLoanRequest

/**
 * Created by wokrey@gmail.com on 7/13/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class MyLoansViewModel(
    private val repository: LoansRepository
): CoroutineViewModel() {

    /*var openLoans: List<Loan> = arrayListOf(
        Loan(),
        Loan(),
        Loan()
    )*/

    private val _openLoans = MutableLiveData<List<MyLoan>>()
    val openLoans: LiveData<List<MyLoan>> = _openLoans

    private val _paidLoans = MutableLiveData<List<MyLoan>>()
    val paidLoans: LiveData<List<MyLoan>> = _paidLoans

    fun getMyLoans(){
        _progressBarStatusLD.value = true
        launch {
            val request = MyLoanRequest(
                username = repository.getUsername(),
                password = repository.getIIN()
            )
            val response = repository.getMyLoans(request)
            when(response){
                is Response.Success ->{
                    val myLoans = response.result
                    _openLoans.value = myLoans.filter { !it.item!!.PaidFor }
                    _paidLoans.value = myLoans.filter { it.item!!.PaidFor }
                }
                is Response.Error ->{
                    _errorLD.value = response.error
                }
            }
            _progressBarStatusLD.value = false
        }
    }
}