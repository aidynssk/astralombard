package kz.astralombard

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.facebook.stetho.Stetho
import kz.astralombard.base.Constants
import kz.astralombard.base.DataHolder
import kz.astralombard.base.RUSSIAN_VALUE
import kz.astralombard.base.SharedPrefKeys
import kz.astralombard.base.ui.BaseActivity
import kz.astralombard.city.CityActivity
import kz.astralombard.home.presentation.HomeActivity
import kz.astralombard.intro.INTRO_SHOWED
import kz.astralombard.intro.IntroActivity
import org.koin.android.ext.android.get

class MainAppActivity : BaseActivity() {

    private val shared: SharedPreferences = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        val lang = shared.getString(SharedPrefKeys.LANGUAGE, RUSSIAN_VALUE)
        setDefaultLanguage(lang)
        DataHolder.currentLang = lang
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app)
        Stetho.initializeWithDefaults(this)
    }

    override fun onResume() {
        super.onResume()
        if(shared.getBoolean(INTRO_SHOWED, false)){
            goHome()
        } else{
            goIntro()
        }
    }

    private fun goHome(){
        val intent = Intent(this, HomeActivity::class.java)
        DataHolder.token = shared.getString(SharedPrefKeys.USER_TOKEN, Constants.DEFAULT_STRING)
        startActivity(intent)
        finish()
    }

    private fun goIntro(){
        val intent = Intent(this, CityActivity::class.java)
        startActivity(intent)
        finish()
    }
}
