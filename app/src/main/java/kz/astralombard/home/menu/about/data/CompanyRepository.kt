package kz.astralombard.home.menu.about.data

import kz.astralombard.base.data.ApiService
import kz.astralombard.base.data.BaseRepository
import kz.astralombard.base.data.Response

/**
 * Created by wokrey@gmail.com on 30.11.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

interface ICompanyRepository{
    suspend fun leaveFeedback(request: FeedbackRequest): Response<FeedbackResponse>
    suspend fun getAboutCompanyText(): Response<AboutCompanyResponse>
    suspend fun getNews(): Response<List<News>>
}
class CompanyRepository(
    private val apiService: ApiService
): BaseRepository(), ICompanyRepository {

    override suspend fun leaveFeedback(request: FeedbackRequest)
            = makeApiRequest { apiService.leaveFeedback(request) }

    override suspend fun getAboutCompanyText()
            = makeApiRequest{ apiService.getAbout() }

    override suspend fun getNews()
            = makeApiRequest { apiService.getNews() }
}