package org.elephant.video.base

import android.app.Application

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
}