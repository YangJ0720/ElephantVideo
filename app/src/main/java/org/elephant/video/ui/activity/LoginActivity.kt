package org.elephant.video.ui.activity

import android.databinding.DataBindingUtil
import org.elephant.video.R
import org.elephant.video.base.BaseActivity
import org.elephant.video.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    override fun initData() {

    }

    override fun initView() {
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)

    }

}
