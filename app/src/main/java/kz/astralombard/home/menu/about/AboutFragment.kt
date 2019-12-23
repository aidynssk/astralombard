package kz.astralombard.home.menu.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_about.*

import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.home.menu.about.company.AboutCompanyFragment
import kz.astralombard.home.menu.about.news.NewsFragment
import kz.astralombard.home.menu.about.writeus.ui.WriteUsFragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    override fun onResume() {
        super.onResume()
        (activity as? HomeActivity?)?.setCurrentMenuItem(R.id.nav_about_company)
    }
   private fun initListeners(){
       cl_about?.setOnClickListener {
           addFragment(AboutCompanyFragment.newInstance())
       }
       cl_news?.setOnClickListener {
           addFragment(NewsFragment.newInstance())
       }
       cl_write?.setOnClickListener {
           addFragment(WriteUsFragment.newInstance())
       }
   }
}
