package kz.astralombard.home.menu.about.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.databinding.FragmentCompanyBinding
import kz.astralombard.ext.hide
import kz.astralombard.ext.show
import kz.astralombard.home.menu.about.CompanyViewModel
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by wokrey@gmail.com on 7/2/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class AboutCompanyFragment: BaseFragment() {

    companion object{
        const val TAG = "AboutCompanyFragment"
        fun newInstance() = AboutCompanyFragment()
    }

    private lateinit var binding: FragmentCompanyBinding
    private val viewModel: CompanyViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_company, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.getAboutCompany(
            if (getCurrentLanguage().language == "en")
                "ru"
            else
                getCurrentLanguage().language
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initObservers(){
        viewModel.errorLD.observe(viewLifecycleOwner, Observer {
            handleError(it)
        })

        viewModel.progressBarStatusLD.observe(viewLifecycleOwner, Observer {
            if (it)
                showProgress()
            else
                hideProgress()
        })

        viewModel.aboutCompanyLD.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrBlank())
                binding.btnBack.hide()
            else
                binding.btnBack.show()
        })
    }
}