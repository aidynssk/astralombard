package kz.astralombard.home.menu.myloans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.astralombard.BR
import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.base.ui.RecyclerBindingAdapter
import kz.astralombard.databinding.FragmentOpenLoansDetailsBinding
import kz.astralombard.home.menu.myloans.data.Item
import kz.astralombard.home.menu.myloans.data.MyLoan

class OpenLoansDetailsFragment : BaseFragment() {

    companion object {
        const val LOAN_DETAILS = "loan_details"
        fun newInstance(args: Bundle) = OpenLoansDetailsFragment().apply {
            arguments = args
        }
    }

    private val loan by lazy {
        arguments!!.getParcelable<MyLoan>(LOAN_DETAILS)
    }
    private lateinit var binding: FragmentOpenLoansDetailsBinding
    private lateinit var adapter: RecyclerBindingAdapter<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = RecyclerBindingAdapter(
            holderLayout = R.layout.item_loan_detail,
            variableId = BR.item,
            context = requireContext()
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_open_loans_details, container, false)
        binding.rvDetails.adapter = adapter
        binding.rvDetails.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        showLoans()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loan = loan
    }

    private fun showLoans(){
        binding.tvLoanNumber.text = "№${loan.item?.NumberTicket}"
    }

}
