package com.example.afop.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_login.*

open class MyActivity : AppCompatActivity() {
    fun switchFragment(fragment: Fragment, bundle: Bundle? = null) {
        supportFragmentManager.beginTransaction()
            .replace(this.container.id, fragment)
            .commitNow()
    }

    fun showLoding() {
        this.progressDialog.visibility = View.VISIBLE
        this.progressBackground.visibility = View.VISIBLE
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        )
    }

    fun hideLoding() {
        this.progressDialog.visibility = View.GONE
        this.progressBackground.visibility = View.GONE
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }

    fun changeTitle(_title: Int) {
        supportActionBar?.apply {
            title = getString(_title)
        }
    }
}