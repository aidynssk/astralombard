package kz.astralombard.home.menu.about.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kz.astralombard.R
import kz.astralombard.base.BaseFragment

/**
 * Created by wokrey@gmail.com on 7/2/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class AboutCompanyFragment: BaseFragment() {

    companion object{
        const val TAG = "AboutCompanyFragment"
        fun newInstance() = AboutCompanyFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_company, container, false)
    }
}