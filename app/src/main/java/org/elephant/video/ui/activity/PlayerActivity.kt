package org.elephant.video.ui.activity

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import org.elephant.video.R
import org.elephant.video.base.BaseActivity
import org.elephant.video.databinding.ActivityPlayerBinding
import org.elephant.video.ui.widget.SmartVideoView

/**
 * @author YangJ
 */
class PlayerActivity : BaseActivity() {

    private var mVideoView: SmartVideoView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // data
        val bundle = intent.extras
        val title = bundle.getString(ARG_PARAM_TITLE)
        val playUrl = bundle.getString(ARG_PARAM_PLAY_URL)
        val description = bundle.getString(ARG_PARAM_DESCRIPTION)
        // view
        val binding = DataBindingUtil.setContentView<ActivityPlayerBinding>(this, R.layout.activity_player)
        binding.videoView.setVideoPath(playUrl)
        binding.controller.setTitle(title)
        binding.videoView.setSmartMediaController(binding.controller)
        binding.tvDescription.text = description
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

//    override fun onConfigurationChanged(newConfig: Configuration?) {
//        super.onConfigurationChanged(newConfig)
//        val orientation = resources.configuration.orientation
//        if (Configuration.ORIENTATION_LANDSCAPE == orientation) {
//
//        } else if (Configuration.ORIENTATION_PORTRAIT == orientation) {
//
//        }
//    }

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
