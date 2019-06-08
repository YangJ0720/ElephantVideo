package org.elephant.video.ui.widget

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.widget.SeekBar
import java.lang.ref.WeakReference

/**
 * 功能描述
 * @author YangJ
 * @since 2019/5/25
 */
class VideoSeekBar : SeekBar {

    companion object {
        private const val HANDLER_WHAT = 1
        private const val HANDLER_DELAYED = 1000L
    }

    private var mDate: Long = 0
    // 开始播放和暂停播放的时间间隔
    private var mDateInterval: Long = HANDLER_DELAYED
    private var mHandler: MainHandler? = null

    constructor(context: Context?) : super(context) {
        initialize()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initialize()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize()
    }

    private fun initialize() {
        setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                stop()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                start()
            }
        })
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mHandler = MainHandler(this)
    }

    fun toggle() {
        mHandler?.let { handler ->
            if (handler.isRunning()) {
                stop()
            } else {
                start()
            }
        }
    }

    /**
     * 开始滚动进度条
     */
    fun start() {
        mHandler?.let { handler ->
            if (handler.isRunning()) return
            onProgressNext(mDateInterval)
        }
    }

    /**
     * 停止滚动进度条
     */
    fun stop() {
        mHandler?.let { handler ->
            if (handler.isRunning()) {
                handler.removeMessages(HANDLER_WHAT)
                // 计算暂停播放与上一次进度自增的时间间隔
                calculateDateInterval()
            }
        }
    }

    /**
     * 进度自增
     */
    private fun onProgressNext(delayMillis: Long) {
        val msg = Message.obtain(mHandler, HANDLER_WHAT)
        mHandler?.sendMessageDelayed(msg, delayMillis)
        // 记录进度自增的时间戳
        mDate = System.currentTimeMillis()
    }

    /**
     * 计算暂停播放与上一次进度自增的时间间隔
     */
    private fun calculateDateInterval() {
        mDateInterval = HANDLER_DELAYED - System.currentTimeMillis() - mDate
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mHandler?.let { handler ->
            if (handler.isRunning()) {
                handler.removeMessages(HANDLER_WHAT)
            }
            mHandler = null
        }
    }

    class MainHandler : Handler {

        private var mReference: WeakReference<VideoSeekBar>

        constructor(progressBar: VideoSeekBar) {
            mReference = WeakReference(progressBar)
        }

        override fun handleMessage(msg: Message?) {
            println("handleMessage")
            val progressBar = mReference.get() ?: return
            // 获取进度条进度
            val progress = progressBar.progress + 1000
            println("progress = $progress")
            progressBar.progress = progress
            // 如果当前进度大于最大进度就停止
            if (progress + 1000 > progressBar.max) return
            // 进度条执行下一次自增
            progressBar.onProgressNext(HANDLER_DELAYED)
        }

        fun isRunning(): Boolean {
            return hasMessages(HANDLER_WHAT)
        }
    }
}