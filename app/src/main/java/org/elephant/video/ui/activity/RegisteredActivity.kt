package org.elephant.video.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import org.elephant.video.R
import org.elephant.video.base.BaseActivity
import org.elephant.video.bean.DeveloperRegisterBean
import org.elephant.video.databinding.ActivityRegisteredBinding
import org.elephant.video.network.bean.BaseResponse
import org.elephant.video.utils.InjectorUtils
import org.elephant.video.viewmodel.DeveloperRegisterModel

/**
 * @author YangJ 用户注册界面
 */
class RegisteredActivity : BaseActivity() {

    override fun initData() {

    }

    override fun initView() {
        val binding = DataBindingUtil.setContentView<ActivityRegisteredBinding>(this, R.layout.activity_registered)
        val etUserName = binding.etUserName
        val etPassWord = binding.etPassWord
        val etEmail = binding.etEmail
        binding.tvRegister.setOnClickListener {
            val username = etUserName.text.toString().trim()
            val password = etPassWord.text.toString().trim()
            val email = etEmail.text.toString().trim()
            developerRegister(username, password, email)
        }
    }

    private fun developerRegister(name: String, passwd: String, email: String) {
        val factory = InjectorUtils.provideDeveloperRegisterModelFactory(name, passwd, email)
        val model = ViewModelProviders.of(this, factory).get(DeveloperRegisterModel::class.java)
        model.developerRegister().observe(this, Observer<BaseResponse<DeveloperRegisterBean>> { response->
            println("response = $response")
            if (response == null) {

            } else {
                val apikey = response.result?.apikey
                println("apikey = $apikey")
            }
        })
    }
}
