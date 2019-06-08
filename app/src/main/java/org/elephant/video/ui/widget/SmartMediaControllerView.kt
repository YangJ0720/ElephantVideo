package org.elephant.video.ui.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import org.elephant.video.R

/**
 * @author YangJ 自定义媒体控制器，用于控制视频播放、暂停、窗口、全屏播放模式
 * @date 2018/11/2
 */
class SmartMediaControllerView : BasisMediaControllerView {

    private lateinit var mTvTitle: TextView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mIvPlay: ImageView
    private lateinit var mSeekBar: VideoSeekBar
    private lateinit var mTvDuration: TextView

    private var mListener: OnMediaControllerListener? = null

    constructor(context: Context) : super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize()
    }

    private fun initialize() {
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
        mSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mListener?.onSeek(mSeekBar.progress)
            }

        })
        mTvDuration = view.findViewById(R.id.tvDuration)
        view.findViewById<View>(R.id.ivFull).setOnClickListener { mListener?.onFull() }
        addView(view)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_UP -> {
                mSeekBar.toggle()
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
     * 设置视频加载进度条显示状态
     */
    fun setProgressVisibility(visibility: Int) {
        mProgressBar.visibility = visibility
    }

    /**
     * 设置视频时长
     */
    fun setDuration(duration: Long) {
        mSeekBar.max = duration.toInt()
        mSeekBar.start()
    }

    /**
     * 设置视频缓冲状态
     */
    fun setBuffRing(isBuff: Boolean) {
        if (isBuff) { // 开始缓冲
            mSeekBar.stop()
            mProgressBar.visibility = View.VISIBLE
        } else { // 缓冲完毕
            mSeekBar.start()
            mProgressBar.visibility = View.GONE
        }
    }

    /**
     * 设置媒体控制器操作监听器
     */
    fun setOnMediaControllerListener(listener: OnMediaControllerListener) {
        mListener = listener
    }

    /**
     * 开始播放
     */
    fun start() {
        mSeekBar.start()
    }

    /**
     * 暂停播放
     */
    fun pause() {
        mSeekBar.stop()
    }

    /**
     * 播放完毕
     */
    fun end() {
        mSeekBar.stop()
    }

    /**
     * 销毁相关资源
     */
    fun onDestroy() {
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

}