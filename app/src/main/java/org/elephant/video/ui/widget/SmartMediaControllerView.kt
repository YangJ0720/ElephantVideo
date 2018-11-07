package org.elephant.video.ui.widget

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import org.elephant.video.R
import org.elephant.video.utils.DateUtils
import java.lang.ref.WeakReference

private const val HANDLER_HIDE = 0
private const val HANDLER_HIDE_DELAYED = 5000L
private const val HANDLER_PROGRESS = 1
private const val HANDLER_PROGRESS_DELAYED = 1000L

/**
 * @author YangJ 自定义媒体控制器，用于控制视频播放、暂停、窗口、全屏播放模式
 * @date 2018/11/2
 */
class SmartMediaControllerView : FrameLayout {

    private var mHandler: ControllerHandler? = null

    private var mTvTitle: TextView? = null
    private var mProgress: ProgressBar? = null
    private var mIvPlay: ImageView? = null
    private var mProgressBar: ProgressBar? = null
    private var mTvDuration: TextView? = null

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
        mProgress = view.findViewById(R.id.progress)
        mIvPlay = view.findViewById(R.id.ivPlay)
        mIvPlay?.setOnClickListener { mListener?.onToggle(mIvPlay) }
        mProgressBar = view.findViewById(R.id.progressBar)
        mTvDuration = view.findViewById(R.id.tvDuration)
        view.findViewById<View>(R.id.ivFull).setOnClickListener { mListener?.onFull() }
        addView(view)
    }

    /**
     * 设置视频标题
     */
    fun setTitle(title: String?) {
        mTvTitle?.text = title
    }

    /**
     * 设置视频加载进度条
     */
    fun setProgressVisibility(visibility: Int) {
        mProgress?.visibility = visibility
    }

    /**
     * 设置视频时长
     */
    fun setDuration(duration: Int) {
        mProgressBar?.max = duration
        postDelayedHide(true, 0)
    }

    /**
     * 开启媒体控制器延时隐藏
     */
    fun postDelayedHide(isPlaying: Boolean, currentPosition: Int) {
        if (View.VISIBLE == visibility) {
            mHandler?.removeMessages(HANDLER_HIDE)
        } else {
            visibility = View.VISIBLE
        }
        // 判断是否为播放状态，如果是播放状态就移除进度条消息
        if (isPlaying) {
            mHandler?.sendMessage(Message.obtain(mHandler, HANDLER_PROGRESS, currentPosition, 0))
        } else if (mHandler?.hasMessages(HANDLER_PROGRESS)!!) {
            mHandler?.removeMessages(HANDLER_PROGRESS)
        }
        mHandler?.sendEmptyMessageDelayed(HANDLER_HIDE, HANDLER_HIDE_DELAYED)
    }

    /**
     * 设置媒体控制器操作监听器
     */
    fun setOnMediaControllerListener(listener: OnMediaControllerListener) {
        mListener = listener
    }

    /**
     * 销毁相关资源
     */
    fun onDestroy() {
        if (mHandler?.hasMessages(HANDLER_HIDE)!!) {
            mHandler?.removeMessages(HANDLER_HIDE)
        }
        if (mHandler?.hasMessages(HANDLER_PROGRESS)!!) {
            mHandler?.removeMessages(HANDLER_PROGRESS)
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
    }

    companion object {
        class ControllerHandler(private val reference: WeakReference<SmartMediaControllerView>) : Handler() {

            override fun handleMessage(msg: Message?) {
                val view = reference?.get()
                if (view != null) {
                    when (msg?.what) {
                        HANDLER_HIDE -> {
                            view.visibility = View.GONE
                            removeMessages(HANDLER_PROGRESS)
                        }
                        HANDLER_PROGRESS -> {
                            view.mProgressBar?.progress = msg.arg1
                            view.mTvDuration?.text = DateUtils.convertPlayDuration(msg.arg1 / 1000)
                            // 发送延时消息，用于更新播放进度
                            msg.arg1 += HANDLER_PROGRESS_DELAYED.toInt()
                            sendMessageDelayed(Message.obtain(msg), HANDLER_PROGRESS_DELAYED)
                        }
                    }
                }
            }
        }
    }
}