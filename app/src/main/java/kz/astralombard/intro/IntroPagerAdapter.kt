package kz.astralombard.intro

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Created by wokrey@gmail.com on 7/16/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

const val INTRO_PAGES_COUNT = 3

class IntroPagerAdapter(
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int) = when (position) {
        0 ->
            FirstFragment.newInstance()
        1 ->
            SecondFragment.newInstance()
        else ->
            ThirdFragment.newInstance()
    }


    override fun getCount() = INTRO_PAGES_COUNT
}