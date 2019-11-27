package kz.astralombard.ext

import android.view.View

/**
 * Created by wokrey@gmail.com on 25.11.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */

fun View.hide() {
    visibility = View.GONE
}

fun View.show(){
    visibility = View.VISIBLE
}

fun View.invis(){
    visibility = View.INVISIBLE
}