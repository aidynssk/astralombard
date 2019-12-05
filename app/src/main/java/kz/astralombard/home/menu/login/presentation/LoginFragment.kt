package kz.astralombard.home.menu.login.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_login.*
import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.ext.hide
import kz.astralombard.ext.show
import kz.astralombard.home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class LoginFragment : BaseFragment() {
    companion object {
        const val TAG = "LoginFragment"
        fun newInstance() = LoginFragment()
    }

    private val viewModel: HomeViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onStart() {
        super.onStart()
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        btn_request_code.setOnClickListener {
            if ((it as AppCompatButton).text == getString(R.string.login_request_code)) {
                if(!checkBox.isChecked){
                    Toast.makeText(activity, "Пожалуйста, ознакомьтесь с настоящим пользовательским соглашением", Toast.LENGTH_SHORT ).show()
                    return@setOnClickListener
                }

                viewModel.onLoginButtonClicked(
                    phone = et_phone.text.toString(),
                    iin = et_iin.text.toString()
                )
            } else {
                if (et_enter_code.text.toString().isNullOrBlank()){
                    return@setOnClickListener
                }
                viewModel.validateSms(
                    iin = et_iin.text.toString(),
                    phone = et_phone.text.toString(),
                    code = et_enter_code.text.toString()
                )
            }
        }
    }

    private fun initObservers() {
        viewModel.getSmsLD().observe(viewLifecycleOwner, Observer { sms ->
            sms ?: return@Observer
            btn_request_code.text = "Подтвердить"
            showSmsView()

            Toast.makeText(activity, sms.code, Toast.LENGTH_SHORT ).show()
            hideProgress()
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

    private fun showSmsView() {
        et_iin.hide()
        et_phone.hide()
        checkBox.hide()

        tv_enter_code.show()
        et_enter_code.show()
    }
}
