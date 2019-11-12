package kz.astralombard

import android.app.Application
import kz.astralombard.di.modules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class LombardApplication: Application()  {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@LombardApplication)
            modules(modules = modules)
        }
    }
}