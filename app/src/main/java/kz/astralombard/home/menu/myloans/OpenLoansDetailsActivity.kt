package kz.astralombard.home.menu.myloans

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil
import kz.astralombard.R
import kz.astralombard.databinding.ActivityOpenLoansDetailsBinding
import kz.astralombard.home.menu.myloans.data.Item

class OpenLoansDetailsActivity : AppCompatActivity() {

    companion object {
        const val LOAN_DETAILS = "loan_details"
    }

    private val loan by lazy {
        intent.getParcelableExtra<Item>(LOAN_DETAILS)
    }
    private lateinit var binding: ActivityOpenLoansDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_open_loans_details)

        showLoans()
    }

    private fun showLoans(){
        with(binding){
            tvOffice.text = loan.Office
            tvSample.text = loan.Sample
            tvMerchandiser.text = loan.Merchant
            tvZalogPeriod.text = loan.GuaranteePeriod
            tvSum.text = loan.PayAmount.toString()
            tvGivenSum.text = loan.HandAmount.toString()
            tvName.text = loan.Name
            tvGramPrice.text = loan.GramPrice.toString()
        }
    }

}
