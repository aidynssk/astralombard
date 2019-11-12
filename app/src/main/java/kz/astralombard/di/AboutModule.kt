package kz.astralombard.di

import kz.astralombard.home.menu.about.data.DefaultAboutRepository
import kz.astralombard.home.menu.about.AboutViewModel
import kz.astralombard.home.menu.about.data.AboutRepository
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

/**
 * Created by wokrey@gmail.com on 7/15/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
val aboutModule = module {

    viewModel {
        AboutViewModel(
            repository = get()
        )
    }

    factory {
        DefaultAboutRepository(
            api = get()
        ) as AboutRepository
    }
}