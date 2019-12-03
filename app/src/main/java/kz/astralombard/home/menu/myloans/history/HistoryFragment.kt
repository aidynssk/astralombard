package kz.astralombard.home.menu.myloans.history


import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kz.astralombard.BR
import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.base.ui.RecyclerBindingAdapter
import kz.astralombard.databinding.FragmentHistoryBinding
import kz.astralombard.home.menu.myloans.data.Item
import kz.astralombard.home.menu.myloans.presentation.MyLoansViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HistoryFragment : BaseFragment(), RecyclerBindingAdapter.OnItemClickListener<Item> {

    companion object{
        fun newInstance() = HistoryFragment()
    }
    private val viewModel: MyLoansViewModel by sharedViewModel()

    private var loansAdapter: RecyclerBindingAdapter<Item>? = null
    private lateinit var binding: FragmentHistoryBinding

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
            R.layout.fragment_history,
            container,
            false
        )
        initObservers()
        with(binding) {
            rvHistory.adapter = loansAdapter
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@HistoryFragment.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMyLoans()
    }

    override fun onItemClick(position: Int, item: Item) {

    }

    private fun initObservers(){
        viewModel.progressBarStatusLD.observe(viewLifecycleOwner, Observer {
            if (it)
                showProgress()
            else
                hideProgress()
        })
    }

}
