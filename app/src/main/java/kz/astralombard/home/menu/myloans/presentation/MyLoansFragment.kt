package kz.astralombard.home.menu.myloans.presentation


import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_calculator.cl_content

import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.databinding.FragmentMyLoansBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


private const val OPEN_PAGE_INDEX = 0
private const val HISTORY_PAGE_INDEX = 1
class MyLoansFragment : BaseFragment() {

    companion object {
        fun newInstance() = MyLoansFragment()
    }

    private val viewModel: MyLoansViewModel by sharedViewModel()

    lateinit var binding: FragmentMyLoansBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_my_loans,
            container,
            false
        )
        binding.pagerCalc.adapter =
            MyLoanPagerAdapter(childFragmentManager)
        binding.pagerCalc.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(index: Int) {
                changeBg(index)
            }

            override fun onPageScrollStateChanged(p0: Int) {

            }

        })
        initListeners()

        return binding.root
    }

    private fun initListeners(){
        binding.tvOpenLoans.setOnClickListener {
            binding.pagerCalc.setCurrentItem(OPEN_PAGE_INDEX, true)
        }
        binding.tvHistory.setOnClickListener {
            binding.pagerCalc.setCurrentItem(HISTORY_PAGE_INDEX, true)
        }
    }

    private fun changeBg(pageIndex: Int){
        cl_content?.background = null
        if (pageIndex == OPEN_PAGE_INDEX){
            cl_content?.background = ContextCompat.getDrawable(context!!, R.drawable.bg_with_pager_left)
            return
        }
        cl_content?.background = ContextCompat.getDrawable(context!!, R.drawable.bg_with_pager_right)
    }
}
