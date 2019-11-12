package kz.astralombard.home.menu.login.presentation


import android.arch.lifecycle.Observer
import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.widget.AppCompatButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.item_news.view.*
import kz.astralombard.R
import kz.astralombard.base.BaseFragment
import kz.astralombard.home.presentation.HomeViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


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
                viewModel.onLoginButtonClicked(et_iin.text.toString(), et_phone.text.toString())
//            } else {
                /*viewModel.validateSms(
                    iin = et_iin.text.toString(),
                    phone = et_phone.text.toString(),
                    code = et_enter_code.text.toString()
                )*/
            }
        }
    }

    private fun initObservers() {
        viewModel.getSmsLD().observe(this, Observer { sms ->
            sms ?: return@Observer
            btn_request_code.text = "Подтвердить"
            showSmsView()

            Toast.makeText(activity, sms, Toast.LENGTH_SHORT ).show()
        })
    }

    private fun showSmsView() {
        et_iin.visibility = View.GONE
        et_phone.visibility = View.GONE

        tv_enter_code.visibility = View.VISIBLE
        et_enter_code.visibility = View.VISIBLE
    }
}
