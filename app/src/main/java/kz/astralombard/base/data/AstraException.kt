package kz.astralombard.base.data

import kz.astralombard.base.WriteUsError

/**
 * Created by wokrey@gmail.com on 30.11.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class AstraException(
    override val message: String
): Exception()

class AboutException(
    val type: WriteUsError
): Exception()