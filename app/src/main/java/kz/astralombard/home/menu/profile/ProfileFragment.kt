package kz.astralombard.home.menu.profile


import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_profile.*

import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.home.presentation.HomeViewModel
import kz.astralombard.models.DialogSize
import kz.astralombard.dialogs.LogoutDialog
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ProfileFragment : BaseFragment() {

    val viewModel: HomeViewModel by sharedViewModel()

    var dialog: AlertDialog? = null

    companion object {
        const val TAG="ProfileFragment"
        fun newInstance() = ProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onStart() {
        super.onStart()
        initListeners()
    }

    private fun initListeners() {
        cl_change_code?.setOnClickListener {
            replaceFragment(CodeFragment.newInstance())
        }
        cl_choose_city?.setOnClickListener {
            replaceFragment(CityFragment.newInstance())
        }
        cl_logout?.setOnClickListener {
            dialog = LogoutDialog(requireContext())
                .apply {
                    yesClickListener {
                        viewModel.logoutConfirmed()
                    }
                    noClickListener {
                        dialog?.dismiss()
                    }
                }
                .create(DialogSize.SmallFixedWidth)
              dialog?.show()
        }
    }
}
