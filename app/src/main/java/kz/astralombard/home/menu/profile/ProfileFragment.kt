package kz.astralombard.home.menu.profile


import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.fragment_profile.*

import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.databinding.FragmentProfileBinding
import kz.astralombard.home.presentation.HomeViewModel
import kz.astralombard.models.DialogSize
import kz.astralombard.dialogs.LogoutDialog
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ProfileFragment : BaseFragment() {

    private val viewModel: HomeViewModel by sharedViewModel()
    private lateinit var binding: FragmentProfileBinding

    private var dialog: AlertDialog? = null

    companion object {
        const val TAG="ProfileFragment"
        fun newInstance() = ProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
