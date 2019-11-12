package kz.astralombard.di

import android.preference.PreferenceManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kz.astralombard.base.ApiService
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://astra182.herokuapp.com/api/"
private const val TEST_BASE_URL = "http://185.146.1.56/api/"

val appModule = module {
    single {
        OkHttpClient.Builder().addInterceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("Accept", "Application/JSON")
                .build()

            return@addInterceptor it.proceed(request)
        }
            .build()
    }

    single {
        PreferenceManager.getDefaultSharedPreferences(androidApplication().applicationContext)
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(ApiService::class.java)
    }
}