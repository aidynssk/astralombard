package kz.astralombard.home.menu.about.news

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
import kz.astralombard.databinding.FragmentNewsBinding
import kz.astralombard.home.menu.about.CompanyViewModel
import kz.astralombard.home.menu.about.data.News
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by wokrey@gmail.com on 7/2/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class NewsFragment:
    BaseFragment(),
    RecyclerBindingAdapter.OnItemClickListener<News> {

    companion object{
        const val TAG = "NewsFragment"
        fun newInstance() = NewsFragment()
    }

    private val viewModel: CompanyViewModel by viewModel()

    private var loansAdapter: RecyclerBindingAdapter<News>? = null
    private lateinit var binding: FragmentNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loansAdapter = RecyclerBindingAdapter(
            holderLayout = R.layout.item_news,
            variableId = BR.news,
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
        with(binding) {
            rvNews.adapter = loansAdapter
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@NewsFragment.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        viewModel.getNews()
    }

    private fun initObservers(){
        viewModel.errorLD.observe(viewLifecycleOwner, Observer {
            handleError(it)
        })
        viewModel.progressBarStatusLD.observe(viewLifecycleOwner, Observer {
            if (it)
                showProgress()
            else
                hideProgress()
        })
    }


    override fun onItemClick(position: Int, item: News) {

    }
}