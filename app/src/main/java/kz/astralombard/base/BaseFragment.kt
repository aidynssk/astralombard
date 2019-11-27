package kz.astralombard.base

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kz.astralombard.R
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

    fun handleError(exception: Exception, ok: (() -> Unit)? = null) {
        val message = when (exception) {
            is HttpException -> exception.response()?.errorBody()?.string()
            is IOException -> "Возможно, проблемы с соединением"
            else -> null
        }
        showErrorAlert(message, ok)

    }

    fun showErrorAlert(message: String? = null, ok: (() -> Unit)? = null) {
        AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setTitle(requireContext().getString(R.string.error))
            .setMessage(message ?: requireContext().getString(R.string.error_general))
            .setPositiveButton(R.string.ok) { _, _ ->
                ok?.invoke()
            }
            .create()
            .show()
    }

    override fun addFragment(fragment: Fragment) = navigator.addFragment(fragment)
    override fun replaceFragment(fragment: Fragment) = navigator.replaceFragment(fragment)
    override fun popFragment() = navigator.popFragment()
}