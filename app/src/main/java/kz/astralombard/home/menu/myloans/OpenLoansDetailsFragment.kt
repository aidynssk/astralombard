package kz.astralombard.home.menu.myloans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.astralombard.BR
import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.databinding.FragmentOpenLoansDetailsBinding
import kz.astralombard.ext.hide
import kz.astralombard.home.menu.myloans.data.DetailableItem
import kz.astralombard.home.menu.myloans.data.DetailsHeaderModel
import kz.astralombard.home.menu.myloans.data.MyLoan
import kz.astralombard.home.menu.myloans.details.LoanDetailsAdapter
import kz.astralombard.home.menu.myloans.presentation.MyLoansViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OpenLoansDetailsFragment : BaseFragment() {

    companion object {
        const val LOAN_DETAILS = "loan_details"
        const val HIDE_PAY_INF = "hide_pay_inf"
        fun newInstance(args: Bundle) = OpenLoansDetailsFragment().apply {
            arguments = args
        }
    }

    private val loan by lazy {
        arguments!!.getParcelable<MyLoan>(LOAN_DETAILS)
    }

    private val hidePayInf by lazy {
        arguments!!.getBoolean(HIDE_PAY_INF)
    }

    private val viewModel: MyLoansViewModel by viewModel()

    private lateinit var binding: FragmentOpenLoansDetailsBinding
    private lateinit var adapter: LoanDetailsAdapter<DetailableItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = LoanDetailsAdapter(
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
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_open_loans_details,
            container,
            false
        )
        binding.rvDetails.adapter = adapter
        binding.rvDetails.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        showLoans()
        initObservers()
        viewModel.prolongate(getCurrentLanguage().language, loan.Id)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loanItems = loan.Items
        val detailItems = emptyList<DetailableItem>().toMutableList().apply {
//            if (loanItems!!.size > 1) {
                loanItems?.forEachIndexed { index, item ->
                    add(DetailsHeaderModel(index + 1))
                    add(item)
                }
           /* } else {
                addAll(loanItems)
            }*/

        }
        if (hidePayInf)
            hidePayInformationViews()
        binding.loan = detailItems
        binding.item = loanItems?.first()
    }

    private fun initObservers() {
        viewModel.prolongateResponseLD.observe(viewLifecycleOwner, Observer {
            with(binding) {
                tvCreditAmountValue.text = it.Calculation.CreditAmount
                tvSum.text = it.Calculation.TotalAmount
                tvFineAmountValue.text = it.Calculation.FineAmount
                tvPercentAmountValue.text = it.Calculation.PercentAmount
            }
        })

        viewModel.progressBarStatusLD.observe(viewLifecycleOwner, Observer {
            if (it)
                showProgress()
            else
                hideProgress()
        })
    }

    private fun showLoans() {
        binding.tvLoanNumber.text = "№${loan.item?.NumberTicket}"
    }

    private fun hidePayInformationViews() = with(binding) {
        tvCreditAmountValue.hide()
        tvCreditAmount.hide()
        tvSum.hide()
        tvSumTitle.hide()
        tvFineAmountValue.hide()
        tvFineAmount.hide()
        tvPercentAmountValue.hide()
        tvPercentAmount.hide()
        btnPay.hide()
    }
}
