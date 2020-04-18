package org.elephant.video.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * @author YangJ
 */
class SPUtils {

    companion object {

        private var mPreferences : SharedPreferences? = null

        fun initialize(context: Context, name: String) {
            mPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        }
    }

}