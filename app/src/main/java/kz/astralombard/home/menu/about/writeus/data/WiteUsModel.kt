package kz.astralombard.home.menu.about.writeus.data

/**
 * Created by wokrey@gmail.com on 30.11.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

data class FeedbackRequest(
    val subject: String,
    val username: String,
    val text: String
)

data class FeedbackResponse(
    val message: String?
)