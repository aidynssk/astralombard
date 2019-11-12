package kz.astralombard.home.menu.calculator.car


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kz.astralombard.R
import kz.astralombard.base.BaseFragment

class CarFragment : BaseFragment() {

    companion object{
        fun newInstance() = CarFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_car, container, false)
    }


}
