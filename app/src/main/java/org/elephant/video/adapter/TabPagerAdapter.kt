package org.elephant.video.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import org.elephant.video.ui.fragment.TabPagerFragment

/**
 * @author YangJ
 * @date 2018/10/30
 */
class TabPagerAdapter(fm: FragmentManager?, ids: ArrayList<Int>) : FragmentStatePagerAdapter(fm) {

    private var mIds: ArrayList<Int> = ids

    override fun getItem(position: Int): Fragment? {
        val id = mIds[position]
        return TabPagerFragment.newInstance(id)
    }

    override fun getCount(): Int {
        return mIds.size
    }

}