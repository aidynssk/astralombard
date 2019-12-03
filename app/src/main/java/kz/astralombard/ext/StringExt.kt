package kz.astralombard.ext

import java.text.SimpleDateFormat
import java.util.*

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