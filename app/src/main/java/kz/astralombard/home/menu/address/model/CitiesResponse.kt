package kz.astralombard.home.menu.address.model

/**
 * Created by wokrey@gmail.com on 7/26/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
data class CitiesResponse(
    val message: String?,
    val data: List<City>,
    val status: String
)

data class City(
    val id: Int,
    val name: String
)