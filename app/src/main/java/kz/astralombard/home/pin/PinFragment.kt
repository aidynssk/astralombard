package kz.astralombard.home.pin


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment


class PinFragment : BaseFragment() {

    companion object{
        fun newInstance() = PinFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pin, container, false)
    }


}
