package kz.astralombard.home.menu.address.presentation


import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_addresses.*
import kz.astralombard.BR

import kz.astralombard.R
import kz.astralombard.base.BaseFragment
import kz.astralombard.base.RecyclerBindingAdapter
import kz.astralombard.databinding.FragmentAddressesBinding
import kz.astralombard.home.menu.myloans.model.Loan
import org.koin.android.viewmodel.ext.android.sharedViewModel
import android.content.Intent
import android.net.Uri


class AddressesFragment :
    BaseFragment(), RecyclerBindingAdapter.OnItemClickListener<Loan> {
    private val viewModel: AddressViewModel by sharedViewModel()

    companion object {
        fun newInstance() = AddressesFragment()
    }

    private var loansAdapter: RecyclerBindingAdapter<Loan>? = null
    private lateinit var binding: FragmentAddressesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loansAdapter = RecyclerBindingAdapter(
            holderLayout = R.layout.item_address_point,
            variableId = BR.loan,
            context = context!!
        )
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

        binding.rvAddress.adapter = loansAdapter
        binding.rvAddress.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        initListeners()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initObservers()
        viewModel.loadCities()
    }

    override fun onItemClick(position: Int, item: Loan) {
    }

    private fun initObservers(){
        viewModel.getCitiesLD().observe(this, Observer {cities ->
            val citiesNames = mutableListOf<String>()
            cities!!.forEach {
                citiesNames.add(it.name)
            }
            val adapter = ArrayAdapter<String>(
                context,
                android.R.layout.simple_spinner_item,
                citiesNames
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spn_choose_city.adapter = adapter
        })
    }
    private fun initListeners(){
        binding.imgPhone.setOnClickListener {
            val uri = "tel:" + "911".trim()
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(uri)
            startActivity(intent)
        }
    }

}
