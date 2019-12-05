package kz.astralombard.home.menu.myloans


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class OpenLoansDetailsFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_open_loans_details, container, false)
    }


}
