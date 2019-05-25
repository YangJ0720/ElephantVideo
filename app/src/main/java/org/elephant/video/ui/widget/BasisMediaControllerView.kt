package org.elephant.video.ui.widget

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import java.lang.ref.WeakReference

/**
 * 功能描述
 * @author YangJ
 * @since 2019/5/25
 */
open class BasisMediaControllerView : FrameLayout {

    companion object {
        // 播放控制器默认隐藏延时
        private const val DEFAULT_HIDE_DELAY = 3000L
        private const val HANDLER_WHAT = 1
    }

    // 播放控制器隐藏延时
    private var mDelay = DEFAULT_HIDE_DELAY
    private var mH: H? = null

    constructor(context: Context) : super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        initialize()
    }

    private fun initialize() {
        mH = H(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        visibility = View.VISIBLE
    }

    /**
     * 切换播放控制器显示、隐藏
     */
    fun toggle() {
        visibility = if (View.VISIBLE == visibility) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    /**
     * 显示播放控制器
     */
    fun show() {
        if (View.VISIBLE == visibility) return
        visibility = View.VISIBLE
    }

    /**
     * 隐藏播放控制器
     */
    fun hide() {
        if (View.VISIBLE == visibility) {
            visibility = View.GONE
        }
    }

    /**
     * 设置播放控制器隐藏延时
     */
    fun setDelayMillis(delayMillis: Long) {
        mDelay = delayMillis
    }

    override fun setVisibility(visibility: Int) {
        if (View.VISIBLE == visibility) { // 如果显示播放控制器
            if (View.VISIBLE != getVisibility()) {
                super.setVisibility(visibility)
            }
            mH?.let { handler ->
                if (handler.isRunning()) {
                    handler.removeMessages(HANDLER_WHAT)
                }
                handler.sendEmptyMessageDelayed(HANDLER_WHAT, mDelay)
            }
        } else { // 否则隐藏播放控制器
            if (View.VISIBLE == getVisibility()) {
                super.setVisibility(visibility)
            }
        }
    }

    override fun onDetachedFromWindow() {
        mH?.let { handler ->
            if (handler.isRunning()) {
                handler.removeMessages(HANDLER_WHAT)
            }
            mH = null
        }
        super.onDetachedFromWindow()
    }

    class H : Handler {

        private var mReference: WeakReference<BasisMediaControllerView>

        constructor(view: BasisMediaControllerView) : super() {
            mReference = WeakReference(view)
        }

        override fun handleMessage(msg: Message?) {
            val view = mReference.get() ?: return
            view.hide()
        }

        fun isRunning(): Boolean {
            return hasMessages(HANDLER_WHAT)
        }
    }
}