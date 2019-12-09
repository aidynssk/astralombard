package kz.astralombard.home.menu.profile


import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
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
import kz.astralombard.ext.toDate
import kz.astralombard.ext.toString
import kz.astralombard.home.menu.address.presentation.AddressViewModel
import kz.astralombard.home.presentation.HomeViewModel
import kz.astralombard.models.DialogSize
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: HomeViewModel by sharedViewModel()
    private val addressViewModel: AddressViewModel by viewModel()

    private var dialog: AlertDialog? = null

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
        defineLanguage()
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
        binding.rgLanguage.setOnCheckedChangeListener { group, checkedId ->
            var languageToLoad = RUSSIAN_VALUE
            when (checkedId) {
                R.id.kzLang -> {
                    viewModel.onLanguageChosen(KAZAKH_VALUE)
                    languageToLoad = KAZAKH_VALUE
                }
                R.id.ruLang -> {
                    viewModel.onLanguageChosen(RUSSIAN_VALUE)
                    languageToLoad = RUSSIAN_VALUE
                }
            }

            // your language
            val locale = Locale(languageToLoad)
            Locale.setDefault(locale)
            val config = Configuration()
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                config.locales = LocaleList(locale)
            else
                config.locale = locale

            activity!!.createConfigurationContext(config)
            activity?.recreate()

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

    private fun defineLanguage() = when (viewModel.getSavedLang()) {
        RUSSIAN_VALUE -> binding.ruLang.performClick()
        KAZAKH_VALUE -> binding.kzLang.performClick()
        else -> {}
    }
}
