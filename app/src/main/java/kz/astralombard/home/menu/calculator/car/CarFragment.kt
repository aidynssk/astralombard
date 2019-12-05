package kz.astralombard.home.menu.calculator.car


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.fragment_car.*

import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.databinding.FragmentCarBinding
import kz.astralombard.home.menu.address.presentation.AddressViewModel
import kz.astralombard.home.menu.calculator.CalculatorViewModel
import kz.astralombard.home.menu.calculator.more.MoreFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class CarFragment : BaseFragment() {

    companion object{
        fun newInstance() = CarFragment()
    }

    private lateinit var binding: FragmentCarBinding
    private val viewModel: CalculatorViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_car, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners(){
        btn_more.setOnClickListener {
            addFragment(MoreFragment.newInstance(MoreFragment.MORE_CAR))
        }
        binding.btnCalculate.setOnClickListener {
            val marketPrice = binding.etMarketPrice.text.toString()
            if (marketPrice.isBlank()) return@setOnClickListener

            val repaymentSum = viewModel.calculateCarHandAmount(
                marketPrice.toInt(),
                binding.checkWithLicense.isChecked
            )
            binding.etGivenSumValue.setText(repaymentSum.roundToInt().toString())
            binding.etRepaymentValue.setText(marketPrice)
        }
    }


}
