package com.example.afop.Utility

import android.app.Activity
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_login.*

class UI {
    companion object {
        fun showLoding(context: Activity) {
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

        fun hideLoding(context: Activity) {
            context.progressDialog.visibility = View.GONE
            context.progressBackground.visibility = View.GONE
            context.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            context.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        }
    }
}