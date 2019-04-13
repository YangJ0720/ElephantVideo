package org.elephant.video.base

import android.app.Application
import android.content.ComponentCallbacks2
import com.bumptech.glide.Glide

/**
 * @author YangJ
 * @date 2018/10/26
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {

    }

    override fun onLowMemory() {
        Glide.get(this).clearMemory()
        super.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        if (ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE == level) {
            Glide.get(this).clearMemory()
        }
        Glide.get(this).trimMemory(level)
        super.onTrimMemory(level)
    }

}