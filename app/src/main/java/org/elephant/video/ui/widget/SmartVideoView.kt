package org.elephant.video.ui.widget

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
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
}