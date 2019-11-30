package kz.astralombard.home.menu.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_code.*

import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.home.presentation.HomeActivity


class CodeFragment : BaseFragment() {

    companion object{
        const val TAG = "CodeFragment"

        fun newInstance() = CodeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_code, container, false)
    }

    override fun onStart() {
        super.onStart()
        btn_save.setOnClickListener {
            (activity as? HomeActivity)?.onBackPressed()
        }
    }
}
