package kz.astralombard.base.ui

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.akexorcist.localizationactivity.core.LocalizationActivityDelegate
import com.akexorcist.localizationactivity.core.OnLocaleChangedListener
import kz.astralombard.R
import kz.astralombard.base.DataHolder
import kz.astralombard.base.Navigator
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
    Navigator,
    OnLocaleChangedListener {

    private val localizationDelegate = LocalizationActivityDelegate(this)

    open val progressBar: ProgressDialog by lazy {
        ProgressDialog(
            this,
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        localizationDelegate.addOnLocaleChangedListener(this)
        localizationDelegate.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState, persistentState)
        DataHolder.currentLang = getCurrentLanguage().language
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(localizationDelegate.attachBaseContext(newBase))
    }

    override fun getApplicationContext(): Context =
        localizationDelegate.getApplicationContext(super.getApplicationContext())

    override fun getResources(): Resources = localizationDelegate.getResources(super.getResources())

    fun setLanguage(language: String) {
        localizationDelegate.setLanguage(this, language)
    }

    fun setLanguage(locale: Locale) {
        localizationDelegate.setLanguage(this, locale)
    }

    fun setDefaultLanguage(language: String) {
        localizationDelegate.setDefaultLanguage(language)
    }

    fun setDefaultLanguage(locale: Locale) {
        localizationDelegate.setDefaultLanguage(locale)
    }

    fun getCurrentLanguage(): Locale = localizationDelegate.getLanguage(this)

    override fun onBeforeLocaleChanged() {

    }

    override fun onAfterLocaleChanged() {

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
            is HttpException -> getServerErrorMessage(exception)
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