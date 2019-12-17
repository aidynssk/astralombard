package kz.astralombard.code

import android.content.Context.VIBRATOR_SERVICE
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kz.astralombard.R
import kz.astralombard.base.Constants
import kz.astralombard.base.SharedPrefKeys
import kz.astralombard.databinding.DialogPinValidatorBinding
import org.koin.android.ext.android.get
import java.util.*

/**
 * Created by wokrey@gmail.com on 08.12.2019.
 * It's not wokrey, if the code smells bad. Somebody set me up.
 */
class PinValidatorBottomDialog : BasePinFragmentDialog(), View.OnClickListener {

    private lateinit var binding: DialogPinValidatorBinding
    private val shared = get<SharedPreferences>()
    private val codeStack = Stack<String>()
    private var code = StringBuilder()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_pin_validator, container, false)
        isCancelable = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    override fun onClick(v: View?) {
        v ?: return

        if (v.id == R.id.rb_delete && codeStack.isNotEmpty()) {
            codeStack.pop()
            setDigitsBackground(false)
            return
        }

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
        setDigitsBackground(true)
        codeStack.push(pressedDigit)
        if (codeStack.size == 4){
            codeStack.toList().forEach {
                code.append(it)
            }
            if (code.toString() != getPin()){
                callWrongPinAction()
            } else {
                dismiss()
            }
            return
        }
    }

    private fun initListeners() {
        with(binding) {
            rb0.setOnClickListener(this@PinValidatorBottomDialog)
            rb1.setOnClickListener(this@PinValidatorBottomDialog)
            rb2.setOnClickListener(this@PinValidatorBottomDialog)
            rb3.setOnClickListener(this@PinValidatorBottomDialog)
            rb4.setOnClickListener(this@PinValidatorBottomDialog)
            rb5.setOnClickListener(this@PinValidatorBottomDialog)
            rb6.setOnClickListener(this@PinValidatorBottomDialog)
            rb7.setOnClickListener(this@PinValidatorBottomDialog)
            rb8.setOnClickListener(this@PinValidatorBottomDialog)
            rb9.setOnClickListener(this@PinValidatorBottomDialog)
            rbDelete.setOnClickListener(this@PinValidatorBottomDialog)
        }
    }

    private fun clearDigitsBackground() {
        binding.firstDigit.isPressed = false
        binding.secondDigit.isPressed = false
        binding.thirdDigit.isPressed = false
        binding.fourthDigit.isPressed = false
    }

    private fun setDigitsBackground(makeFilled: Boolean) = when (codeStack.size) {
        0 -> binding.firstDigit.isPressed = makeFilled
        1 -> binding.secondDigit.isPressed = makeFilled
        2 -> binding.thirdDigit.isPressed = makeFilled
        3 -> binding.fourthDigit.isPressed = makeFilled
        else -> {
        }
    }

    private fun callWrongPinAction(){
        codeStack.clear()
        code.clear()
        vibrate()
        clearDigitsBackground()
    }

    private fun vibrate(){
        val vibrator = context?.getSystemService(VIBRATOR_SERVICE) as Vibrator?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator?.vibrate(200)
        }
    }

    private fun getPin() = shared.getString(SharedPrefKeys.SAVED_PIN_CODE, Constants.DEFAULT_STRING)!!
}