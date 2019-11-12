package kz.astralombard.di

import kz.astralombard.home.data.HomeRepository
import kz.astralombard.home.data.HomeRepositoryImpl
import kz.astralombard.home.presentation.HomeViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

/**
 * Created by wokrey@gmail.com on 04.06.2019
 */

val homeModule = module {

    viewModel {
        HomeViewModel(
            repository = get()
        )
    }

    factory {
        HomeRepositoryImpl(
            api = get()
        ) as HomeRepository
    }
}