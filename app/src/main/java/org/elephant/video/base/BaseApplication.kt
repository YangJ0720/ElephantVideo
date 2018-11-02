package org.elephant.video.base

import android.app.Application
import com.squareup.leakcanary.LeakCanary

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
        LeakCanary.install(this)
    }
}