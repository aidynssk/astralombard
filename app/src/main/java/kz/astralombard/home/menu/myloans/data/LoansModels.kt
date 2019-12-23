package kz.astralombard.home.menu.myloans.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kz.astralombard.base.Constants
import kz.astralombard.base.DataHolder
import kz.astralombard.ext.differenceInDays
import kz.astralombard.ext.toDate
import kz.astralombard.ext.toString
import java.util.*
import kotlin.math.abs

/**
 * Created by wokrey@gmail.com on 01.12.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
@Parcelize
data class MyLoan(
    val Id: String,
    val Number: String,
    var Items: List<Item>?
) : Parcelable {
    var item: Item? = null
        get() = Items?.first()

    var totalPayAmount = 0
    get(){
        var total = 0
        Items?.forEach {
            total += it.PayAmount
        }
        return total
    }

    var totalHandAmount = 0
        get(){
            var total = 0
            Items?.forEach {
                total += it.HandAmount
            }
            return total
        }

    var hasExtraInfornmation = false
        get() = Items!!.size > 1

    var isRu: Boolean = DataHolder.currentLang == "ru"
}

data class DetailsHeaderModel(
    val index: Int
) : DetailableItem

//marker
interface DetailableItem

@Parcelize
data class Item(
    val Id: String,
    val Type: String,
    val Sample: String,
    val PaidFor: Boolean,
    val Name: String,
    val Weight: Double,
    val WeightWithoutStone: Double,
    val GramPrice: String,
    val Period: Int,
    val HandAmount: Int,
    val Office: String,
    val Merchant: String,
    val PayAmount: Int,
    val IdTicket: String,
    val NumberTicket: String,
    val LoanDate: String,
    val GuaranteePeriod: String


) : Parcelable, DetailableItem {
    fun getFormattedLoanDate(): String =
        if (LoanDate.isNotBlank()) LoanDate.toDate(Constants.TIME_FORMAT).toString(Constants.YYYY_DD_MM)
        else LoanDate

    var ClosingDate: String = ""
        get() = field.toDate(Constants.TIME_FORMAT).toString(Constants.YYYY_DD_MM)


    fun getFormattedGuaranteePreiod(): String =
        if (GuaranteePeriod.isNotBlank()) GuaranteePeriod.toDate(Constants.TIME_FORMAT).toString(
            Constants.YYYY_DD_MM
        )
        else GuaranteePeriod


    var leftDays: Int = 0
        get() {
            val diff = Date().differenceInDays(GuaranteePeriod.toDate(Constants.YYYY_DD_MM))
            return if (diff < 0)
                abs(diff)
            else
                diff
        }

    var isExpired = false
    get() = Date().differenceInDays(GuaranteePeriod.toDate(Constants.YYYY_DD_MM)) < 0
}
//Prolongate
data class ProlongateResponse(
    val Id: String,
    val Number: String,
    val Calculation: Calculation
)

data class Calculation(
    val CreditAmount: String,
    val PercentAmount: String,
    val PercentDays: String,
    val PercentDaysAmount: String,
    val FineAmount: String,
    val FineDays: String,
    val FineDaysAmount: String,
    val TotalAmount: String
)

data class ProlongateRequest(
    val number: String,
    val username: String,
    val password: String,
    val lang: String
)