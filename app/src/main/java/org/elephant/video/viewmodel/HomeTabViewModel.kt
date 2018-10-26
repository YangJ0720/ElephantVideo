package org.elephant.video.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import org.elephant.video.bean.VideoBean
import org.elephant.video.repository.RemoteDataRepository

/**
 * @author YangJ
 * @date 2018/10/26
 */
class HomeTabViewModel(application: Application) : AndroidViewModel(application) {

    private var mLiveData: LiveData<VideoBean>? = null

    init {
        mLiveData = RemoteDataRepository.getInstance().getToDayVideo()
    }

    fun getLiveData(): LiveData<VideoBean>? {
        return mLiveData
    }

}