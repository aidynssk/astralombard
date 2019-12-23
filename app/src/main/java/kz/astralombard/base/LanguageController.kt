package kz.astralombard.base

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import java.util.*

/**
 * Created by wokrey@gmail.com on 09.12.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

object LanguageController {

    const val APP_LANG = "appLang"


    fun onAttach(context: Context): Context {
        val lang = currentAppLang(context, Locale.getDefault().language)
        return setLocale(context, lang)
    }

    fun onAttach(context: Context, defaultLanguage: String): Context {
        val lang = currentAppLang(context, defaultLanguage)
        return setLocale(context, lang)
    }

    fun getLanguage(context: Context): String? {
        return currentAppLang(context, Locale.getDefault().language)
    }

    fun getLanguageLocale(context: Context)=  Locale.getDefault()

    fun setLocale(context: Context, language: String?): Context {
        saveAppLang(context, language)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else
            updateResourcesLegacy(context, language)

    }

    @TargetApi(Build.VERSION_CODES.N)
    fun updateResources(context: Context, language: String?): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration.apply {
            setLocale(locale)
            setLayoutDirection(locale)
        }

        return context.createConfigurationContext(configuration)
    }

    @Suppress("DEPRECATION")
    private fun updateResourcesLegacy(context: Context, language: String?): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = resources.configuration

        configuration.locale = locale
        configuration.setLayoutDirection(locale)

        resources.updateConfiguration(configuration, resources.displayMetrics)

        return context
    }

    fun currentAppLang(context: Context, language: String): String? {
        val sharedPreferences = context.getSharedPreferences(APP_LANG, Context.MODE_PRIVATE)
        return sharedPreferences.getString(APP_LANG, language)
    }

    fun saveAppLang(context: Context, lang: String?) {
        val sharedPreferences = context.getSharedPreferences(APP_LANG, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(APP_LANG, lang).apply()
    }
}