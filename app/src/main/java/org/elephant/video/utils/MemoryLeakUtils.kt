package org.elephant.video.utils

import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * 功能描述
 * @author YangJ
 * @since 2019/5/25
 */
object MemoryLeakUtils {

    /**
     * 对InputMethodManager持有的view引用置空
     */
    fun fixInputMethodMemoryLeak(context: Context?) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) return
        if (context == null) return
        val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val cls = InputMethodManager::class.java
        val names = arrayOf("mCurRootView", "mServedView", "mNextServedView")
        names.forEach { name ->
            val field = cls.getDeclaredField(name)
            if (!field.isAccessible) {
                field.isAccessible = true
            }
            val obj = field.get(manager)
            if (obj != null && obj is View) {
                field.set(manager, null)
            }
        }
    }
}