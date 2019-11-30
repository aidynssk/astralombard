package kz.astralombard.base

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kz.astralombard.base.ui.RecyclerBindingAdapter
import kz.astralombard.ext.hide
import kz.astralombard.ext.show
import kz.astralombard.home.menu.myloans.model.Loan

/**
 * Created by wokrey@gmail.com on 7/13/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
object BindingUtils {
    @JvmStatic
    @BindingAdapter("loans")
    fun RecyclerView.bindItems(items: List<Loan>) {
        val adapter = adapter as RecyclerBindingAdapter<Loan>
        adapter.setItems(items)
    }

    @JvmStatic
    @BindingAdapter("items")
    fun <T> RecyclerView.bindItems(items: LiveData<List<T>?>?) {
        items ?: return
        items.value ?: return
        val adapter = adapter as RecyclerBindingAdapter<T>
        adapter.setItems(items.value)
    }

    @JvmStatic
    @BindingAdapter("gone")
    fun gone(view: View, show: Boolean) {
         if (show)
            view.show()
        else
            view.hide()

    }


}