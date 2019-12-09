package kz.astralombard.code

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kz.astralombard.R

/**
 * Created by wokrey@gmail.com on 08.12.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
open class BasePinFragmentDialog: DialogFragment() {

    override fun onStart() {
        super.onStart()
        dialog?.let {
            with(it.window!!) {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        PinManager.isShowing = true
        super.show(manager, tag)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogBottomAnimation
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun dismiss() {
        super.dismiss()
        PinManager.isShowing = false
    }
}