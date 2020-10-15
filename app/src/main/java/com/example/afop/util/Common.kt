package com.example.afop.Utility

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.core.content.getSystemService
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

const val PARAM_EMAIL = "email"
const val PARAM_NAME = "name"

class Common {
    companion object {
        fun enableBlock(context: Activity) {
            context.progressDialog.visibility = View.VISIBLE
            context.progressBackground.visibility = View.VISIBLE
            context.window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            context.window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            )
        }

        fun disableBlock(context: Activity) {
            context.progressDialog.visibility = View.GONE
            context.progressBackground.visibility = View.GONE
            context.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            context.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        }
    }
}