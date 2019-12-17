package kz.astralombard.code

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import kz.astralombard.R
import kz.astralombard.base.SharedPrefKeys
import kz.astralombard.base.ui.BaseActivity
import kz.astralombard.databinding.DialogSetPinBinding
import kz.astralombard.ext.hide
import kz.astralombard.ext.show
import org.koin.android.ext.android.get
import java.util.*

/**
 * Created by wokrey@gmail.com on 05.12.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class SetPinBottomDialog : BasePinFragmentDialog(), View.OnClickListener {

    private lateinit var binding: DialogSetPinBinding
    private val shared = get<SharedPreferences>()
    private val codeStack = Stack<String>()
    private var code = StringBuilder()
    private var confirmCode = StringBuilder()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_set_pin, container, false)
        isCancelable = false
        initListeners()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        codeStack.forEachIndexed { index, s ->
            setDigitsBackground(true, index)
        }
    }

    override fun onClick(v: View?) {
        v ?: return

        if (v.id == R.id.rb_delete && codeStack.isNotEmpty()) {
            codeStack.pop()
            setDigitsBackground(false, codeStack.size)
            return
        }
        if (codeStack.size == 4) return

        val pressedDigit = when (v.id) {
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
        setDigitsBackground(true, codeStack.size)
        codeStack.push(pressedDigit)
    }

    private fun initListeners() {
        with(binding) {
            rb0.setOnClickListener(this@SetPinBottomDialog)
            rb1.setOnClickListener(this@SetPinBottomDialog)
            rb2.setOnClickListener(this@SetPinBottomDialog)
            rb3.setOnClickListener(this@SetPinBottomDialog)
            rb4.setOnClickListener(this@SetPinBottomDialog)
            rb5.setOnClickListener(this@SetPinBottomDialog)
            rb6.setOnClickListener(this@SetPinBottomDialog)
            rb7.setOnClickListener(this@SetPinBottomDialog)
            rb8.setOnClickListener(this@SetPinBottomDialog)
            rb9.setOnClickListener(this@SetPinBottomDialog)
            rbDelete.setOnClickListener(this@SetPinBottomDialog)
        }
        binding.btnSave.setOnClickListener {
            if (codeStack.size == 4) {
                codeStack.toList().forEach {
                    code.append(it)
                }
                binding.btnConfirm.show()
                binding.btnSave.visibility = View.INVISIBLE
                binding.tvEnter.text = getString(R.string.profile_confirm_new_pin)
                codeStack.clear()
                clearDigitsBackground()
            }
        }
        binding.btnConfirm.setOnClickListener {
            if (codeStack.size == 4) {
                codeStack.toList().forEach {
                    confirmCode.append(it)
                }

                if (code.toString() == confirmCode.toString()) {
                    shared.edit()
                        .putString(SharedPrefKeys.SAVED_PIN_CODE, code.toString())
                        .apply()
                    dismiss()
                } else {
                    (activity as BaseActivity).showErrorAlert(message = getString(R.string.error_confirm_code_text))
                    binding.btnConfirm.hide()
                    binding.btnSave.show()
                    confirmCode.clear()
                    code.clear()
                    codeStack.clear()
                    clearDigitsBackground()
                    binding.tvEnter.text = getString(R.string.profile_set_new_pin)
                }

            }
        }
    }

    private fun clearDigitsBackground() {
        binding.firstDigit.isPressed = false
        binding.secondDigit.isPressed = false
        binding.thirdDigit.isPressed = false
        binding.fourthDigit.isPressed = false
    }

    private fun setDigitsBackground(makeFilled: Boolean, btnNum: Int) = when (btnNum) {
        0 -> binding.firstDigit.isPressed = makeFilled
        1 -> binding.secondDigit.isPressed = makeFilled
        2 -> binding.thirdDigit.isPressed = makeFilled
        3 -> binding.fourthDigit.isPressed = makeFilled
        else -> {
        }
    }
}