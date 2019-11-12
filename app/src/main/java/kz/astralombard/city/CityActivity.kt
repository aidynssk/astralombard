package kz.astralombard.city

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_city.*
import kotlinx.android.synthetic.main.activity_intro.*
import kz.astralombard.R
import kz.astralombard.base.BaseActivity
import kz.astralombard.home.presentation.HomeActivity
import kz.astralombard.intro.INTRO_SHOWED
import org.koin.android.ext.android.get

class CityActivity : BaseActivity() {

    var sharedPref: SharedPreferences = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
    }

    override fun onStart() {
        super.onStart()
        btn_further.setOnClickListener {
            openHome()
        }
        bindAdapter(spinner, R.array.planets_array)

    }

    private fun openHome(){
        startActivity(Intent(this, HomeActivity::class.java))
        sharedPref.edit().putBoolean(
            INTRO_SHOWED,
            true
        ).apply()
        finish()
    }

    private fun bindAdapter(spinner: Spinner, array: Int){
        ArrayAdapter.createFromResource(
            this,
            array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }
}
