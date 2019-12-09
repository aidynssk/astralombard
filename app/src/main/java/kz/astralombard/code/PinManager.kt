package kz.astralombard.code

import androidx.fragment.app.FragmentManager

/**
 * Created by wokrey@gmail.com on 08.12.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
object PinManager {

    var isShowing = false

    fun showPinSetter(manager: FragmentManager) {
        SetPinBottomDialog().show(manager, SetPinBottomDialog::class.java.toString())
    }

    fun showPinReader(manager: FragmentManager){
        PinValidatorBottomDialog().show(manager, PinValidatorBottomDialog::class.java.toString())
    }
}