package kz.astralombard.city

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_city.*
import kz.astralombard.R
import kz.astralombard.base.ui.BaseActivity
import kz.astralombard.home.menu.address.presentation.AddressViewModel
import kz.astralombard.home.presentation.HomeActivity
import kz.astralombard.intro.INTRO_SHOWED
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class CityActivity : BaseActivity() {

    var sharedPref: SharedPreferences = get()

    private val viewModel by viewModel<AddressViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        initObservers()
        initListeners()
        viewModel.loadCities()
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

    private fun initListeners() {
        btn_further.setOnClickListener {
            if (spinner.selectedItemPosition >= 0) {
                viewModel.saveCity(spinner.selectedItemPosition)
            }
            openHome()
        }
    }

    private fun initObservers() {
        viewModel.citiesLD.observe(this, Observer { cities ->
            val citiesNames = mutableListOf<String>()
            cities!!.forEach {
                citiesNames.add(it.name)
            }
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                citiesNames
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner.setSelection(0)
        })

        viewModel.errorLD.observe(this, Observer {
            handleError(it)
        })
        viewModel.progressBarStatusLD.observe(this, Observer {
            if (it)
                showProgress()
            else
                hideProgress()
        })
    }

}
