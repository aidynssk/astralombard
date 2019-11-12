package kz.astralombard.base

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
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


}