package kz.astralombard.home.menu.myloans.presentation

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.home.menu.myloans.history.HistoryFragment
import kz.astralombard.home.menu.myloans.open.OpenLoansFragment

/**
 * Created by wokrey@gmail.com on 7/12/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
private const val LOANS_PAGES_COUNT = 2
class MyLoanPagerAdapter(
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): BaseFragment {
        return when (position) {
            0 ->
                OpenLoansFragment.newInstance()
            else ->
                HistoryFragment.newInstance()

        }

    }
    override fun getCount() = LOANS_PAGES_COUNT
}