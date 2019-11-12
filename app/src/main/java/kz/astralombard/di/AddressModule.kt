package kz.astralombard.di

import kz.astralombard.home.menu.address.data.AddressRepository
import kz.astralombard.home.menu.address.data.DefaultAddressRepository
import kz.astralombard.home.menu.address.presentation.AddressViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

/**
 * Created by wokrey@gmail.com on 7/16/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

val addressModule = module {

    viewModel {
        AddressViewModel(
            repository = get()
        )
    }

    factory {
        DefaultAddressRepository(
           api =  get()
        ) as AddressRepository
    }
}