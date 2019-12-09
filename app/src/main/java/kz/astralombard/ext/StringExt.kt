package kz.astralombard.ext

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by wokrey@gmail.com on 02.12.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

fun String.toDate(format: String): Date {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.parse(this)
}
fun Date.toString(format: String): String =
    SimpleDateFormat(format, Locale.getDefault()).format(this)

fun Date.differenceInDays(secondDate: Date): Int =
    TimeUnit.DAYS.convert(this.differenceInMillis(secondDate), TimeUnit.MILLISECONDS).toInt()

fun Date.differenceInMillis(secondDate: Date) =
    secondDate.setToMidnight.time - this.setToMidnight.time

inline fun <reified T : kotlin.Enum<T>> safeValueOf(type: String?): T? {
    return java.lang.Enum.valueOf(T::class.java, type)
}

val Date.setToMidnight: Date
    get() = Calendar.getInstance().apply {
        time = this@setToMidnight
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time