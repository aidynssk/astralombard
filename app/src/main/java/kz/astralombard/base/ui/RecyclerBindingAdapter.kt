package kz.astralombard.base.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kz.astralombard.R
import kz.astralombard.databinding.ItemNewsBinding
import kz.astralombard.ext.hide
import kz.astralombard.ext.show

/**
 * Created by wokrey@gmail.com on 7/13/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
open class RecyclerBindingAdapter<T>(
    private val holderLayout: Int,
    private val variableId: Int,
    private val context: Context
) : RecyclerView.Adapter<RecyclerBindingAdapter.BindingHolder>() {
    protected var bindingItems: List<T> = ArrayList<T>()
    private var onItemClickListener: OnItemClickListener<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)
        return BindingHolder(v)
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val item = bindingItems[position]

        if (holderLayout == R.layout.item_news) {
            val binding = holder.binding as ItemNewsBinding
            binding.imgArrow.setOnClickListener {
                if (binding.text.visibility == View.VISIBLE) {
                    binding.text.hide()
                    binding.imgArrow.setImageResource(R.drawable.spinner_arrow)
                } else {
                    binding.text.show()
                    binding.imgArrow.setImageResource(R.drawable.arrow_up)
                }
            }
        }

        holder.binding!!.root.setOnClickListener { v ->
            if (onItemClickListener != null)
                onItemClickListener!!.onItemClick(position, item)
        }
        holder.binding.setVariable(variableId, item)
    }

    override fun getItemViewType(position: Int) = holderLayout

    override fun getItemCount() = bindingItems.size

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener<T> {
        fun onItemClick(position: Int, item: T)
    }

    class BindingHolder internal constructor(v: View) : RecyclerView.ViewHolder(v) {
        val binding: ViewDataBinding? = DataBindingUtil.bind(v)
    }

    open fun setItems(items: List<T>?) {
        if (items != null) {
            this.bindingItems = items
        }
        notifyDataSetChanged()
    }
}

