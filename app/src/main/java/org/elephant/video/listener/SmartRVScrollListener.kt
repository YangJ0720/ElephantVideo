package org.elephant.video.listener

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * @author YangJ
 * @date 2018/10/30
 */
class SmartRVScrollListener(private var mContext: Context) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (RecyclerView.SCROLL_STATE_IDLE == newState) {
            resumeRequests()
        } else {
            pauseRequests()
        }
    }

    /**
     * 暂停图片加载请求
     */
    private fun pauseRequests() {
        if (mContext == null) {
            return
        }
        Glide.with(mContext).pauseRequests()
    }

    /**
     * 恢复图片加载请求
     */
    private fun resumeRequests() {
        if (mContext == null) {
            return
        }
        if (Glide.with(mContext).isPaused) {
            Glide.with(mContext).resumeRequests()
        }
    }

}