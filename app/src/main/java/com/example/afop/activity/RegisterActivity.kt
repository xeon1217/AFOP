package com.example.afop.activity

import android.os.Bundle
import android.view.MenuItem
import com.example.afop.R
import com.example.afop.ui.auth.register.RegisterFragment
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : MyActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        if (savedInstanceState == null) {
            initToolbar()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RegisterFragment.newInstance())
                .commitNow()
        }
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