package kz.astralombard.base.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import kz.astralombard.base.DataHolder
import kz.astralombard.base.LanguageController
import kz.astralombard.base.Navigator
import kz.astralombard.base.updateLanguage
import java.util.*

/**
 * Created by wokrey@gmail.com on 03.06.2019
 */
open class BaseFragment :
    Fragment(),
    Navigator {

    open val navigator: Navigator by lazy {
        activity as Navigator
    }

    open val baseActivity by lazy {
        activity as BaseActivity
    }
    open val progressBar: ProgressDialog by lazy {
        ProgressDialog(
            viewLifecycleOwner,
            requireContext()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataHolder.currentLang = getCurrentLanguage().language
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

    fun setLanguage(language: String) =
        LanguageController.setLocale(requireContext(), language)

    fun getCurrentLanguage(): Locale = baseActivity.getCurrentLanguage()

    override fun addFragment(fragment: Fragment) = navigator.addFragment(fragment)
    override fun replaceFragment(fragment: Fragment) = navigator.replaceFragment(fragment)
    override fun popFragment() = navigator.popFragment()
}