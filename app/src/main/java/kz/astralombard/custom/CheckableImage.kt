package kz.astralombard.custom

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable

/**
 * Created by wokrey@gmail.com on 7/14/19.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class CheckableImage(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs), Checkable {

    companion object {
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }

    private var checked = false

    override fun isChecked(): Boolean {
        return checked
    }

    override fun setChecked(b: Boolean) {
        if (b != checked) {
            checked = b
            refreshDrawableState()

        }
    }

    override fun toggle() {
        isChecked = !checked
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            View.mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }

}