package org.elephant.video.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import org.elephant.video.bean.VideoCategoryDetailsBean
import org.elephant.video.network.bean.BaseResponse
import org.elephant.video.repository.RemoteDataRepository

/**
 * @author YangJ
 * @date 2018/10/30
 */
class TabPagerViewModel(repository: RemoteDataRepository, id: Int?) : ViewModel() {

    private val mLiveData by lazy { repository.getVideoCategoryDetails(id) }

    fun getLiveData(): LiveData<BaseResponse<List<VideoCategoryDetailsBean>>> {
        return mLiveData
    }

}