package org.elephant.video.ui.activity

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.widget.Toast
import org.elephant.video.R
import org.elephant.video.adapter.TabPagerAdapter
import org.elephant.video.base.BaseActivity
import org.elephant.video.databinding.ActivityPlayerBinding
import org.elephant.video.ui.fragment.PlayCommentFragment
import org.elephant.video.ui.fragment.PlayDetailsFragment
import org.elephant.video.ui.widget.SmartVideoView

/**
 * @author YangJ 视频播放界面
 */
class PlayerActivity : BaseActivity() {

    private var mTitle: String? = null
    private var mPlayUrl: String? = null
    private var mFragments: ArrayList<Fragment>? = null

    private var mVideoView: SmartVideoView? = null

    override fun initData() {
        val bundle = intent.extras
        mTitle = bundle.getString(ARG_PARAM_TITLE)
        mPlayUrl = bundle.getString(ARG_PARAM_PLAY_URL)
        mFragments = arrayListOf(PlayDetailsFragment.newInstance("1", "1"), PlayCommentFragment.newInstance("1", "1"))
    }

    override fun initView() {
        val binding = DataBindingUtil.setContentView<ActivityPlayerBinding>(this, R.layout.activity_player)
        // 初始化播放器
        mVideoView = binding.videoView
        mVideoView?.setVideoPath(mPlayUrl)
        binding.controller.setTitle(mTitle)
        binding.videoView.setSmartMediaController(binding.controller)
        // 初始化选项卡
        binding.viewPager.adapter = TabPagerAdapter(supportFragmentManager, mFragments)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.getTabAt(0)?.text = resources.getText(R.string.tab_label_play_details)
        binding.tabLayout.getTabAt(1)?.text = resources.getText(R.string.tab_label_play_comment)
    }

    override fun onDestroy() {
        mVideoView?.onDestroy()
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE == requestedOrientation) {
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
                mVideoView?.setVideoViewParams(true)
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                mVideoView?.setVideoViewParams(false)
            }
        }
    }

    companion object {
        private const val ARG_PARAM_TITLE = "title"
        private const val ARG_PARAM_PLAY_URL = "playUrl"
        private const val ARG_PARAM_DESCRIPTION = "description"

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
