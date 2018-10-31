package org.elephant.video.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import org.elephant.video.R
import org.elephant.video.base.BaseActivity
import org.elephant.video.databinding.ActivityMainBinding
import org.elephant.video.ui.fragment.FindTabFragment
import org.elephant.video.ui.fragment.HomeTabFragment
import org.elephant.video.ui.fragment.PersonTabFragment
import org.elephant.video.utils.FragmentManagerUtil

/**
 * @author YangJ
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // data
        val fragments = ArrayList<Fragment>(3)
        fragments.add(HomeTabFragment())
        fragments.add(FindTabFragment())
        fragments.add(PersonTabFragment())
        // view
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val mBottomNavigationBar = binding.bottomNavigationBar
        mBottomNavigationBar?.setMode(BottomNavigationBar.MODE_FIXED)
        mBottomNavigationBar?.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
        mBottomNavigationBar?.addItem(BottomNavigationItem(R.drawable.ic_videocam_black_24dp, R.string.tab_label_home))
        mBottomNavigationBar?.addItem(BottomNavigationItem(R.drawable.ic_search_black_24dp, R.string.tab_label_find))
        mBottomNavigationBar?.addItem(BottomNavigationItem(R.drawable.ic_person_black_24dp, R.string.tab_label_person))
        mBottomNavigationBar?.initialise()
        mBottomNavigationBar?.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {

            override fun onTabReselected(position: Int) {

            }

            override fun onTabUnselected(position: Int) {
                FragmentManagerUtil.onHide(supportFragmentManager.beginTransaction(), fragments[position])
            }

            override fun onTabSelected(position: Int) {
                FragmentManagerUtil.onShow(
                    supportFragmentManager.beginTransaction(),
                    R.id.frameLayout, fragments[position]
                )
            }

        })
        mBottomNavigationBar?.selectTab(0, true)
    }

}