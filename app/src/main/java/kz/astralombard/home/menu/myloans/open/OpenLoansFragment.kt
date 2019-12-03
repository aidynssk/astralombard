package kz.astralombard.home.menu.myloans.open


import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kz.astralombard.BR

import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.base.ui.RecyclerBindingAdapter
import kz.astralombard.databinding.FragmentOpenLoansBinding
import kz.astralombard.home.menu.myloans.OpenLoansDetailsActivity
import kz.astralombard.home.menu.myloans.data.Item
import kz.astralombard.home.menu.myloans.presentation.MyLoansViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class OpenLoansFragment : BaseFragment(), RecyclerBindingAdapter.OnItemClickListener<Item> {

    private val viewModel: MyLoansViewModel by sharedViewModel()
    companion object{
        fun newInstance() = OpenLoansFragment()
    }

    private var loansAdapter: RecyclerBindingAdapter<Item>? = null
    private lateinit var binding: FragmentOpenLoansBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loansAdapter = RecyclerBindingAdapter(
            holderLayout = R.layout.item_loan,
            variableId = BR.loan,
            context = context!!
        )
        loansAdapter?.setOnItemClickListener(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_open_loans,
            container,
            false
        )

        binding.tvOpenLoans.adapter = loansAdapter
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onItemClick(position: Int, item: Item) {
        val detailsIntent = Intent(context, OpenLoansDetailsActivity::class.java).apply {
            putExtra(OpenLoansDetailsActivity.LOAN_DETAILS, item)
        }
        context?.startActivity(detailsIntent)
    }

}
