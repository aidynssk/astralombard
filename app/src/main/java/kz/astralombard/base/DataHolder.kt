package kz.astralombard.base

import org.koin.core.KoinComponent

/**
 * Created by wokrey@gmail.com on 7/16/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
object DataHolder : KoinComponent {

    var token: String? = null
        get() = if (!field.isNullOrBlank())
            Constants.TOKEN_PREFIX + field
        else
            null
}