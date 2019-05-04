package org.elephant.video.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import org.elephant.video.repository.RemoteDataRepository

/**
 * 功能描述
 * @author Administrator
 * @since 2019/5/4
 */
class DeveloperRegisterModelFactory : ViewModelProvider.NewInstanceFactory {

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

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DeveloperRegisterModel(repository, name, passwd, email) as T
    }
}