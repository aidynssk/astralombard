package kz.astralombard.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AlertDialog
import android.view.View
import kz.astralombard.R
import kz.astralombard.models.DialogSize

/**
 * Created by wokrey@gmail.com on 7/16/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
abstract class BaseDialogCreator(
    open val context: Context
){

    abstract val dialogView: View
    abstract val builder: AlertDialog.Builder

    //  required bools
    open var cancelable: Boolean = false
    open var isBackGroundTransparent: Boolean = true

    //  dialog
    open var dialog: AlertDialog? = null
    //  dialog create
    open fun create(dialogSize: DialogSize?): AlertDialog {
        dialog = builder
            .setCancelable(cancelable)
            .create()

        //  very much needed for customised dialogs
        if (isBackGroundTransparent)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (dialogSize != null) {
            dialog?.setOnShowListener {
                val windowlp = dialog?.window?.attributes
                when (dialogSize) {
                    DialogSize.SmallFixedWidth -> windowlp?.width = getDimensionValue(R.dimen.smallDialogWidth)
                    DialogSize.SmallFixedWidthHeight -> {
                        windowlp?.width = getDimensionValue(R.dimen.smallDialogWidth)
                        windowlp?.height = getDimensionValue(R.dimen.smallDialogHeight)
                    }
                    DialogSize.LargeFixedWidth -> windowlp?.width = getDimensionValue(R.dimen.largeDialogWidth)
                    DialogSize.LargeFixedWidthHeight -> {
                        windowlp?.width = getDimensionValue(R.dimen.largeDialogWidth)
                        windowlp?.height = getDimensionValue(R.dimen.largeDialogHeight)
                    }
                }
                dialog?.window?.attributes = windowlp
            }
        }
        return dialog!!
    }

    //  cancel listener
    open fun onCancelListener(func: () -> Unit): AlertDialog.Builder? =
        builder.setOnCancelListener {
            func()
        }


    fun getDimensionValue(id: Int): Int
    {
        return context.resources.getDimension(id).toInt()
    }

    //  view click listener as extension function
    fun View.setClickListenerToDialogButton(func: (() -> Unit)?) =
        setOnClickListener {
            func?.invoke()
            dialog?.dismiss()
        }

    fun AlertDialog.setOnDismissListener(func: (() -> Unit)?) =
        setOnDismissListener {
            func?.invoke()
        }

    fun dismissListener(func: (() -> Unit)? = null) =
        with(this) {
            dismissFunc = func
        }

    var dismissFunc: (() -> Unit)? = null

    fun setDismissListener() =
        with(dialog!!) {
            setOnDismissListener(dismissFunc)
        }
}