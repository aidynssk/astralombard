package kz.astralombard.ext

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

/**
 * Created by wokrey@gmail.com on 12.11.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
fun Fragment.hideKeyboard() {
    view?.let {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}