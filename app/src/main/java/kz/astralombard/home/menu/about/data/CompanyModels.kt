package kz.astralombard.home.menu.about.data

/**
 * Created by wokrey@gmail.com on 30.11.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
//News
data class News(
    val id: Int,
    val photo: String,
    val text: String
)

//about company
data class AboutCompanyResponse(
    val text: String?
)

//Feedback
data class FeedbackRequest(
    val subject: String,
    val username: String,
    val text: String
)

data class FeedbackResponse(
    val message: String? = null
)