package kz.astralombard.di

import kz.astralombard.home.menu.about.data.ICompanyRepository
import kz.astralombard.home.menu.about.data.CompanyRepository
import kz.astralombard.home.menu.about.CompanyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by wokrey@gmail.com on 30.11.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

val companyModule = module {
    viewModel {
        CompanyViewModel(
            repository = get()
        )
    }
    factory<ICompanyRepository> {
        CompanyRepository(
            apiService = get()
        )
    }
}