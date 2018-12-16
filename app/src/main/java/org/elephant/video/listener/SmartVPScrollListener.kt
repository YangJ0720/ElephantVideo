package org.elephant.video.listener

import android.content.Context
import android.support.v4.view.ViewPager
import org.elephant.video.utils.GlideTools

/**
 * Created by YangJ on 2018/12/16.
 */
class SmartVPScrollListener(context: Context) : ViewPager.OnPageChangeListener {

    private val mTools by lazy { GlideTools(context) }

    override fun onPageScrollStateChanged(newState: Int) {
        if (ViewPager.SCROLL_STATE_IDLE == newState) {
            mTools.resumeRequests()
        } else {
            mTools.pauseRequests()
        }
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

    }

    override fun onPageSelected(p0: Int) {

    }

}