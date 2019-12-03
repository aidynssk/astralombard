package kz.astralombard.home.menu.myloans.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kz.astralombard.ext.toDate
import kz.astralombard.ext.toString
import java.util.*

/**
 * Created by wokrey@gmail.com on 01.12.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
data class MyLoan(
    val Id: String,
    val Number: String,
    val Items: List<Item>?
)
@Parcelize
data class Item(
    val Id: String,
    val Type: String,
    val Sample: String,
    val PaidFor: Boolean,
    val Name: String,
    val Weight: Double,
    val GramPrice: Double,
    val Period: Int,
    val GuaranteePeriod: String,
    val HandAmount: Int,
    val Office: String,
    val Merchant: String,
    val PayAmount: Int,
    val IdTicket: String,
    val NumberTicket: String
): Parcelable {
    var LoanDate: String = ""
        get() = field.toDate("yyyy-MM-dd").toString("yyyy-MM-dd")

    var ClosingDate: String = ""
        get() = field.toDate("yyyy-MM-dd").toString("yyyy-MM-dd")
}