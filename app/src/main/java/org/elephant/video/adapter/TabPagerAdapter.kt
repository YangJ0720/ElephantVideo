package org.elephant.video.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * @author YangJ
 * @date 2018/10/30
 */
class TabPagerAdapter(fm: FragmentManager?, private var fragments: List<Fragment>?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return fragments?.get(position)
    }

    override fun getCount(): Int {
        return if (fragments == null) {
            0
        } else {
            fragments!!.size
        }
    }

}