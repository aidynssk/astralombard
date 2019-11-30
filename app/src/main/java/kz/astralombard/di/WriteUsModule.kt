package kz.astralombard.di

import kz.astralombard.home.menu.about.writeus.data.IWriteUsRepository
import kz.astralombard.home.menu.about.writeus.data.WriteUsRepository
import kz.astralombard.home.menu.about.writeus.presentation.WriteUsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by wokrey@gmail.com on 30.11.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

val writeUsModule = module {
    viewModel {
        WriteUsViewModel(
            repository = get()
        )
    }
    factory<IWriteUsRepository> {
        WriteUsRepository(
            apiService = get()
        )
    }
}