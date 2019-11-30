package kz.astralombard.base.ui

import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kz.astralombard.R
import kz.astralombard.base.Navigator

abstract class BaseActivity
    : AppCompatActivity(),
    Navigator {


    fun showProgress() {
        val progressDialog = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal)
    }

    override fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.current_menu_container, fragment, fragment::class.java.toString())
            .addToBackStack(null)
            .commit()
    }

    override fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.current_menu_container, fragment)
            .commit()
    }

    override fun popFragment() = supportFragmentManager.popBackStack()
}