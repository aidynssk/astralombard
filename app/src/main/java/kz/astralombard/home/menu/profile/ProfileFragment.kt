package kz.astralombard.home.menu.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import kz.astralombard.R
import kz.astralombard.base.Constants
import kz.astralombard.base.KAZAKH_VALUE
import kz.astralombard.base.RUSSIAN_VALUE
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.databinding.FragmentProfileBinding
import kz.astralombard.dialogs.LogoutDialog
import kz.astralombard.ext.hide
import kz.astralombard.ext.show
import kz.astralombard.ext.toDate
import kz.astralombard.ext.toString
import kz.astralombard.home.menu.address.presentation.AddressViewModel
import kz.astralombard.home.presentation.HomeViewModel
import kz.astralombard.models.DialogSize
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: HomeViewModel by sharedViewModel()
    private val addressViewModel: AddressViewModel by viewModel()

    private var dialog: AlertDialog? = null
    private var confirmationDialog: AlertDialog? = null

    companion object {
        const val TAG = "ProfileFragment"
        fun newInstance() = ProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        initObservers()
        defineChosenLanguage(viewModel.getSavedLang())
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
        binding.ruLang.setOnClickListener {
            viewModel.onLanguageChosen(RUSSIAN_VALUE)
            showRestartConfirmation(RUSSIAN_VALUE)
        }

        binding.kzLang.setOnClickListener {
            viewModel.onLanguageChosen(KAZAKH_VALUE)
            showRestartConfirmation(KAZAKH_VALUE)
        }
    }

    private fun initObservers() {
        viewModel.profileLD.observe(viewLifecycleOwner, Observer {
            binding.tvFioValue.text = it.FullName
            binding.tvCityValue.text = addressViewModel.getSavedCity()?.name ?: Constants.DEFAULT_STRING
            binding.tvAddressValue.text = it.Address
            binding.tvIinValue.text = it.iin
            binding.tvBirthdayValue.text =
                it.BirthDate.toDate(Constants.YYYY_DD_MM).toString(Constants.DD_MM_YYYY) + " г."
            binding.tvPhoneValue.text = it.Phone
        })
        viewModel.errorLD.observe(viewLifecycleOwner, Observer {
            handleError(it)
        })
        viewModel.progressBarStatusLD.observe(viewLifecycleOwner, Observer {
            if (it)
                binding.progressBar.show()
            else
                binding.progressBar.hide()
        })
    }

    private fun defineChosenLanguage(lang: String) = when (lang) {
        RUSSIAN_VALUE -> binding.ruLang.isChecked = true
        KAZAKH_VALUE -> binding.kzLang.isChecked = true
        else -> {}
    }

    private fun showRestartConfirmation(lang: String) {
        confirmationDialog = AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setTitle(getString(R.string.profile_restart_app_title))
            .setMessage(getString(R.string.profile_restart_app_text))
            .setPositiveButton(R.string.ok) { _, _ ->
                onBackPressed()
                setLanguage(lang)
            }
            .setNegativeButton(R.string.cancel){ dialog, which ->
                dialog?.dismiss()
                if (lang == RUSSIAN_VALUE)
                    defineChosenLanguage(KAZAKH_VALUE)
                else
                    defineChosenLanguage(RUSSIAN_VALUE)
            }
            .create()

        confirmationDialog?.show()
    }
}
