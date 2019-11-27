package kz.astralombard.home.menu.address.model

import com.google.gson.annotations.SerializedName

/**
 * Created by wokrey@gmail.com on 26.11.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

data class Point(
    val address: String,
    @SerializedName("phone_1")
    val phone1: String,
    @SerializedName("phone_2")
    val phone2: String,
    @SerializedName("phone_3")
    val phone3: String
)