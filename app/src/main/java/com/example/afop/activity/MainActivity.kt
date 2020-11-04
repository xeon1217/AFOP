package com.example.afop.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.afop.R
import com.example.afop.data.dataSource.DataSource
import com.example.afop.databinding.ActivityMainBinding
import com.example.afop.ui.main.community.CommunityFragment
import com.example.afop.ui.main.home.HomeFragment
import com.example.afop.ui.main.infomation.InformationFragment
import com.example.afop.ui.main.market.MarketFragment
import com.example.afop.ui.main.market.marketList.MarketListFragment
import com.example.afop.ui.main.member.MemberFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

/**
 * 메인 관련 액티비티
 * LoginActivity에서 로그인, 초기화를 마친 후 메인 액티비티가 됨
 */
class MainActivity : MyActivity() {
    private var lastTimeBackPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_main)

        var binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (savedInstanceState == null) {
            switchFragment(HomeFragment.newInstance())
        }
        initToolbar()

        mainBottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuItemHome -> {
                    switchFragment(HomeFragment.newInstance())
                } // 홈
                R.id.menuItemCommunity -> {
                    switchFragment(CommunityFragment.newInstance())
                } // 커뮤니티
                R.id.menuItemMarket -> {
                    switchFragment(MarketFragment.newInstance())
                } // 매칭
                R.id.menuItemInformation -> {
                    switchFragment(InformationFragment.newInstance())
                } // 혼족 정보
                R.id.menuItemPreferences -> {
                    switchFragment(MemberFragment.newInstance())
                }
            }
            true
        }
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