package kz.astralombard.home.menu.calculator.gold


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.fragment_gold.*

import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.home.menu.calculator.more.MoreFragment


class GoldFragment : BaseFragment() {
    companion object{
            fun newInstance() = GoldFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gold, container, false)
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
    }
    private fun bindAdapter(spinner: Spinner, array: Int){
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
