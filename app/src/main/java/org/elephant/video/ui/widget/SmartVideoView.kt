package org.elephant.video.ui.widget

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.ImageView
import android.widget.VideoView
import org.elephant.video.R

/**
 * @author YangJ
 * @date 2018/11/2
 */
class SmartVideoView : VideoView, MediaPlayer.OnPreparedListener {

    private var mActivity: Activity? = null
    private var mMediaController: SmartMediaControllerView? = null
    // listener
    private var mListener: CustomOnGlobalLayoutListener? = null

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
        mActivity = context as Activity
        mListener = CustomOnGlobalLayoutListener()
        viewTreeObserver.addOnGlobalLayoutListener(mListener)
        // 设置视频播放状态监听
        setOnPreparedListener(this)
    }

    /**
     * 设置媒体控制器
     */
    fun setSmartMediaController(controller: SmartMediaControllerView) {
        mMediaController = controller
        mMediaController?.setOnMediaControllerListener(object : SmartMediaControllerView.OnMediaControllerListener {
            override fun onFull() {
                val config = resources.configuration
                if (Configuration.ORIENTATION_LANDSCAPE == config.orientation) {
                    mActivity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                } else if (Configuration.ORIENTATION_PORTRAIT == config.orientation) {
                    mActivity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                }
            }

            override fun onToggle(view: ImageView?) {
                if (isPlaying) {
                    pause()
                    view?.setImageResource(R.drawable.ic_video_play)
                } else {
                    start()
                    view?.setImageResource(R.drawable.ic_video_pause)
                }
                mMediaController?.postDelayedHide(isPlaying, currentPosition)
            }
        })
    }

    /**
     * 根据播放界面横竖屏设置布局参数
     */
    fun setVideoViewParams(isFull: Boolean) {
        val window = mActivity?.window
        layoutParams = if (isFull) {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, mListener?.mHeight!!)
        } else {
            window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT)
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mMediaController?.setDuration(duration)
        mMediaController?.setProgressVisibility(View.GONE)
        mp?.start()
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (MotionEvent.ACTION_DOWN == ev?.action) {
            mMediaController?.postDelayedHide(isPlaying, currentPosition)
        }
        return super.onTouchEvent(ev)
    }

    /**
     * 销毁相关资源
     */
    fun onDestroy() {
        stopPlayback()
        mActivity = null
        mMediaController?.onDestroy()
    }

    /**
     * 播放器高度监听
     */
    inner class CustomOnGlobalLayoutListener : ViewTreeObserver.OnGlobalLayoutListener {
        var mHeight = 0
        override fun onGlobalLayout() {
            if (mHeight == 0) {
                mHeight = height
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
            } else {
                viewTreeObserver.removeGlobalOnLayoutListener(this)
            }
        }
    }

}