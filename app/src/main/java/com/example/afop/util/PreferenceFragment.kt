package com.example.afop.util

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.afop.R
import com.example.afop.ui.activity.LoginActivity
import com.example.afop.data.dataSource.DataSource

class PreferenceFragment : PreferenceFragmentCompat() {
    private lateinit var mActivity: ActivityExtendFunction
    private lateinit var dataSource: DataSource

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as ActivityExtendFunction
        mActivity.changeTitle("사용자 이름")
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        dataSource = DataSource()

        if(DataSource.isLogin()) { //로그인이 되어있을 경우 보여줄 메뉴
            addPreferencesFromResource(R.xml.preferences_after_login)
            createLogout()
        } else { //로그인이 되어있지 않은 경우 보여줄 메뉴
            addPreferencesFromResource(R.xml.preferences_before_login)
            createLogin()
        }
    }

    private fun createLogin() {
        val loginPreference: Preference? = findPreference("login")
        loginPreference?.setOnPreferenceClickListener {
            it.isVisible = true
            mActivity.startActivity(Intent(mActivity, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            true
        }
    }

    private fun createLogout() {
        val logoutPreference: Preference? = findPreference("logout")
        logoutPreference?.setOnPreferenceClickListener {
            it.isVisible = true
            AlertDialog.Builder(mActivity).apply {
                setCancelable(false)
                setTitle(getString(R.string.dialog_title_logout))
                setMessage(getString(R.string.dialog_message_logout))
                setPositiveButton(getString(R.string.action_yes)) { _, _ ->
                    DataSource.logout()
                    mActivity.startActivity(Intent(mActivity, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                }
                setNegativeButton(getString(R.string.action_no)) {_, _->
                }
            }.show()
            true
        }
    }

    companion object {
        fun newInstance() =
            PreferenceFragment().apply {
                arguments = Bundle().apply {
                }
            }

    }
}