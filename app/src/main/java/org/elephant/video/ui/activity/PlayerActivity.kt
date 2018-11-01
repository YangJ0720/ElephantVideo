package org.elephant.video.ui.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import org.elephant.video.R
import org.elephant.video.base.BaseActivity
import org.elephant.video.databinding.ActivityPlayerBinding

/**
 * @author YangJ
 */
class PlayerActivity : BaseActivity() {

    private var mMediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // data
        val bundle = intent.extras
        val title = bundle.getString(ARG_PARAM_TITLE)
        val playUrl = bundle.getString(ARG_PARAM_PLAY_URL)
        val description = bundle.getString(ARG_PARAM_DESCRIPTION)
        // view
        val binding = DataBindingUtil.setContentView<ActivityPlayerBinding>(this, R.layout.activity_player)
        binding.surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {

            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
                mMediaPlayer?.setDisplay(holder)
            }

        })
        binding.title.findViewById<TextView>(R.id.tvTitle).text = title
        binding.title.findViewById<ImageView>(R.id.ivBack).setOnClickListener { finish() }
        binding.tvDescription.text = description
        mMediaPlayer = MediaPlayer()
        mMediaPlayer?.setDataSource(this, Uri.parse(playUrl))
        mMediaPlayer?.setOnPreparedListener { mp ->
            binding.progressBar.visibility = View.GONE
            mp.start()
        }
        mMediaPlayer?.prepareAsync()
    }

    override fun onDestroy() {
        if (mMediaPlayer != null) {
            mMediaPlayer?.release()
            mMediaPlayer = null
        }
        super.onDestroy()
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
