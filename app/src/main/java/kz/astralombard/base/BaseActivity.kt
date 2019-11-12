package kz.astralombard.base

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.widget.ProgressBar
import kz.astralombard.R

abstract class BaseActivity: AppCompatActivity() {


    fun showProgress(){
        val progressDialog = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal)

    }

}