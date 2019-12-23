package kz.astralombard

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.facebook.stetho.Stetho
import kz.astralombard.di.modules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class LombardApplication : Application() {

    companion object {
        fun updateLanguage(ctx: Context) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(ctx)
            val lang = prefs.getString("locale_override", "")
            updateLanguage(ctx, lang)
        }

        fun updateLanguage(ctx: Context, lang: String?) {
            /*     val cfg = Configuration()
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                     if (!TextUtils.isEmpty(lang))
                         cfg.locales = LocaleList(Locale(lang))
                     else
                         cfg.locales = LocaleList(Locale.getDefault())
                 } else {
                     if (!TextUtils.isEmpty(lang))
                         cfg.locale = Locale(lang)
                     else
                         cfg.locale = Locale.getDefault()
                 }*/

        }
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        startKoin {
            androidLogger()
            androidContext(this@LombardApplication)
            modules(modules = modules)
        }
    }
}