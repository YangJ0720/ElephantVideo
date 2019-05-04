package org.elephant.video.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import org.elephant.video.bean.DeveloperRegisterBean
import org.elephant.video.network.bean.BaseResponse
import org.elephant.video.repository.RemoteDataRepository

/**
 * 功能描述
 * @author YangJ
 * @since 2019/5/4
 */
class DeveloperRegisterModel : ViewModel {

    private var repository: RemoteDataRepository
    private var name: String
    private var passwd: String
    private var email: String

    constructor(repository: RemoteDataRepository, name: String, passwd: String, email: String) : super() {
        this.repository = repository
        this.name = name
        this.passwd = passwd
        this.email = email
    }

    /**
     * 开发者注册
     */
    fun developerRegister(): LiveData<BaseResponse<DeveloperRegisterBean>> {
        return repository.developerRegister(name, passwd, email)
    }
}