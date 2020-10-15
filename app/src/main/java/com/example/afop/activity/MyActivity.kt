package com.example.afop.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.afop.Utility.Common
import kotlinx.android.synthetic.main.activity_login.*

open class MyActivity : AppCompatActivity() {
    fun switchFragment(fragment: Fragment, bundle: Bundle? = null) {
        supportFragmentManager.beginTransaction()
            .replace(this.container.id, fragment)
            .commit()
    }

    fun enableBlock() {
        Common.enableBlock(this)
    }

    fun disableBlock() {
        Common.disableBlock(this)
    }

    fun changeTitle(_title: Int) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(_title)
        }
    }
}