package kz.astralombard.home.menu.calculator

import kz.astralombard.base.CoroutineViewModel
import kz.astralombard.home.menu.address.model.City
import kz.astralombard.home.menu.calculator.data.ICalculatorRepository

/**
 * Created by wokrey@gmail.com on 7/2/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class CalculatorViewModel(
    private val repository: ICalculatorRepository
): CoroutineViewModel() {
    companion object{
        private const val INTEREST_RATE = 0.0029
    }
    fun getSavedCity() = repository.getSavedCity()


    fun calculatePrice(
        dayString: String,
        handAmount: Double
    ) = handAmount * (getIntFromDays(dayString) * INTEREST_RATE + 1)

    fun calculateHandAmount(
        sampleIndex: Int,
        weight: String
    ) = getDoubleWeight(weight) * getPrice(sampleIndex, getSavedCity()!!)

    fun getIntFromDays(dayString: String) = dayString.substringBefore(' ').toInt()

    fun getPrice(sampleIndex: Int, city: City) = when (sampleIndex) {
        0 -> city.price1
        1 -> city.price2
        2 -> city.price3
        3 -> city.price4
        else -> city.price5
    }

    fun getDoubleWeight(weight: String) = weight.replace(',' ,'.').toDouble()
}