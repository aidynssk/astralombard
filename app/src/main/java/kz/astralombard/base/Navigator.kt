package kz.astralombard.base

import androidx.fragment.app.Fragment

/**
 * Created by wokrey@gmail.com on 27.11.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
interface Navigator {
    fun addFragment(fragment: Fragment)
    fun replaceFragment(fragment: Fragment)
    fun popFragment()
}