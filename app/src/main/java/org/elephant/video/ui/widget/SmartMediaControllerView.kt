package org.elephant.video.ui.widget

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.*
import org.elephant.video.R
import org.elephant.video.utils.DateUtils
import java.lang.ref.WeakReference

/**
 * 让媒体控制器显示、隐藏
 */
private const val HANDLER_WHAT_HIDE = 1
private const val HANDLER_HIDE_DELAYED = 5000L
/**
 * 让媒体控制器进度改变播放进度
 */
private const val HANDLER_PROGRESS = 2
private const val HANDLER_PROGRESS_DELAYED = 1000L

/**
 * @author YangJ 自定义媒体控制器，用于控制视频播放、暂停、窗口、全屏播放模式
 * @date 2018/11/2
 */
class SmartMediaControllerView : FrameLayout, SeekBar.OnSeekBarChangeListener {

    private lateinit var mHandler: ControllerHandler

    private lateinit var mTvTitle: TextView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mIvPlay: ImageView
    private lateinit var mSeekBar: SeekBar
    private lateinit var mTvDuration: TextView

    private var mListener: OnMediaControllerListener? = null

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
        mHandler = ControllerHandler(WeakReference(this))
        // 初始化媒体控制器界面
        val view = LayoutInflater.from(context).inflate(R.layout.view_media_controller, null)
        view.findViewById<View>(R.id.ivBack).setOnClickListener {
            if (context is Activity) {
                (context as Activity).finish()
            }
        }
        mTvTitle = view.findViewById(R.id.tvTitle)
        mProgressBar = view.findViewById(R.id.progressBar)
        mIvPlay = view.findViewById(R.id.ivPlay)
        mIvPlay.setOnClickListener { mListener?.onToggle(mIvPlay) }
        mSeekBar = view.findViewById(R.id.seekBar)
        mSeekBar.setOnSeekBarChangeListener(this)
        mTvDuration = view.findViewById(R.id.tvDuration)
        view.findViewById<View>(R.id.ivFull).setOnClickListener { mListener?.onFull() }
        addView(view)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_UP -> {
                mHandler.sendEmptyMessageDelayed(HANDLER_WHAT_HIDE, HANDLER_HIDE_DELAYED)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 设置视频标题
     */
    fun setTitle(title: String?) {
        mTvTitle.text = title
    }

    /**
     * 设置视频加载进度条
     */
    fun setProgressVisibility(visibility: Int) {
        mProgressBar.visibility = visibility
    }

    /**
     * 设置视频时长
     */
    fun setDuration(duration: Int) {
        mSeekBar.max = duration
        postDelayedHide(true, 0)
    }

    /**
     * 设置视频缓冲状态
     */
    fun setBuffRing(isBuff: Boolean) {
        if (isBuff) {
            mProgressBar.visibility = View.VISIBLE
        } else {
            mProgressBar.visibility = View.GONE
        }
    }

    /**
     * 开启媒体控制器延时隐藏
     */
    fun postDelayedHide(isPlaying: Boolean, currentPosition: Int) {
        if (View.VISIBLE == visibility) {
            mHandler.removeMessages(HANDLER_WHAT_HIDE)
        } else {
            visibility = View.VISIBLE
        }
        // 判断是否为播放状态，如果是播放状态就移除进度条消息
        if (isPlaying) {
            mHandler.sendMessage(Message.obtain(mHandler, HANDLER_PROGRESS, currentPosition, 0))
        } else if (mHandler.hasMessages(HANDLER_PROGRESS)) {
            mHandler.removeMessages(HANDLER_PROGRESS)
        }
        mHandler.sendEmptyMessageDelayed(HANDLER_WHAT_HIDE, HANDLER_HIDE_DELAYED)
    }

    /**
     * 设置媒体控制器操作监听器
     */
    fun setOnMediaControllerListener(listener: OnMediaControllerListener) {
        mListener = listener
    }

    /**
     * 播放完毕
     */
    fun end() {
        if (mHandler.hasMessages(HANDLER_PROGRESS)) {
            mHandler.removeMessages(HANDLER_PROGRESS)
        }
    }

    /**
     * 销毁相关资源
     */
    fun onDestroy() {
        if (mHandler.hasMessages(HANDLER_WHAT_HIDE)) {
            mHandler.removeMessages(HANDLER_WHAT_HIDE)
        }
        if (mHandler.hasMessages(HANDLER_PROGRESS)) {
            mHandler.removeMessages(HANDLER_PROGRESS)
        }
        mListener = null
    }

    interface OnMediaControllerListener {
        /**
         * 切换窗口、全屏播放模式
         */
        fun onFull()

        /**
         * 点击按钮执行播放、暂停
         */
        fun onToggle(view: ImageView?)

        /**
         * 跳转到指定播放位置
         */
        fun onSeek(progress: Int?)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        mHandler.removeCallbacksAndMessages(null)
    }

    override fun onProgressChanged(seekBar: SeekBar?, p1: Int, p2: Boolean) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        mListener?.onSeek(mSeekBar.progress)
    }

    companion object {
        class ControllerHandler(private val reference: WeakReference<SmartMediaControllerView>) : Handler() {

            override fun handleMessage(msg: Message?) {
                val view = reference.get() ?: return
                when (msg?.what) {
                    HANDLER_WHAT_HIDE -> {
                        view.visibility = View.INVISIBLE
                        removeMessages(HANDLER_PROGRESS)
                    }
                    HANDLER_PROGRESS -> {
                        view.mSeekBar.progress = msg.arg1
                        view.mTvDuration.text =
                            "${DateUtils.convertPlayDuration(msg.arg1 / 1000)} : ${DateUtils.convertPlayDuration(view.mSeekBar.max / 1000)}"
                        // 发送延时消息，用于更新播放进度
                        msg.arg1 += HANDLER_PROGRESS_DELAYED.toInt()
                        sendMessageDelayed(Message.obtain(msg), HANDLER_PROGRESS_DELAYED)
                    }
                }
            }
        }
    }

}