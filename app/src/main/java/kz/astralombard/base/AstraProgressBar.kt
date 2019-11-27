package kz.astralombard.base

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kz.astralombard.R

/**
 * Created by wokrey@gmail.com on 26.11.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class ProgressDialog(
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context,
    private val text: String? = context.getString(R.string.loading)
): LifecycleObserver {

    init {
//        lifecycleOwner.lifecycle.addObserver(this)
    }

    private var alertDialog: AlertDialog? = null
    private var message: TextView? = null

    fun showDialog() {
        val promptsView = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
        message = promptsView.findViewById<View>(R.id.progress_message) as TextView
        message?.text = text

        alertDialog = AlertDialog.Builder(context)
            .setView(promptsView)
            .create().apply {
                setCancelable(false)
                setCanceledOnTouchOutside(false)
                show()
            }
    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun hideDialog() {
        if (alertDialog != null) {
            alertDialog!!.cancel()
        }
    }

}
