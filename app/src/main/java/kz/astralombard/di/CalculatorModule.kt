package kz.astralombard.di

import kz.astralombard.home.menu.calculator.CalculatorViewModel
import kz.astralombard.home.menu.calculator.data.CalculatorRepository
import kz.astralombard.home.menu.calculator.data.ICalculatorRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by wokrey@gmail.com on 05.12.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

val calculatorModule = module {
    viewModel<CalculatorViewModel>{
        CalculatorViewModel(
            repository = get()
        )
    }

    factory<ICalculatorRepository> {
        CalculatorRepository(
            prefs = get()
        )
    }
}