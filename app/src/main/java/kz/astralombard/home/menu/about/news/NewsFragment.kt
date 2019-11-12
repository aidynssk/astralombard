package kz.astralombard.home.menu.about.news

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kz.astralombard.BR
import kz.astralombard.R
import kz.astralombard.base.BaseFragment
import kz.astralombard.base.RecyclerBindingAdapter
import kz.astralombard.databinding.FragmentNewsBinding
import kz.astralombard.home.menu.about.AboutViewModel
import kz.astralombard.home.menu.myloans.model.Loan
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Created by wokrey@gmail.com on 7/2/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class NewsFragment: BaseFragment(), RecyclerBindingAdapter.OnItemClickListener<Loan> {

    companion object{
        const val TAG = "NewsFragment"
        fun newInstance() = NewsFragment()
    }

    private val viewModel: AboutViewModel by sharedViewModel()

    private var loansAdapter: RecyclerBindingAdapter<Loan>? = null
    private lateinit var binding: FragmentNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loansAdapter = RecyclerBindingAdapter(
            holderLayout = R.layout.item_news,
            variableId = BR.loan,
            context = context!!
        )
        loansAdapter?.setOnItemClickListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_news,
            container,
            false
        )


        binding.rvNews.adapter = loansAdapter
        binding.rvNews.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }


    override fun onItemClick(position: Int, item: Loan) {

    }
}