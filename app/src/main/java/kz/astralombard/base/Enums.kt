package kz.astralombard.base

/**
 * Created by wokrey@gmail.com on 28.11.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

enum class PermisionStatus(
    val value: Int
) {
    NOT_ASKED(0),
    ALLOWED(1),
    DENIED(-1)
}

enum class WriteUsError{
    EMPTY_TEXT,
    EMPTY_SUBJECT,
    EMPTY_PHONE
}

const val RUSSIAN_VALUE = "ru"
const val KAZAKH_VALUE = "kz"