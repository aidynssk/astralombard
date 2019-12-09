package kz.astralombard.home.menu.calculator


import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_calculator.*

import kz.astralombard.R
import kz.astralombard.base.hideKeyboard
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.databinding.FragmentCalculatorBinding

private const val GOLD_PAGE_INDEX = 0
private const val CAR_PAGE_INDEX = 1
class CalculatorFragment : BaseFragment() {

    companion object {
        fun newInstance() = CalculatorFragment()
    }

    lateinit var binding: FragmentCalculatorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_calculator,
            container,
            false
        )
        binding.pagerCalc.adapter = CalculatorPagerAdapter(childFragmentManager)
        binding.pagerCalc.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(index: Int) {
                if (index == 0) {
                    activity?.hideKeyboard()
                }
                changeBg(index)
            }

            override fun onPageScrollStateChanged(p0: Int) {

            }

        })
        initListeners()

        return binding.root
    }

    private fun initListeners(){
        binding.tvCar.setOnClickListener {
            binding.pagerCalc.setCurrentItem(CAR_PAGE_INDEX, true)
        }
        binding.tvGold.setOnClickListener {
            binding.pagerCalc.setCurrentItem(GOLD_PAGE_INDEX, true)
        }
    }

    private fun changeBg(pageIndex: Int){
        cl_content?.background = null
        if (pageIndex == GOLD_PAGE_INDEX){
            cl_content?.background = ContextCompat.getDrawable(context!!, R.drawable.bg_with_pager_left)
            return
        }
        cl_content?.background = ContextCompat.getDrawable(context!!, R.drawable.bg_with_pager_right)
    }


}
