package kz.astralombard.home.menu.profile


import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.fragment_code.*

import kz.astralombard.R
import kz.astralombard.base.SharedPrefKeys
import kz.astralombard.base.ui.BaseFragment
import kz.astralombard.databinding.FragmentCodeBinding
import kz.astralombard.home.presentation.HomeActivity
import org.koin.android.ext.android.get
import java.util.*


class CodeFragment : BaseFragment(), View.OnClickListener {

    companion object{
        const val TAG = "CodeFragment"

        fun newInstance() = CodeFragment()
    }
    private val shared = get<SharedPreferences>()
    private val codeStack = Stack<String>()
    private lateinit var binding: FragmentCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_code, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    override fun onClick(v: View?) {
        v ?: return

        if (v.id == R.id.rb_delete && codeStack.isNotEmpty()){
            codeStack.pop()
            setDigitsBackground(false)
            return
        }
        if (codeStack.size == 4) return

        val pressedDigit = when(v.id){
            R.id.rb_0 -> "0"
            R.id.rb_1 -> "1"
            R.id.rb_2 -> "2"
            R.id.rb_3 -> "3"
            R.id.rb_4 -> "4"
            R.id.rb_5 -> "5"
            R.id.rb_6 -> "6"
            R.id.rb_7 -> "7"
            R.id.rb_8 -> "8"
            R.id.rb_9 -> "9"
            else -> return
        }
        setDigitsBackground(true)
        codeStack.push(pressedDigit)
    }

    private fun initListeners(){
        with(binding){
            rb0.setOnClickListener(this@CodeFragment)
            rb1.setOnClickListener(this@CodeFragment)
            rb2.setOnClickListener(this@CodeFragment)
            rb3.setOnClickListener(this@CodeFragment)
            rb4.setOnClickListener(this@CodeFragment)
            rb5.setOnClickListener(this@CodeFragment)
            rb6.setOnClickListener(this@CodeFragment)
            rb7.setOnClickListener(this@CodeFragment)
            rb8.setOnClickListener(this@CodeFragment)
            rb9.setOnClickListener(this@CodeFragment)
            rbDelete.setOnClickListener(this@CodeFragment)
        }

        binding.btnSave.setOnClickListener {
            if (codeStack.size == 4){
                val code = StringBuilder().apply {
                    codeStack.toList().forEach { append(it) }
                }

                shared.edit()
                    .putString(SharedPrefKeys.SAVED_PIN_CODE, code.toString())
                    .apply()
                showAlert(message = getString(R.string.profile_success_change_code_text), ok = {(activity as? HomeActivity)?.onBackPressed()})
            }
        }
    }

    private fun setDigitsBackground(makeFilled: Boolean) = when(codeStack.size){
        0 -> binding.firstDigit.isPressed = makeFilled
        1 -> binding.secondDigit.isPressed = makeFilled
        2 -> binding.thirdDigit.isPressed = makeFilled
        3 -> binding.fourthDigit.isPressed = makeFilled
        else ->{}
    }
}
