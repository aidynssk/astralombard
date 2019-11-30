package kz.astralombard.intro

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_intro.*
import kz.astralombard.R
import kz.astralombard.base.ui.BaseActivity
import kz.astralombard.city.CityActivity
import org.koin.android.ext.android.get

const val INTRO_SHOWED = "intro_showed"
class IntroActivity : BaseActivity() {

    var sharedPref: SharedPreferences = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        small.performClick()
    }

    override fun onStart() {
        super.onStart()
        vp_intro.adapter = IntroPagerAdapter(supportFragmentManager)
        vp_intro.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(index: Int) {
                when (index) {
                    0 -> {
                        small.performClick()
                    }
                    1 -> {
                        medium.performClick()
                    }
                    2 -> {
                        large.performClick()
                    }
                    else -> {

                    }
                }
            }

            override fun onPageScrollStateChanged(p0: Int) {

            }

        })

        btn_skip.setOnClickListener {
            openHome()
        }
    }

    private fun openHome(){
        startActivity(Intent(this, CityActivity::class.java))
        sharedPref.edit().putBoolean(
            INTRO_SHOWED,
            true
        ).apply()
        finish()
    }

}

