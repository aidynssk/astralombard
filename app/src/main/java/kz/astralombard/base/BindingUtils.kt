package kz.astralombard.base

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import kz.astralombard.base.ui.RecyclerBindingAdapter
import kz.astralombard.ext.hide
import kz.astralombard.ext.invis
import kz.astralombard.ext.show
import kz.astralombard.home.menu.myloans.model.Loan

/**
 * Created by wokrey@gmail.com on 7/13/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

private const val IMAGE_URL_PREFIX = "https://astra-back.herokuapp.com"
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
    @BindingAdapter("items")
    fun <T> RecyclerView.bindItemsList(items: List<T>?) {
        items ?: return
        val adapter = adapter as RecyclerBindingAdapter<T>
        adapter.setItems(items)
    }

    @JvmStatic
    @BindingAdapter("gone")
    fun gone(view: View, show: Boolean) {
         if (show)
            view.show()
        else
            view.hide()

    }

    @JvmStatic
    @BindingAdapter("invis")
    fun invis(view: View, show: Boolean) {
        if (show)
            view.show()
        else
            view.invis()
    }

    @JvmStatic
    @BindingAdapter("gone")
    fun gone(view: View, show: LiveData<Boolean?>) {
        show.value ?: return
        if (show.value!!)
            view.show()
        else
            view.hide()

    }

    @JvmStatic
    @BindingAdapter("img")
    fun loadImg(view: ImageView, url: String?) {
        if (url.isNullOrBlank()) return

        Glide.with(view)
            .load(IMAGE_URL_PREFIX + url)
            .centerCrop()
            .placeholder(getProgresDrawable(view.context))
            .into(view)
    }

    private fun getProgresDrawable(context: Context) = CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }
}