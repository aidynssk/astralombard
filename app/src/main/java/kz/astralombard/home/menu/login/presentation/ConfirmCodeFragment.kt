package kz.astralombard.home.menu.login.presentation


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_confirm_code.*

import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.ext.hideKeyboard
import kz.astralombard.home.presentation.HomeActivity
import kz.astralombard.home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass.
 */
class ConfirmCodeFragment : BaseFragment() {

    companion object{
        fun newInstance() = ConfirmCodeFragment()
    }
    private val viewModel: HomeViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUserLoggedLD().observe(this, Observer {
            if (it)
                onDestroy()
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        et_enter_code.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s ?: return
                if (s.length == 4) hideKeyboard()
            }
        })
        btn_request_code.setOnClickListener {
                if (et_enter_code.text.toString().isNullOrBlank()){
                    return@setOnClickListener
                }
                viewModel.validateSms(
                    iin = viewModel.getSmsLD().value!!.password,
                    phone = viewModel.getSmsLD().value!!.username,
                    code = et_enter_code.text.toString()
                )
            }
    }

    private fun initObservers() {
        viewModel.userSuccesLogged.observe(viewLifecycleOwner, Observer {
            startActivity(Intent(requireContext(), HomeActivity::class.java))
            activity?.finish()
        })
        viewModel.progressBarStatusLD.observe(viewLifecycleOwner, Observer {
            if (it)
                showProgress()
            else
                hideProgress()
        })

        viewModel.errorLD.observe(viewLifecycleOwner, Observer {
            handleError(it)
        })
    }
}
