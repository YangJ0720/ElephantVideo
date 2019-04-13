package org.elephant.video.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.github.moduth.blockcanary.BlockCanary
import com.squareup.leakcanary.LeakCanary
import org.elephant.video.base.AppBlockCanaryContext

/**
 * @author YangJ
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000)
    }

    override fun onBackPressed() {

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            Thread(Runnable {
                LeakCanary.install(application)
                BlockCanary.install(application, AppBlockCanaryContext()).start()
            }).start()
        }
    }

}
