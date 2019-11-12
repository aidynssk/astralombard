package kz.astralombard.home.menu.calculator.gold


import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.fragment_gold.*

import kz.astralombard.R
import kz.astralombard.base.BaseFragment


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

    override fun onStart() {
        super.onStart()
       bindAdapter(spinner_period, R.array.planets_array)
       bindAdapter(spinner_sample, R.array.planets_array)
       bindAdapter(spinner_weight, R.array.planets_array)
    }


    private fun bindAdapter(spinner: Spinner, array: Int){
        ArrayAdapter.createFromResource(
            context,
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
