package kz.astralombard

import android.app.Application
import kz.astralombard.di.modules
import org.koin.android.ext.android.startKoin

class LombardApplication: Application()  {

    override fun onCreate() {
        super.onCreate()
        startKoin(
            modules = modules,
            androidContext = this
        )
    }
}