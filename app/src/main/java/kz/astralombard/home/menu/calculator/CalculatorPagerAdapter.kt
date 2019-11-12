package kz.astralombard.home.menu.calculator

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kz.astralombard.home.menu.calculator.car.CarFragment
import kz.astralombard.home.menu.calculator.gold.GoldFragment

/**
 * Created by wokrey@gmail.com on 7/2/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

private const val CALC_PAGES_COUNT = 2

class CalculatorPagerAdapter(
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int) = when (position) {
        0 ->
            GoldFragment.newInstance()
        else ->
            CarFragment.newInstance()
    }


    override fun getCount() = CALC_PAGES_COUNT
}