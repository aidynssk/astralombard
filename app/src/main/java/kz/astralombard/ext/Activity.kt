package kz.astralombard.base

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.view.inputmethod.InputMethodManager
import java.util.*

fun Activity.hideKeyboard() {
    this.currentFocus?.apply {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(getWindowToken(), 0)
    }
}

fun Context.updateLanguage(language: String): Context {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val res = resources
    val config = Configuration(res.configuration)
    config.setLocale(locale)

    return createConfigurationContext(config)
}