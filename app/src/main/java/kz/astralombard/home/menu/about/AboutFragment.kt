package kz.astralombard.home.menu.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_about.*

import kz.astralombard.R
import kz.astralombard.base.BaseFragment
import kz.astralombard.home.menu.about.company.AboutCompanyFragment
import kz.astralombard.home.menu.about.news.NewsFragment
import kz.astralombard.home.menu.about.writeus.WriteUsFragment
import kz.astralombard.home.presentation.HomeActivity


class AboutFragment : BaseFragment() {

    companion object {
        const val TAG = "AboutFragment"
        fun newInstance() = AboutFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onStart() {
        super.onStart()
        initListeners()
    }

   private fun initListeners(){
       cl_about?.setOnClickListener {
           replaceFragment(AboutCompanyFragment.newInstance(), AboutCompanyFragment.TAG)
       }
       cl_news?.setOnClickListener {
           replaceFragment(NewsFragment.newInstance(), NewsFragment.TAG)
       }
       cl_write?.setOnClickListener {
           replaceFragment(WriteUsFragment.newInstance(), WriteUsFragment.TAG)
       }
   }

    private fun replaceFragment(fragment: BaseFragment, tag: String){
        (activity as HomeActivity?)?.replaceFragment(
            id = R.id.current_menu_container,
            f = fragment,
            tag = tag
        )
    }
}
