package kz.astralombard.dialogs

import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import kz.astralombard.R
import kz.astralombard.base.ui.BaseDialogCreator
import kz.astralombard.databinding.DialogLogoutBinding
import kz.astralombard.models.DialogSize

/**
 * Created by wokrey@gmail.com on 7/17/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class LogoutDialog(
    override val context: Context
): BaseDialogCreator(context = context) {
    lateinit var binding: DialogLogoutBinding
    private val dialogSize: DialogSize? = DialogSize.SmallFixedWidthHeight
    //  dialog view
    override val dialogView: View by lazy {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context) ,
            R.layout.dialog_logout, null, false)
        binding.root
    }

    override val builder: AlertDialog.Builder = AlertDialog.Builder(context).setView(dialogView)

    override fun create(dialogSize: DialogSize?): AlertDialog {
        dialog = super.create(dialogSize ?: this.dialogSize)

        val windowlp = dialog?.window?.attributes
        windowlp?.windowAnimations = R.style.DialogAnimation
        dialog?.window?.attributes = windowlp
        setDismissListener()

        return dialog!!
    }

    fun yesClickListener(func: (() -> Unit)? = null) =
        binding.btnYes.setClickListenerToDialogButton(func)

    fun noClickListener(func: (() -> Unit)? = null) =
        binding.btnNo.setClickListenerToDialogButton(func)
}