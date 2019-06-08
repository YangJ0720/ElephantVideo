package org.elephant.video.ui.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import org.elephant.video.R
import org.elephant.video.base.BaseActivity
import org.elephant.video.databinding.ActivityLoginBinding

/**
 * @author YangJ
 */
class LoginActivity : BaseActivity() {

    override fun initData() {

    }

    override fun initView() {
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisteredActivity::class.java))
        }
    }

}
