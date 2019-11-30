package kz.astralombard.home.menu.about.writeus.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.databinding.FragmentWriteUsBinding
import kz.astralombard.home.menu.about.writeus.presentation.WriteUsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by wokrey@gmail.com on 7/2/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class WriteUsFragment : BaseFragment() {

    companion object{
        const val TAG = "WriteUsFragment"
        fun newInstance() = WriteUsFragment()
    }

    private lateinit var binding: FragmentWriteUsBinding
    private val viewModel: WriteUsViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write_us, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers(){
        viewModel.feedbackLD.observe(viewLifecycleOwner, Observer {

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