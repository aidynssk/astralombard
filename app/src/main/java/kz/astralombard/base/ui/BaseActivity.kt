package kz.astralombard.base.ui

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kz.astralombard.R
import kz.astralombard.base.Navigator
import kz.astralombard.base.data.AstraException
import kz.astralombard.code.PinManager
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

abstract class BaseActivity
    : AppCompatActivity(),
    Navigator {


    open val progressBar: ProgressDialog by lazy {
        ProgressDialog(
            this,
            this
        )
    }


    fun showProgress() {
        if (!PinManager.isShowing)
            progressBar.showDialog()
    }

    fun hideProgress() {
        progressBar.hideDialog()
    }

    override fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.current_menu_container, fragment, fragment::class.java.toString())
            .addToBackStack(null)
            .commit()
    }

    override fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.current_menu_container, fragment)
            .commit()
    }

    override fun popFragment() = supportFragmentManager.popBackStack()

    fun handleError(exception: Exception, ok: (() -> Unit)? = null) {
        val message = when (exception) {
            is HttpException -> exception.response()?.errorBody()?.string()
            is IOException -> "Возможно, проблемы с соединением"
            is AstraException -> exception.message
            else -> null
        }
        showErrorAlert(message, ok)

    }

    fun showErrorAlert(message: String? = null, ok: (() -> Unit)? = null) {
        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle(getString(R.string.error))
            .setMessage(message ?: getString(R.string.error_general))
            .setPositiveButton(R.string.ok) { _, _ ->
                ok?.invoke()
            }
            .create()
            .show()
    }

    fun showAlert(title: String? = null, message: String? = null, ok: (() -> Unit)? = null) {
        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle(title ?: getString(R.string.profile_success_change_code_title))
            .setMessage(message ?: getString(R.string.error_general))
            .setPositiveButton(R.string.ok) { _, _ ->
                ok?.invoke()
            }
            .create()
            .show()
    }
}