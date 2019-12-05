package kz.astralombard.home.menu.calculator.gold


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.fragment_gold.*

import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.databinding.FragmentGoldBinding
import kz.astralombard.home.menu.calculator.CalculatorViewModel
import kz.astralombard.home.menu.calculator.more.MoreFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt


class GoldFragment : BaseFragment() {
    companion object{
            fun newInstance() = GoldFragment()
    }

    private val viewModel: CalculatorViewModel by viewModel()
    private lateinit var binding: FragmentGoldBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gold, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindAdapter(spinner_period, R.array.period_array)
        bindAdapter(spinner_sample, R.array.sample_array)
        bindAdapter(spinner_weight, R.array.weight_array)

        initListeners()
    }

    private fun initListeners(){
        btn_more.setOnClickListener {
            addFragment(MoreFragment.newInstance(MoreFragment.MORE_GOLD))
        }
        binding.btnCalculate.setOnClickListener {
            val handAmount = viewModel.calculateGoldHandAmount(spinner_sample.selectedItemPosition, spinner_weight.selectedItem.toString())
            val repaymentSum = viewModel.calculateGoldPrice(spinner_period.selectedItem.toString(), handAmount)

            binding.tvGivenSumValue.setText(handAmount.roundToInt().toString())
            binding.tvRepaymentValue.setText(repaymentSum.roundToInt().toString())
        }
    }
    private fun bindAdapter(spinner: Spinner, array: Int){
        spinner.setSelection(0)
        ArrayAdapter.createFromResource(
            requireContext(),
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
