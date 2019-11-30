package kz.astralombard.home.menu.address.model

import com.google.gson.annotations.SerializedName

/**
 * Created by wokrey@gmail.com on 26.11.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

data class AddressesResponse(
    val message: String,
    val data: List<Point>
)

data class Point(
    val address: String,
    val phone1: String,
    val phone2: String,
    val phone3: String,
    val distance: String,
    @SerializedName("open_time")
    val openTime: String,
    @SerializedName("close_time")
    val closeTime: String,
    val city: String
)