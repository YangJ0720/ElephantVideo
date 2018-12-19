package org.elephant.video.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup
import com.bumptech.glide.Glide

/**
 * @author YangJ
 * @date 2018/10/30
 */
class TabPagerAdapter : FragmentPagerAdapter {

    private var mContext: Context?
    private var mFragments: List<Fragment>

    constructor(fm: FragmentManager?, fragments: List<Fragment>) : this(null, fm, fragments)

    constructor(context: Context?, fm: FragmentManager?, fragments: List<Fragment>) : super(fm) {
        mContext = context
        mFragments = fragments
    }

    override fun getItem(position: Int): Fragment? {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        mContext?.let {
            Glide.get(it).clearMemory()
        }
    }

}