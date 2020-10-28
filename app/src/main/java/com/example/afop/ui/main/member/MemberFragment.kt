package com.example.afop.ui.main.member

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.afop.R
import com.example.afop.activity.LoginActivity
import com.example.afop.activity.MainActivity
import com.example.afop.activity.MyActivity
import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.repository.RegisterRepository
import com.example.afop.ui.auth.login.LoginResult

class MemberFragment : PreferenceFragmentCompat() {
    private lateinit var mActivity: MyActivity
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as MyActivity
        mActivity.changeTitle("사용자 이름")
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        val dataSource = DataSource()
        val logoutPreference: Preference? = findPreference("logout")

        logoutPreference?.setOnPreferenceClickListener {
            AlertDialog.Builder(mActivity).apply {
                setCancelable(false)
                setTitle(getString(R.string.dialog_title_logout))
                setMessage(getString(R.string.dialog_message_logout))
                setPositiveButton(getString(R.string.action_yes)) { _, _ ->
                    dataSource.logout()
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
            MemberFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}