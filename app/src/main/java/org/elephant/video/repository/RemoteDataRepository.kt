package org.elephant.video.repository

import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.elephant.video.bean.BaseResponse
import org.elephant.video.bean.VideoBean
import yangj.mvvm.RetrofitApi
import yangj.mvvm.RetrofitManager

/**
 * @author YangJ
 * @date 2018/10/26
 */
class RemoteDataRepository {

    companion object {
        fun getInstance() = SingletonHolder.INSTANCE
    }

    private object SingletonHolder {
        val INSTANCE = RemoteDataRepository()
    }

    fun getToDayVideo(): MutableLiveData<BaseResponse<VideoBean>> {
        val data = MutableLiveData<BaseResponse<VideoBean>>()
        val api = RetrofitManager.getInstance().create(RetrofitApi::class.java)
        val observers = api?.toDayVideo()
        observers?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<BaseResponse<VideoBean>> {
                override fun onComplete() {
                    println("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    println("onSubscribe")
                }

                override fun onNext(t: BaseResponse<VideoBean>) {
                    println("onNext")
                    data.value = t
                }

                override fun onError(e: Throwable) {
                    println("onError: $e")
                }

            })
        return data
    }
}