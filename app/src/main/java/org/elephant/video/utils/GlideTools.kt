package org.elephant.video.utils

import android.content.Context
import com.bumptech.glide.Glide

/**
 * Created by YangJ on 2018/12/16.
 */
class GlideTools(private val context: Context) {

    /**
     * 暂停图片加载请求
     */
    fun pauseRequests() {
        Glide.with(context).pauseRequests()
    }

    /**
     * 恢复图片加载请求
     */
    fun resumeRequests() {
        if (Glide.with(context).isPaused) {
            Glide.with(context).resumeRequests()
        }
    }

}