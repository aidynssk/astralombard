package kz.astralombard.home.menu.profile


import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.databinding.FragmentProfileBinding
import kz.astralombard.home.presentation.HomeViewModel
import kz.astralombard.models.DialogSize
import kz.astralombard.dialogs.LogoutDialog
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: HomeViewModel by sharedViewModel()

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
        initObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        viewModel.loadProfileData()
    }

    private fun initListeners() {
        binding.clChangeCode.setOnClickListener {
            addFragment(CodeFragment.newInstance())
        }
        binding.clChooseCity.setOnClickListener {
            addFragment(CityFragment.newInstance())
        }
        binding.clLogout.setOnClickListener {
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
    private fun initObservers(){
        viewModel.profileLD.observe(viewLifecycleOwner, Observer{
            binding.tvFioValue.text = it.FullName
            binding.tvCityValue.text = it.City
            binding.tvAddressValue.text = it.Address
            binding.tvIinValue.text = it.iin
        })
        viewModel.errorLD.observe(viewLifecycleOwner, Observer {
            handleError(it)
        })
        viewModel.progressBarStatusLD.observe(viewLifecycleOwner, Observer {
            if (it)
                showProgress()
            else
                hideProgress()
        })
    }
}
