package kz.astralombard.home.menu.address.presentation


import android.Manifest
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
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kz.astralombard.base.Constants
import kz.astralombard.base.PermisionStatus
import kz.astralombard.home.menu.address.model.City
import kz.astralombard.home.menu.address.model.Point
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class AddressesFragment
    : BaseFragment(),
    RecyclerBindingAdapter.OnItemClickListener<Point>,
    LocationListener {
    private val viewModel: AddressViewModel by viewModel()
    lateinit var fusedLocationClient: FusedLocationProviderClient
    private var isLocationDefined = false
    private var location: Location? = null

    companion object {
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
        requestLocationPermission()
    }

    override fun onItemClick(position: Int, item: Point) {
    }

    private fun initObservers() {
        viewModel.citiesLD.observe(viewLifecycleOwner, Observer { cities ->
            val citiesNames = mutableListOf<String>()
            viewModel.getSavedCity()?.let { savedCity ->
                cities?.removeIf { removable ->
                    removable.id == savedCity.id
                }
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

    private fun requestLocationPermission() {
        if (checkPermission()) {
            requestLocation()

            return
        }
        Dexter.withActivity(activity)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    viewModel.saveLocationPermissionStatus(PermisionStatus.ALLOWED)
                    requestLocation()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    viewModel.saveLocationPermissionStatus(PermisionStatus.DENIED)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest,
                    token: PermissionToken
                ) {
//                    token.continuePermissionRequest()
                }
            }).check()
    }

    private fun checkPermission() = ActivityCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED


    private fun initListeners() {
        binding.imgPhone.setOnClickListener {
            val uri = "tel:" + "911".trim()
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
                location?.longitude?.toString() ?: Constants.DEFAULT_ALMATY_LONG
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
                    lat = location.latitude.toString(),
                    long = location.longitude.toString(),
                    id = chosenCity?.id?.toString() ?: "1"
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

    private fun createSpinnerAdapter(citiesNames: List<String>): ArrayAdapter<String> {
        val adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            citiesNames
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }
}
