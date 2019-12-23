package kz.astralombard.home.menu.address.presentation


import android.Manifest
import android.content.Context
import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_addresses.*
import kz.astralombard.BR

import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.base.ui.RecyclerBindingAdapter
import kz.astralombard.databinding.FragmentAddressesBinding
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.widget.AdapterView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kz.astralombard.base.Constants
import kz.astralombard.home.menu.address.model.City
import kz.astralombard.home.menu.address.model.Point
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.text.TextUtils
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.os.Build
import kz.astralombard.home.presentation.HomeActivity

class AddressesFragment
    : BaseFragment(),
    RecyclerBindingAdapter.OnItemClickListener<Point>,
    LocationListener {
    private val viewModel: AddressViewModel by viewModel()
    lateinit var fusedLocationClient: FusedLocationProviderClient
    private var isLocationDefined = false
    private var location: Location? = null

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 99
        fun newInstance() = AddressesFragment()
    }

    private var loansAdapter: RecyclerBindingAdapter<Point>? = null
    private lateinit var binding: FragmentAddressesBinding
    private var chosenCity: City? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loansAdapter = RecyclerBindingAdapter(
            holderLayout = R.layout.item_address_point,
            variableId = BR.address,
            context = context!!
        )
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        loansAdapter?.setOnItemClickListener(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_addresses,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSavedCity()?.let {
            chosenCity = it
            spn_choose_city.adapter = createSpinnerAdapter(listOf(it.name))
        }
        with(binding) {
            rvAddress.adapter = loansAdapter
            rvAddress.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@AddressesFragment.viewModel
        }
        initObservers()
        viewModel.loadCities()
        defineLocation()
    }

    override fun onResume() {
        super.onResume()
        (activity as? HomeActivity?)?.setCurrentMenuItem(R.id.nav_addresses)
    }

    override fun onItemClick(position: Int, item: Point) {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.size == 1){
            if (grantResults.first() == PackageManager.PERMISSION_GRANTED){
                requestLocation()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun initObservers() {
        viewModel.citiesLD.observe(viewLifecycleOwner, Observer { cities ->
            val citiesNames = mutableListOf<String>()
            viewModel.getSavedCity()?.let { savedCity ->
                val removableCity = cities.find {
                    it.id == savedCity.id
                }
                cities?.remove(removableCity)
                cities?.add(0, savedCity)
            }
            cities!!.forEach {
                citiesNames.add(it.name)
            }
            initListeners()
            spn_choose_city.adapter = createSpinnerAdapter(citiesNames)
            spn_choose_city.setSelection(0)
        })
    }

    private fun defineLocation() {
        if (checkPermission()) {
            requestLocation()
            return
        }
        locationPermissionRequest()
    }

    private fun checkPermission() = ActivityCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED


    private fun initListeners() {
        binding.imgPhone.setOnClickListener {
            val uri = "tel:" + "8 800 070 05 40".trim()
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(uri)
            startActivity(intent)
        }

        binding.spnChooseCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) = viewModel.onCitySelected(
                position,
                location?.latitude?.toString() ?: Constants.DEFAULT_ALMATY_LAT,
                location?.longitude?.toString() ?: Constants.DEFAULT_ALMATY_LONG,
                checkPermission() && isLocationEnabled(requireContext())
            )

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun requestLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                this.location = location
                viewModel.getAddresses(
                    lat = location?.latitude?.toString() ?: Constants.DEFAULT_ALMATY_LAT,
                    long = location?.longitude?.toString() ?: Constants.DEFAULT_ALMATY_LONG,
                    id = chosenCity?.id?.toString() ?: "1",
                    showDistance = checkPermission() && isLocationEnabled(requireContext())
                )
            }
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(l: LocationResult?) {
                super.onLocationResult(l)
                l ?: return
                val lat = l.lastLocation.latitude.toString()
                val long = l.lastLocation.longitude.toString()
            }
        }
    }

    override fun onLocationChanged(newLocation: Location?) {
        isLocationDefined = true
        location = newLocation
    }

    private fun createSpinnerAdapter(citiesNames: List<String>): ArrayAdapter<String> =
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            citiesNames
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

    /*  public void requestPermissionWithRationale() {
   if (ActivityCompat.shouldShowRequestPermissionRationale(this,
           Manifest.permission.READ_EXTERNAL_STORAGE)) {
       final String message = "Storage permission is needed to show files count";
       Snackbar.make(view_, message, Snackbar.LENGTH_LONG)
               .setAction("GRANT", new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       requestReadExtStorage();
                   }
               })
               .show();
   } else {
       requestReadExtStorage();
   }
   }*/

    private fun locationPermissionRequest() = requestPermissions(
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
        LOCATION_PERMISSION_REQUEST_CODE
    )

    fun isLocationEnabled(context: Context): Boolean {
        var locationMode = 0
        val locationProviders: String

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(
                    context.contentResolver,
                    Settings.Secure.LOCATION_MODE
                )

            } catch (e: SettingNotFoundException) {
                e.printStackTrace()
                return false
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF

        } else {
            locationProviders = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED
            )
            return !TextUtils.isEmpty(locationProviders)
        }


    }
}
