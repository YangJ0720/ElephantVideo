package org.elephant.video.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import org.elephant.video.bean.TodayVideoBean
import org.elephant.video.bean.VideoCategoryBean
import org.elephant.video.bean.VideoHomeTabBean
import org.elephant.video.network.bean.BaseResponse
import org.elephant.video.repository.RemoteDataRepository

/**
 * @author YangJ
 * @date 2018/10/26
 */
class HomeTabViewModel(private var repository: RemoteDataRepository) : ViewModel() {

    fun getLiveData(): LiveData<BaseResponse<TodayVideoBean>> {
        return repository.getToDayVideo()
    }

    fun getVideoHomeTabLiveData(): LiveData<BaseResponse<List<VideoHomeTabBean>>> {
        return repository.getVideoHomeTab()
    }

    fun getVideoCategoryLiveData(): LiveData<BaseResponse<VideoCategoryBean>> {
        return repository.getVideoCategory()
    }

}