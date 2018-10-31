package org.elephant.video.ui.activity

import android.databinding.DataBindingUtil
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
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
        val title = bundle.getString("title")
        val playUrl = bundle.getString("playUrl")
        val description = bundle.getString("description")
        // view
        val binding = DataBindingUtil.setContentView<ActivityPlayerBinding>(this, R.layout.activity_player)
        binding.title.findViewById<TextView>(R.id.tvTitle).text = title
        binding.title.findViewById<ImageView>(R.id.ivBack).setOnClickListener { finish() }
        binding.tvDescription.text = description
        mMediaPlayer = MediaPlayer()
        mMediaPlayer?.setDataSource(this, Uri.parse(playUrl))
        mMediaPlayer?.setOnPreparedListener { mp ->
            binding.progressBar.visibility = View.GONE
            mMediaPlayer?.setDisplay(binding.surfaceView.holder)
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
}
