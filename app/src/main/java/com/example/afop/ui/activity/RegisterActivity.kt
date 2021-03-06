package com.example.afop.ui.activity

import android.os.Bundle
import android.view.MenuItem
import com.example.afop.R
import com.example.afop.ui.auth.register.RegisterFragment
import com.example.afop.util.ActivityExtendFunction
import kotlinx.android.synthetic.main.activity_register.*

/**
 * 회원가입 관련 액티비티
 */
class RegisterActivity : ActivityExtendFunction() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        if (savedInstanceState == null) {
            switchFragment(RegisterFragment.newInstance())
        }
        initToolbar()
    }

    //툴바 관련
    private fun initToolbar() {
        setSupportActionBar(registerToolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}