package kz.astralombard.home.presentation

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.annotation.IdRes
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.Menu
import android.view.View
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import kotlinx.android.synthetic.main.activity_home.*
import kz.astralombard.R
import kz.astralombard.base.DataHolder
import kz.astralombard.base.ui.BaseActivity
import kz.astralombard.code.PinManager
import kz.astralombard.home.menu.about.AboutFragment
import kz.astralombard.home.menu.address.presentation.AddressesFragment
import kz.astralombard.home.menu.calculator.CalculatorFragment
import kz.astralombard.home.menu.login.presentation.LoginFragment
import kz.astralombard.home.menu.myloans.presentation.MyLoansFragment
import kz.astralombard.home.menu.profile.ProfileFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by wokrey@gmail.com on 02.06.2019
 */

const val CALCULATOR_FRAGMENT_KEY = "calculator_fragment"
const val ABOUT_FRAGMENT_KEY = "about_fragment"
const val ADDRESSES_FRAGMENT_KEY = "addresses_fragment"
const val LOGIN_FRAGMENT_KEY = "login_fragment"
const val MY_LOANS_FRAGMENT_KEY = "my_loan_fragment"
const val PROFILE_FRAGMENT_KEY = "profile_fragment"

class HomeActivity : BaseActivity() {

    private val viewModel: HomeViewModel by viewModel()

    private lateinit var navigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener
    private var currentTag: String = CALCULATOR_FRAGMENT_KEY

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_on_right)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initNavListener()
        initObservers()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount < 2) {
            finish()
        }else{
            supportFragmentManager.popBackStack()
        }
    }

    private fun initObservers() {
        viewModel.getUserLoggedLD().observe(this, Observer { isLogged ->
            isLogged ?: return@Observer

            if (isLogged) {
                main_navigation.menu.findItem(R.id.nav_my_loans).isVisible = true
                main_navigation.menu.removeItem(R.id.nav_login)
                main_navigation.menu
                    .add(Menu.NONE, R.id.nav_profile, 5, R.string.profile)
                    .setIcon(R.drawable.ic_profile_black_24dp)
                supportFragmentManager.findFragmentByTag(LOGIN_FRAGMENT_KEY)?.onDestroy()

                main_navigation.selectedItemId = R.id.nav_my_loans
                return@Observer
            }

            main_navigation.menu.findItem(R.id.nav_my_loans)?.isVisible = false
            main_navigation.menu.removeItem(R.id.nav_profile)
            if (main_navigation.menu.findItem(R.id.nav_login) == null) {
                main_navigation.menu
                    .add(Menu.NONE, R.id.nav_login, 4, R.string.login)
                    .setChecked(true)
                    .setIcon(R.drawable.ic_login_black_24dp)
            }
            supportFragmentManager.findFragmentByTag(ProfileFragment.TAG)?.onDestroy()
            main_navigation.selectedItemId = R.id.nav_calculator

        })

        viewModel.userHasPinLD.observe(this, Observer {
            it ?: return@Observer
            if (DataHolder.languageWasChange) {
                PinManager.showPinReader(supportFragmentManager)
                DataHolder.languageWasChange = false
                return@Observer
            }
            if (it)
                PinManager.showPinReader(supportFragmentManager)
            else
                PinManager.showPinSetter(supportFragmentManager)
        })
    }

    private fun initNavListener() {
        val menuView = main_navigation?.getChildAt(0) as BottomNavigationMenuView?

        for (i in 0 until menuView!!.childCount) {
            val item = menuView.getChildAt(i) as BottomNavigationItemView
            val activeLabel: View = item.findViewById(R.id.largeLabel)
            if (activeLabel is TextView) {
                activeLabel.setPadding(0, 0, 0, 0)
            }
        }
        navigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var selectedFragment = supportFragmentManager.findFragmentByTag(getTag(item.itemId))

            if (item.itemId == main_navigation.selectedItemId && selectedFragment != null)
                return@OnNavigationItemSelectedListener false

            onBottomNavigationSelected(id = item.itemId)

            if (selectedFragment == null) {
                selectedFragment = when (currentTag) {
                    MY_LOANS_FRAGMENT_KEY -> MyLoansFragment.newInstance()
                    CALCULATOR_FRAGMENT_KEY -> CalculatorFragment.newInstance()
                    ABOUT_FRAGMENT_KEY -> AboutFragment.newInstance()
                    ADDRESSES_FRAGMENT_KEY -> AddressesFragment.newInstance()
                    LOGIN_FRAGMENT_KEY -> LoginFragment.newInstance()
                    else -> ProfileFragment.newInstance()
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.current_menu_container, selectedFragment, currentTag)
                .addToBackStack(currentTag)
                .commit()

            title = currentTag

            return@OnNavigationItemSelectedListener true
        }

        main_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
    }

    private fun onBottomNavigationSelected(@IdRes id: Int) {
        val selectedTag = getTag(id)
        if (selectedTag == currentTag) {

            return
        }

        currentTag = selectedTag
    }

    private fun getTag(@IdRes id: Int): String = when (id) {
        R.id.nav_my_loans -> MY_LOANS_FRAGMENT_KEY
        R.id.nav_calculator -> CALCULATOR_FRAGMENT_KEY
        R.id.nav_about_company -> ABOUT_FRAGMENT_KEY
        R.id.nav_addresses -> ADDRESSES_FRAGMENT_KEY
        R.id.nav_profile -> PROFILE_FRAGMENT_KEY
        else -> LOGIN_FRAGMENT_KEY
    }

    fun setCurrentMenuItem(@IdRes id: Int){
        main_navigation.menu.findItem(id)?.setChecked(true)
    }

}
