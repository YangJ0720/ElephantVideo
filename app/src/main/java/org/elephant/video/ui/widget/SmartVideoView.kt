package org.elephant.video.ui.widget

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.text.TextUtils
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import org.elephant.video.R
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer

/**
 * @author YangJ
 * @date 2018/11/2
 */
class SmartVideoView : FrameLayout {

    private var isPause = false

    private lateinit var mActivity: Activity
    private lateinit var mMediaPlayer: IjkMediaPlayer
    private lateinit var mSurfaceView: SurfaceView

    private lateinit var mMediaController: SmartMediaControllerView
    // listener
    private lateinit var mListener: CustomOnGlobalLayoutListener

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
        mActivity = context as Activity
        mListener = CustomOnGlobalLayoutListener()
        viewTreeObserver.addOnGlobalLayoutListener(mListener)
        // IMediaPlayer
        mMediaPlayer = IjkMediaPlayer()
        // 开启硬解码
        // mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1)
        // 设置监听
        mMediaPlayer.setOnPreparedListener { player ->
            println("OnPrepared")
            mMediaController.setDuration(mMediaPlayer.duration.toInt())
            mMediaController.setProgressVisibility(View.GONE)
            player.start()
        }
        mMediaPlayer.setOnCompletionListener {
            println("onCompletion")
            mMediaController.end()
        }
        mMediaPlayer.setOnInfoListener(object : IMediaPlayer.OnInfoListener {
            override fun onInfo(p: IMediaPlayer, p1: Int, p2: Int): Boolean {
                println("onInfo: p1 = $p1, p2 = $p2")
                if (IMediaPlayer.MEDIA_INFO_BUFFERING_START == p1) {
                    mMediaController.postDelayedHide(false, p.duration.toInt() / 1000)
                } else if (IMediaPlayer.MEDIA_INFO_BUFFERING_END == p1) {
                    mMediaController.postDelayedHide(true, p.duration.toInt() / 1000)
                }
                return false
            }

        })
        mMediaPlayer.setOnSeekCompleteListener { println("onSeekComplete") }
        mMediaPlayer.setOnErrorListener { player, p1, p2 ->
            println("onError -> $player")
            println("onError -> $p1")
            println("onError -> $p2")
            false
        }
        // SurfaceView
        mSurfaceView = SurfaceView(context)
        mSurfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                mMediaPlayer.setDisplay(null)
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
                mMediaPlayer.setDisplay(holder)
                if (isPause) {
                    mMediaPlayer.start()
                    isPause = false
                } else {
                    mMediaPlayer.prepareAsync()
                }
            }

        })
        mSurfaceView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(mSurfaceView)
        // MediaController
        mMediaController = SmartMediaControllerView(context)
        setSmartMediaController()
        addView(mMediaController)
    }

    /**
     * 设置视频播放链接
     */
    fun setVideoPath(path: String?) {
        if (TextUtils.isEmpty(path)) {
            return
        }
        mMediaPlayer.dataSource = path
    }

    /**
     * 设置标题
     */
    fun setTitle(title: String?) {
        if (TextUtils.isEmpty(title)) {
            return
        }
        mMediaController.setTitle(title)
    }

    /**
     * 设置媒体控制器
     */
    private fun setSmartMediaController() {
        mMediaController.setOnMediaControllerListener(object : SmartMediaControllerView.OnMediaControllerListener {
            override fun onFull() {
                val config = resources.configuration
                if (Configuration.ORIENTATION_LANDSCAPE == config.orientation) {
                    mActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                } else if (Configuration.ORIENTATION_PORTRAIT == config.orientation) {
                    mActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                }
            }

            override fun onToggle(view: ImageView?) {
                if (mMediaPlayer.isPlaying) {
                    mMediaPlayer.pause()
                    view?.setImageResource(R.drawable.ic_video_play)
                } else {
                    mMediaPlayer.start()
                    view?.setImageResource(R.drawable.ic_video_pause)
                }
                mMediaController.postDelayedHide(mMediaPlayer.isPlaying, mMediaPlayer.currentPosition.toInt())
            }

            override fun onSeek(progress: Int?) {
                progress ?: return
                mMediaPlayer.seekTo(progress.toLong())
                mMediaController.postDelayedHide(mMediaPlayer.isPlaying, mMediaPlayer.currentPosition.toInt())
            }
        })
    }

    /**
     * 根据播放界面横竖屏设置布局参数
     */
    fun setVideoViewParams(isFull: Boolean) {
        val window = mActivity.window
        layoutParams = if (isFull) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, mListener.mHeight)
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                mMediaController.postDelayedHide(mMediaPlayer.isPlaying, mMediaPlayer.currentPosition.toInt())
            }
        }
        return super.dispatchTouchEvent(event)
    }

    fun pause() {
        mMediaPlayer.pause()
        isPause = true
    }

    /**
     * 销毁相关资源
     */
    fun onDestroy() {
        if (mMediaPlayer.isPlaying) {
            mMediaPlayer.pause()
        }
        mMediaPlayer.stop()
        mMediaPlayer.release()
        mMediaController.onDestroy()
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