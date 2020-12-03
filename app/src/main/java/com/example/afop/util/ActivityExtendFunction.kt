package com.example.afop.util

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_login.container
import kotlinx.android.synthetic.main.activity_login.progressBackground
import kotlinx.android.synthetic.main.activity_login.progressBar

/**
 * 액티비티들의 공통 클래스
 * 공통된 동작을 상속하기위해 사용됨
 */
open class ActivityExtendFunction : AppCompatActivity() {
    enum class ActivityType(val type: String) {
        LOGIN("LOGIN"),
        MARKET("MARKET"),
        CREATE("CREATE"), //생성
        READ("READ"), //읽기
        UPDATE("UPDATE") //수정
    }

    fun switchFragment(fragment: Fragment, bundle: Bundle? = null) {
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(this.container.id, fragment)
            .commit()
    }

    fun initToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }
    }

    fun showLoading() {
        this.progressBar.visibility = View.VISIBLE
        disableTouch()
    }

    fun hideLoading() {
        this.progressBar.visibility = View.GONE
        ableTouch()
    }

    fun disableTouch() {
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

    fun ableTouch() {
        this.progressBackground.visibility = View.GONE
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }

    fun changeTitle(_title: Int) {
        supportActionBar?.apply {
            title = getString(_title)
        }
    }

    fun changeTitle(_title: String?) {
        supportActionBar?.apply {
            title = _title
        }
    }
}