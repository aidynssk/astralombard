package kz.astralombard.home.menu.about.writeus.data

import kz.astralombard.base.data.ApiService
import kz.astralombard.base.data.BaseRepository
import kz.astralombard.base.data.Response

/**
 * Created by wokrey@gmail.com on 30.11.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

interface IWriteUsRepository{
    suspend fun leaveFeedback(request: FeedbackRequest): Response<FeedbackResponse>
}
class WriteUsRepository(
    private val apiService: ApiService
): BaseRepository(), IWriteUsRepository {

    override suspend fun leaveFeedback(request: FeedbackRequest)
            = makeApiRequest { apiService.leaveFeedback(request) }
}