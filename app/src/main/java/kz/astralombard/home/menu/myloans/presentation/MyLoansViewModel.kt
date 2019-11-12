package kz.astralombard.home.menu.myloans.presentation

import kz.astralombard.base.CoroutineViewModel
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
}