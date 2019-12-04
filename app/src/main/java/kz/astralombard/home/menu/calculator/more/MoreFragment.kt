package kz.astralombard.home.menu.calculator.more


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import kotlinx.android.synthetic.main.fragment_more.*

import kz.astralombard.R
import kz.astralombard.base.ui.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class MoreFragment : BaseFragment() {

    companion object {
        const val MORE_GOLD = 0
        const val MORE_CAR = 1
        const val MORE_TYPE = "more_type"
        private const val BOLD_TEXT = "<b>%s</b>"

        fun newInstance(type: Int) = MoreFragment().apply {
            arguments = Bundle().apply {
                putInt(MORE_TYPE, type)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            if (it.getInt(MORE_TYPE) == MORE_GOLD){
                tv_more.text = requireContext().getText(R.string.calculator_more_gold_text)
            } else {
                tv_more_title.text = requireContext().getString(R.string.calculator_more_auto_title)
                val with = requireContext().getString(R.string.calculator_more_auto_with_right)
                val without = requireContext().getString(R.string.calculator_more_auto_without_right)
                /*val autoText = String.format(
                    requireContext().getString(R.string.calculator_more_auto_text).toString(),
                    String.format(BOLD_TEXT, with),
                    String.format(BOLD_TEXT, without)
                )*/
                val autoText = requireContext().getString(R.string.calculator_more_auto_text)
//                tv_more.text =HtmlCompat.fromHtml(autoText, HtmlCompat.FROM_HTML_MODE_COMPACT)
                tv_more.text =autoText

            }
        }

        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }
    }


}
