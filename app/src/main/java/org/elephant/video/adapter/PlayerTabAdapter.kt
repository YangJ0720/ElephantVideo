package org.elephant.video.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class PlayerTabAdapter : FragmentPagerAdapter {

    private var mFragments: ArrayList<Fragment>

    constructor(fm: FragmentManager?, fragments: ArrayList<Fragment>) : super(fm) {
        this.mFragments = fragments
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }
}