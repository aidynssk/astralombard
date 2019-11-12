package kz.astralombard.home.menu.about

import kz.astralombard.base.CoroutineViewModel
import kz.astralombard.home.menu.about.data.AboutRepository
import kz.astralombard.home.menu.myloans.model.Loan

/**
 * Created by wokrey@gmail.com on 7/15/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

class AboutViewModel(
    private val repository: AboutRepository
): CoroutineViewModel(){


    var openLoans: List<Loan> = arrayListOf(
        Loan(),
        Loan(),
        Loan()
    )
}