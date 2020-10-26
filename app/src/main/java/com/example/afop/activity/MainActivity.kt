package com.example.afop.activity

import android.os.Bundle
import android.widget.Toast
import com.example.afop.R
import com.example.afop.data.dataSource.DataSource
import com.example.afop.ui.main.community.CommunityFragment
import com.example.afop.ui.main.home.HomeFragment
import com.example.afop.ui.main.infomation.InformationFragment
import com.example.afop.ui.main.market.marketList.MarketListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : MyActivity() {
    private var lastTimeBackPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
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
                    switchFragment(MarketListFragment.newInstance())
                } // 매칭
                R.id.menuItemInformation -> {
                    switchFragment(InformationFragment.newInstance())
                } // 혼족 정보
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
            if (DataSource.auth.currentUser != null) {
                DataSource.auth.signOut()
            }
            finishAffinity()
            System.runFinalization()
            exitProcess(0)
        }
        //'뒤로' 버튼 한번 클릭 시 메시지
        Toast.makeText(this, getString(R.string.action_exit), Toast.LENGTH_SHORT).show()
        //lastTimeBackPressed에 '뒤로'버튼이 눌린 시간을 기록
        lastTimeBackPressed = System.currentTimeMillis()
        return
    }
}