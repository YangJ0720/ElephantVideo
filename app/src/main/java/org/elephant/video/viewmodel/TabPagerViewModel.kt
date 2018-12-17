package org.elephant.video.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import org.elephant.video.bean.VideoBean
import org.elephant.video.repository.RemoteDataRepository

/**
 * @author YangJ
 * @date 2018/10/30
 */
class TabPagerViewModel(repository: RemoteDataRepository, id: Int?) : ViewModel() {

    private val mLiveData by lazy { repository.getVideoBean(id) }

    fun getLiveData(): LiveData<List<VideoBean>> {
        return mLiveData
    }
}