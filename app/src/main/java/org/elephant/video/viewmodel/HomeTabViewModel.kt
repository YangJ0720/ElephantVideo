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
class HomeTabViewModel(repository: RemoteDataRepository) : ViewModel() {

    private var mLiveData: LiveData<BaseResponse<TodayVideoBean>>? = null

    private var mVideoHomeTabLiveData: LiveData<BaseResponse<List<VideoHomeTabBean>>>? = null

    private var mVideoCategoryLiveData: LiveData<BaseResponse<VideoCategoryBean>>? = null

    init {
        mLiveData = repository.getToDayVideo()
        mVideoHomeTabLiveData = repository.getVideoHomeTab()
        mVideoCategoryLiveData = repository.getVideoCategory()
    }

    fun getLiveData(): LiveData<BaseResponse<TodayVideoBean>>? {
        return mLiveData
    }

    fun getVideoHomeTabLiveData(): LiveData<BaseResponse<List<VideoHomeTabBean>>>? {
        return mVideoHomeTabLiveData
    }

    fun getVideoCategoryLiveData(): LiveData<BaseResponse<VideoCategoryBean>>? {
        return mVideoCategoryLiveData
    }

}