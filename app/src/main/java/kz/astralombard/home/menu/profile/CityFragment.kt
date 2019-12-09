package kz.astralombard.home.menu.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.databinding.FragmentCityBinding
import kz.astralombard.home.menu.address.model.City
import kz.astralombard.home.menu.address.presentation.AddressViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class CityFragment : BaseFragment() {
    companion object {
        const val TAG = "CityFragment"
        fun newInstance() = CityFragment()
    }

    private lateinit var binding: FragmentCityBinding
    private val viewModel: AddressViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_city,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
        viewModel.loadCities()
    }

    private fun initObservers(){
        viewModel.citiesLD.observe(viewLifecycleOwner, Observer { cities ->
            setDropDownList(cities)
        })

        viewModel.errorLD.observe(viewLifecycleOwner, Observer {
            handleError(it)
        })

        viewModel.progressBarStatusLD.observe(viewLifecycleOwner, Observer {
            if (it)
                showProgress()
            else
                hideProgress()
        })
    }

    private fun initListeners(){
        binding.btnSave.setOnClickListener {
            if(binding.spinner.selectedItemPosition >= 0) {
                viewModel.saveCity(binding.spinner.selectedItemPosition)
                showAlert(message = getString(R.string.profile_city_was_saved)){
                    onBackPressed()
                }
            }
        }
    }

    private fun setDropDownList(cities: List<City>){
        val citiesNames = mutableListOf<String>()
        cities.forEach {
            citiesNames.add(it.name)
        }
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            citiesNames
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
    }
}
