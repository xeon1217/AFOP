package com.example.afop.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.afop.R
import com.example.afop.data.dataSource.DataSource
import com.example.afop.databinding.ActivityMainBinding
import com.example.afop.service.ForcedTerminationService
import com.example.afop.ui.main.MainCommunityFragment
import com.example.afop.ui.main.MainHomeFragment
import com.example.afop.ui.main.MainInformationFragment
import com.example.afop.ui.main.MainMarketFragment
import com.example.afop.ui.main.market.marketList.MarketListFragment
import com.example.afop.util.PreferenceFragment
import com.example.afop.util.ActivityExtendFunction
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

/**
 * 메인 관련 액티비티
 * LoginActivity에서 로그인, 초기화를 마친 후 메인 액티비티가 됨
 */
class MainActivity : ActivityExtendFunction() {
    private var lastTimeBackPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initApp()

        //setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    1);
            }
        }

        var binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (savedInstanceState == null) {
            switchFragment(MainHomeFragment.newInstance())
        }
        initToolbar()

        mainBottomNavigationView.setOnNavigationItemSelectedListener { item ->
            if (mainBottomNavigationView.selectedItemId != item.itemId) {
                when (item.itemId) {
                    R.id.menuItemHome -> {
                        switchFragment(MainHomeFragment.newInstance())
                    } // 홈
                    R.id.menuItemMeeting -> {
                        switchFragment(MainInformationFragment.newInstance())
                    } // 모임
                    R.id.menuItemMarket -> {
                        switchFragment(MainMarketFragment.newInstance())
                    } // 중고마켓
                    R.id.menuItemCommunity -> {
                        switchFragment(MainCommunityFragment.newInstance())
                    } // 커뮤니티
                    R.id.menuItemPreferences -> {
                        switchFragment(PreferenceFragment.newInstance())
                    }
                }
            }
            true
        }
    }

    fun initApp() {
        DataSource.init(this)
        startService(Intent(this, ForcedTerminationService::class.java))
        if (!DataSource.isLogin() && DataSource.getAutoLogin()) {
            goToLogin(null)
        }
    }

    fun goToLogin(view: View?) {
        startActivity(
            Intent(
                this,
                LoginActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    //툴바 관련
    private fun initToolbar() {
        setSupportActionBar(mainToolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            title = getString(R.string.app_name)
        }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed < 2000) {
            DataSource.exit()
            finishAffinity()
            System.runFinalization()
            exitProcess(0)
        }
        //'뒤로' 버튼 한번 클릭 시 메시지
        Toast.makeText(this, getString(R.string.action_app_exit), Toast.LENGTH_SHORT).show()
        //lastTimeBackPressed에 '뒤로'버튼이 눌린 시간을 기록
        lastTimeBackPressed = System.currentTimeMillis()
        return
    }
}