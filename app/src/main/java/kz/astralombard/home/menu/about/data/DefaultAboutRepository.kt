package kz.astralombard.home.menu.about.data

import kz.astralombard.base.data.ApiService
import kz.astralombard.base.data.BaseRepository

/**
 * Created by wokrey@gmail.com on 7/15/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class DefaultAboutRepository(
    private val api: ApiService
): BaseRepository(), AboutRepository {
}