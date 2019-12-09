package kz.astralombard.base

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    this.currentFocus?.apply {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(getWindowToken(), 0)
    }
}