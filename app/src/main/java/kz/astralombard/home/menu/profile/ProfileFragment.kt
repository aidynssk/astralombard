package kz.astralombard.home.menu.profile


import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_profile.*

import kz.astralombard.R
import kz.astralombard.base.BaseFragment
import kz.astralombard.home.presentation.HomeActivity
import kz.astralombard.home.presentation.HomeViewModel
import kz.astralombard.models.DialogSize
import kz.astralombard.dialogs.LogoutDialog
import org.koin.android.viewmodel.ext.android.sharedViewModel

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
            replaceFragment(CodeFragment.newInstance(), CodeFragment.TAG)
        }
        cl_choose_city?.setOnClickListener {
            replaceFragment(CityFragment.newInstance(), CityFragment.TAG)
        }
        cl_logout?.setOnClickListener {
            dialog = LogoutDialog(context!!)
                .apply {
                    yesClickListener {
                        viewModel.onLoginButtonClicked("","")
                    }
                    noClickListener {
                        dialog?.dismiss()
                    }
                }
                .create(DialogSize.SmallFixedWidth)
              dialog?.show()
        }
    }
        private fun replaceFragment(fragment: BaseFragment, tag: String){
            (activity as HomeActivity?)?.replaceFragment(
                id = R.id.current_menu_container,
                f = fragment,
                tag = tag
            )
        }
}
