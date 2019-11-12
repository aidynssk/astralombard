package kz.astralombard

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import kz.astralombard.base.BaseActivity
import kz.astralombard.home.presentation.HomeActivity
import kz.astralombard.intro.INTRO_SHOWED
import kz.astralombard.intro.IntroActivity
import org.koin.android.ext.android.get

class MainAppActivity : BaseActivity() {

    private val shared: SharedPreferences = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app)
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
        startActivity(intent)
        finish()
    }

    private fun goIntro(){
        val intent = Intent(this, IntroActivity::class.java)
        startActivity(intent)
        finish()
    }
}
