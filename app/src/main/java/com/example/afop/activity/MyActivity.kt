package com.example.afop.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 액티비티들의 공통 클래스
 * 공통된 동작을 상속하기위해 사용됨
 */
open class MyActivity : AppCompatActivity() {
    fun switchFragment(fragment: Fragment, bundle: Bundle? = null) {
        supportFragmentManager.beginTransaction()
            .replace(this.container.id, fragment)
            .commit()
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

    fun changeTitle(_title: String) {
        supportActionBar?.apply {
            title = _title
        }
    }
}