package kz.astralombard.di

import kz.astralombard.home.menu.myloans.data.DefaultLoansRepository
import kz.astralombard.home.menu.myloans.data.LoansRepository
import kz.astralombard.home.menu.myloans.presentation.MyLoansViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by wokrey@gmail.com on 7/13/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

val loansModule = module {
    viewModel {
        MyLoansViewModel(
            repository = get()
        )
    }

    factory {
        DefaultLoansRepository(
            api = get()
        ) as LoansRepository
    }
}