package org.elephant.video.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import org.elephant.video.repository.RemoteDataRepository

/**
 * @author YangJ
 * @date 2018/10/30
 */
class HomeTabViewModelFactory(private var repository: RemoteDataRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeTabViewModel(repository) as T
    }
}