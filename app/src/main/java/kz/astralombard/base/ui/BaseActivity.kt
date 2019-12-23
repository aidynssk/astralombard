package kz.astralombard.base.ui

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kz.astralombard.R
import kz.astralombard.base.WriteUsError
import kz.astralombard.base.LanguageController
import kz.astralombard.base.Navigator
import kz.astralombard.base.data.AboutException
import kz.astralombard.base.data.AstraException
import kz.astralombard.code.PinManager
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import java.util.*

private const val ERROR_DETAIL_KEY = "error"

abstract class BaseActivity
    : AppCompatActivity(),
    Navigator{

    open val progressBar: ProgressDialog by lazy {
        ProgressDialog(
            this,
            this
        )
    }

    fun setDefaultLanguage(language: String) = LanguageController.setLocale(this, language)

    fun getCurrentLanguage(): Locale = LanguageController.getLanguageLocale(this)

    fun showProgress() {
        if (!PinManager.isShowing)
            progressBar.showDialog()
    }

    fun hideProgress() {
        progressBar.hideDialog()
    }

    /*override fun attachBaseContext(newBase: Context?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            super.attachBaseContext(LanguageController.updateResources(newBase, ))
            return
        }
        super.attachBaseContext(newBase)
    }*/

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
            is HttpException -> getServerErrorMessage(exception)
            is IOException -> getString(R.string.no_connection_error)
            is AstraException -> exception.message
            is AboutException -> getWriteUsErrorMessage(exception.type)
            else -> null
        }
        showErrorAlert(message, ok)
    }

    private fun getWriteUsErrorMessage(type: WriteUsError) = getString(
        when(type){
            WriteUsError.EMPTY_SUBJECT -> R.string.about_subject_empty_error
            WriteUsError.EMPTY_TEXT -> R.string.about_text_empty_error
            WriteUsError.EMPTY_PHONE -> R.string.about_phone_empty_error
        }
    )

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

    private fun getServerErrorMessage(error: HttpException): String? {
        error.response()?.errorBody()?.string()?.let {
            val errorObj = JSONObject(it)
            if (errorObj.has(ERROR_DETAIL_KEY)) {
                return errorObj.getJSONArray(ERROR_DETAIL_KEY).getString(0)
            }
        }
        return null
    }
}