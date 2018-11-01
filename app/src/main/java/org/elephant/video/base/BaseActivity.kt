package org.elephant.video.base

import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * @author YangJ
 * @date 2018/10/26
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (MotionEvent.ACTION_DOWN == ev?.action) {
            val childView = currentFocus
            if (childView != null && hideKeyboard(childView, ev.x.toInt(), ev.y.toInt())) {
                var manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(childView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun hideKeyboard(childView: View, x: Int, y: Int): Boolean {
        if (childView is EditText) {
            val location = IntArray(2)
            childView.getLocationInWindow(location)
            // 获取当前拥有焦点的控件view所在位置
            val left = location[0]
            val top = location[1]
            val rect = Rect(left, top, left + childView.width, top + childView.height)
            return !rect.contains(x, y)
        }
        return false
    }
}