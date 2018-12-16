package org.elephant.video.listener

import android.content.Context
import android.support.v7.widget.RecyclerView
import org.elephant.video.utils.GlideTools

/**
 * @author YangJ
 * @date 2018/10/30
 */
class SmartRVScrollListener(context: Context) : RecyclerView.OnScrollListener() {

    private val mTools by lazy { GlideTools(context) }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (RecyclerView.SCROLL_STATE_IDLE == newState) {
            mTools.resumeRequests()
        } else {
            mTools.pauseRequests()
        }
    }

}