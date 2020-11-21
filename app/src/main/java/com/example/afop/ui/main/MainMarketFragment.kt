package com.example.afop.ui.main

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.afop.R
import com.example.afop.data.dataSource.DataSource
import com.example.afop.databinding.FragmentMainMarketBinding
import com.example.afop.databinding.FragmentMarketSellBinding
import com.example.afop.ui.activity.ChatActivity
import com.example.afop.ui.activity.MarketActivity
import com.example.afop.util.ActivityExtendFunction
import kotlinx.android.synthetic.main.fragment_main_market.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class MainMarketFragment : Fragment() {
    private lateinit var mActivity: ActivityExtendFunction
    private lateinit var binding: FragmentMainMarketBinding
    private var isOpen: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_market, container, false)
        binding.fragment = this
        mActivity = activity as ActivityExtendFunction
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as ActivityExtendFunction
        mActivity.changeTitle(getString(R.string.title_menu_market))
        initFAB()
    }

    fun isOpen(): Boolean {
        return isOpen
    }

    fun openSell(view: View) {
        hideFAB()
        mActivity.startActivity(Intent(context, MarketActivity::class.java))
    }

    fun openBuyHistory(view: View) {
        hideFAB()
        Toast.makeText(mActivity, getString(R.string.alert_not_implement_function), Toast.LENGTH_SHORT).show()
        mActivity.startActivity(Intent(context, MarketActivity::class.java).putExtra("sell", DataSource.getUser().uid))
    }

    fun openSellHistory(view: View) {
        hideFAB()
        Toast.makeText(mActivity, getString(R.string.alert_not_implement_function), Toast.LENGTH_SHORT).show()
        mActivity.startActivity(Intent(context, MarketActivity::class.java).putExtra("buy", DataSource.getUser().uid))
    }

    fun openChatting(view: View) {
        hideFAB()
        Toast.makeText(mActivity, getString(R.string.alert_not_implement_function), Toast.LENGTH_SHORT).show()
        //mActivity.startActivity(Intent(context, ChatActivity::class.java))
    }

    fun openKeyword(view: View) {
        hideFAB()
        Toast.makeText(mActivity, getString(R.string.alert_not_implement_function), Toast.LENGTH_SHORT).show()
        //mActivity.startActivity(Intent(context, MarketActivity::class.java))
    }

    fun openFavorite(view: View) {
        hideFAB()
        Toast.makeText(mActivity, getString(R.string.alert_not_implement_function), Toast.LENGTH_SHORT).show()
        //mActivity.startActivity(Intent(context, MarketActivity::class.java))
    }

    private fun initFAB() {
        val fabOpen = AnimationUtils.loadAnimation(mActivity, R.anim.fab_open)
        val fabClose = AnimationUtils.loadAnimation(mActivity, R.anim.fab_close)

        mainMarketOpenMenuFloatingButton.setOnClickListener {
            if (!isOpen) {
                showFAB()
            } else {
                hideFAB()
            }
        }
    }

    private fun hideFAB() {
        isOpen = false
        binding.invalidateAll()

        mainMarketFavoriteFloatingButton.hide()
        mainMarketKeywordFloatingButton.hide()
        mainMarketChattingFloatingButton.hide()
        mainMarketSellHistoryFloatingButton.hide()
        mainMarketBuyHistoryFloatingButton.hide()
        mainMarketSellFloatingButton.hide()

        val fabRClockwise = AnimationUtils.loadAnimation(mActivity, R.anim.rotate_clock_wise)
        mainMarketOpenMenuFloatingButton.startAnimation(fabRClockwise)
    }

    private fun showFAB() {
        isOpen = true
        binding.invalidateAll()

        mainMarketSellFloatingButton.show()
        mainMarketBuyHistoryFloatingButton.show()
        mainMarketSellHistoryFloatingButton.show()
        mainMarketChattingFloatingButton.show()
        mainMarketKeywordFloatingButton.show()
        mainMarketFavoriteFloatingButton.show()

        val fabRAntiClockwise = AnimationUtils.loadAnimation(mActivity, R.anim.rotate_anti_clock_wise)
        mainMarketOpenMenuFloatingButton.startAnimation(fabRAntiClockwise)
    }

    /*
    FloatingActionButton mAddFab, mAddAlarmFab, mAddPersonFab;

    // These are taken to make visible and invisible along
    // with FABs
    TextView addAlarmActionText, addPersonActionText;

    // to check whether sub FAB buttons are visible or not.
    Boolean isAllFabsVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Register all the FABs with their IDs
        // This FAB button is the Parent
        mAddFab = findViewById(R.id.add_fab);
        // FAB button
        mAddAlarmFab = findViewById(R.id.add_alarm_fab);
        mAddPersonFab = findViewById(R.id.add_person_fab);

        // Also register the action name text, of all the FABs.
        addAlarmActionText = findViewById(R.id.add_alarm_action_text);
        addPersonActionText = findViewById(R.id.add_person_action_text);

        // Now set all the FABs and all the action name
        // texts as GONE
        mAddAlarmFab.setVisibility(View.GONE);
        mAddPersonFab.setVisibility(View.GONE);
        addAlarmActionText.setVisibility(View.GONE);
        addPersonActionText.setVisibility(View.GONE);

        // make the boolean variable as false, as all the
        // action name texts and all the sub FABs are invisible
        isAllFabsVisible = false;

        // We will make all the FABs and action name texts
        // visible only when Parent FAB button is clicked So
        // we have to handle the Parent FAB button first, by
        // using setOnClickListener you can see below
        mAddFab.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isAllFabsVisible) {

                        // when isAllFabsVisible becomes
                        // true make all the action name
                        // texts and FABs VISIBLE.
                        mAddAlarmFab.show();
                        mAddPersonFab.show();
                        addAlarmActionText.setVisibility(View.VISIBLE);
                        addPersonActionText.setVisibility(View.VISIBLE);

                        // make the boolean variable true as
                        // we have set the sub FABs
                        // visibility to GONE
                        isAllFabsVisible = true;
                    } else {

                        // when isAllFabsVisible becomes
                        // true make all the action name
                        // texts and FABs GONE.
                        mAddAlarmFab.hide();
                        mAddPersonFab.hide();
                        addAlarmActionText.setVisibility(View.GONE);
                        addPersonActionText.setVisibility(View.GONE);

                        // make the boolean variable false
                        // as we have set the sub FABs
                        // visibility to GONE
                        isAllFabsVisible = false;
                    }
                }
            });

        // below is the sample action to handle add person
        // FAB. Here it shows simple Toast msg. The Toast
        // will be shown only when they are visible and only
        // when user clicks on them
        mAddPersonFab.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "Person Added", Toast.LENGTH_SHORT).show();
                }
            });

        // below is the sample action to handle add alarm
        // FAB. Here it shows simple Toast msg The Toast
        // will be shown only when they are visible and only
        // when user clicks on them
        mAddAlarmFab.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "Alarm Added", Toast.LENGTH_SHORT).show();
                }
            });
    }
}
     */
    companion object {
        fun newInstance() =
            MainMarketFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}