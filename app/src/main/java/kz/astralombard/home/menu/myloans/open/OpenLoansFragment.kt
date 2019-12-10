package kz.astralombard.home.menu.myloans.open


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kz.astralombard.BR
import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.base.ui.RecyclerBindingAdapter
import kz.astralombard.databinding.FragmentOpenLoansBinding
import kz.astralombard.home.menu.myloans.OpenLoansDetailsFragment
import kz.astralombard.home.menu.myloans.data.MyLoan
import kz.astralombard.home.menu.myloans.presentation.MyLoansViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class OpenLoansFragment : BaseFragment(), RecyclerBindingAdapter.OnItemClickListener<MyLoan> {

    private val viewModel: MyLoansViewModel by sharedViewModel()
    companion object{
        fun newInstance() = OpenLoansFragment()
    }

    private var loansAdapter: RecyclerBindingAdapter<MyLoan>? = null
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

    override fun onItemClick(position: Int, item: MyLoan) {
        val bundle = Bundle().apply {
            putParcelable(OpenLoansDetailsFragment.LOAN_DETAILS, item)
        }
        addFragment(OpenLoansDetailsFragment.newInstance(bundle))
    }
}
