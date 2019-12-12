package kz.astralombard.home.menu.myloans.details

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kz.astralombard.BR
import kz.astralombard.R
import kz.astralombard.base.ui.RecyclerBindingAdapter
import kz.astralombard.databinding.ItemLoanDetailBinding
import kz.astralombard.home.menu.myloans.data.DetailsHeaderModel

/**
 * Created by wokrey@gmail.com on 10.12.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class LoanDetailsAdapter<T>(
    private val holderLayout: Int,
    private val variableId: Int,
    private val context: Context
): RecyclerBindingAdapter<T>(holderLayout, variableId, context) {

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val item = bindingItems[position]

        if (item is DetailsHeaderModel){
            holder.binding?.setVariable(BR.index, item.index)
            return
        }

        super.onBindViewHolder(holder, position)
    }

    override fun getItemViewType(position: Int): Int {
        if (bindingItems[position] is DetailsHeaderModel){

            return R.layout.item_loan_details_header
        }
        return super.getItemViewType(position)
    }
}