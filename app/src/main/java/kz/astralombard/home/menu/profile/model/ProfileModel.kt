package kz.astralombard.home.menu.profile.model

/**
 * Created by wokrey@gmail.com on 02.12.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

data class Profile(
    val iin: String,
    val Phone: String,
    val FullName: String,
    val City: String,
    val Address: String,
    val BirthDate: String
)