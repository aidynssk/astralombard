package kz.astralombard.base.ui

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kz.astralombard.R
import kz.astralombard.base.Navigator
import kz.astralombard.base.data.AstraException
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

/**
 * Created by wokrey@gmail.com on 03.06.2019
 */
open class BaseFragment :
    Fragment(),
    Navigator {

    open val navigator: Navigator by lazy {
        activity as Navigator
    }
    open val progressBar: ProgressDialog by lazy {
        ProgressDialog(
            viewLifecycleOwner,
            requireContext()
        )
    }

    fun showProgress() {
        progressBar.showDialog()
    }

    fun hideProgress() {
        progressBar.hideDialog()
    }

    fun handleError(exception: Exception, ok: (() -> Unit)? = null)
            = (requireActivity() as BaseActivity).handleError(exception, ok)

    fun showErrorAlert(message: String? = null, ok: (() -> Unit)? = null)
            = (requireActivity() as BaseActivity).showErrorAlert(message, ok)

    fun showAlert(title: String? = null, message: String? = null, ok: (() -> Unit)? = null)
            = (requireActivity() as BaseActivity).showAlert(title, message, ok)

    fun onBackPressed() = activity?.onBackPressed()

    override fun addFragment(fragment: Fragment) = navigator.addFragment(fragment)
    override fun replaceFragment(fragment: Fragment) = navigator.replaceFragment(fragment)
    override fun popFragment() = navigator.popFragment()
}