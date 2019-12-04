package kz.astralombard.home.menu.calculator.data

import android.content.SharedPreferences
import com.google.gson.Gson
import kz.astralombard.base.SharedPrefKeys
import kz.astralombard.base.data.BaseRepository
import kz.astralombard.home.menu.address.model.City

/**
 * Created by wokrey@gmail.com on 05.12.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

interface ICalculatorRepository {

    fun getSavedCity(): City?

}

class CalculatorRepository(
    private val prefs: SharedPreferences
) : BaseRepository(), ICalculatorRepository {

    override fun getSavedCity() = prefs.getString(SharedPrefKeys.SAVED_CITY, null)?.let {
        Gson().fromJson(it, City::class.java)
    }
}