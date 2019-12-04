package kz.astralombard.home.menu.calculator.car


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_car.*

import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.home.menu.calculator.more.MoreFragment

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners(){
        btn_more.setOnClickListener {
            addFragment(MoreFragment.newInstance(MoreFragment.MORE_CAR))
        }
    }


}
