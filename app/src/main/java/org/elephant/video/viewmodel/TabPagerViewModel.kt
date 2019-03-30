package org.elephant.video.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import org.elephant.video.bean.VideoBean
import org.elephant.video.repository.RemoteDataRepository

/**
 * @author YangJ
 * @date 2018/10/30
 */
class TabPagerViewModel(private var repository: RemoteDataRepository, private var id: Int?) : ViewModel() {

    fun getLiveData(): LiveData<List<VideoBean>> {
        return repository.getVideoBean(id)
    }
}