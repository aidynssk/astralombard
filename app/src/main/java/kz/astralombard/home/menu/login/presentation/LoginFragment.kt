package kz.astralombard.home.menu.login.presentation


import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_login.*
import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.databinding.FragmentLoginBinding
import kz.astralombard.ext.hide
import kz.astralombard.ext.hideKeyboard
import kz.astralombard.ext.show
import kz.astralombard.home.presentation.HomeActivity
import kz.astralombard.home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class LoginFragment : BaseFragment() {
    companion object {
        const val TAG = "LoginFragment"
        fun newInstance() = LoginFragment()
    }

    private val viewModel: HomeViewModel by sharedViewModel()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSMSObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        (activity as? HomeActivity?)?.setCurrentMenuItem(R.id.nav_login)
    }

    private fun initListeners() {
        et_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s ?: return
                if (s.length == 18) hideKeyboard()
            }
        })
        btn_request_code.setOnClickListener {
            if (!checkBox.isChecked) {
                Toast.makeText(
                    activity,
                    getString(R.string.login_read_user_agreement),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            viewModel.onLoginButtonClicked(
                phone = "+7" + binding.etPhone.rawText,
                iin = binding.etIin.text.toString()
            )
        }

        et_phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
    }

    private fun initSMSObserver() {
        viewModel.getSmsLD().observe(this, Observer { sms ->
            addFragment(ConfirmCodeFragment.newInstance())

            Toast.makeText(activity, sms.code, Toast.LENGTH_SHORT).show()
            hideProgress()
        })

    }

    private fun initObservers() {
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

    private fun showSmsView() {
        et_iin.hide()
        binding.etPhone.hide()
        checkBox.hide()

        tv_enter_code.show()
        et_enter_code.show()
    }

    private fun hideSmsView() {
        et_iin.show()
        binding.etPhone.show()
        checkBox.show()

        tv_enter_code.hide()
        et_enter_code.hide()
    }
}
