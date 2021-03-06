package org.elephant.video.ui.activity

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import org.elephant.video.R
import org.elephant.video.adapter.PlayerTabAdapter
import org.elephant.video.base.BaseActivity
import org.elephant.video.databinding.ActivityPlayerBinding
import org.elephant.video.ui.fragment.PlayCommentFragment
import org.elephant.video.ui.fragment.PlayDetailsFragment
import org.elephant.video.ui.widget.SmartVideoView
import tv.danmaku.ijk.media.player.IjkMediaPlayer

/**
 * @author YangJ 视频播放界面
 */
class PlayerActivity : BaseActivity() {

    private lateinit var mTitle: String
    private lateinit var mPlayUrl: String
    private lateinit var mFragments: ArrayList<Fragment>

    private lateinit var mVideoView: SmartVideoView

    override fun initData() {
        val bundle = intent.extras
        bundle?.let {
            mTitle = it.getString(ARG_PARAM_TITLE)
            mPlayUrl = it.getString(ARG_PARAM_PLAY_URL)
            val description = it.getString(ARG_PARAM_DESCRIPTION)
            mFragments = arrayListOf(
                PlayDetailsFragment.newInstance(description),
                PlayCommentFragment.newInstance()
            )
        }
    }

    override fun initView() {
        val binding = DataBindingUtil.setContentView<ActivityPlayerBinding>(this, R.layout.activity_player)
        // 初始化播放器
        mVideoView = binding.videoView
        mVideoView.setVideoPath(mPlayUrl)
        mVideoView.setTitle(mTitle)
        // 初始化选项卡
        binding.viewPager.adapter = PlayerTabAdapter(supportFragmentManager, mFragments)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        // 简介
        binding.tabLayout.getTabAt(0)?.text = resources.getText(R.string.tab_label_play_details)
        // 评论
        binding.tabLayout.getTabAt(1)?.text = resources.getText(R.string.tab_label_play_comment)
    }

    override fun onPause() {
        super.onPause()
        mVideoView.pause()
    }

    override fun onStop() {
        super.onStop()
        IjkMediaPlayer.native_profileEnd()
    }

    override fun onDestroy() {
        mVideoView.onDestroy()
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            // 判断视频播放界面是否为横屏模式
            if (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE == requestedOrientation) {
                // 如果为横屏模式，先退出横屏模式
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                mVideoView.setVideoViewParams(true)
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                mVideoView.setVideoViewParams(false)
            }
        }
    }

    companion object {
        private const val ARG_PARAM_TITLE = "title"
        private const val ARG_PARAM_PLAY_URL = "playUrl"
        private const val ARG_PARAM_DESCRIPTION = "description"

        /**
         * 打开视频播放界面
         * @param context 参数为当前上下文对象
         * @param title 参数为视频标题
         * @param playUrl 参数为视频播放地址
         * @param description 参数为视频描述
         */
        @JvmStatic
        fun startActivity(context: Context?, title: String?, playUrl: String?, description: String?) {
            val intent = Intent(context, PlayerActivity::class.java)
            val bundle = Bundle()
            bundle.putString(ARG_PARAM_TITLE, title)
            bundle.putString(ARG_PARAM_PLAY_URL, playUrl)
            bundle.putString(ARG_PARAM_DESCRIPTION, description)
            intent.putExtras(bundle)
            context?.startActivity(intent)
        }
    }
}
