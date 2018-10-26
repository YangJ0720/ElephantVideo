package org.elephant.video.repository

import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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

    fun getToDayVideo(): MutableLiveData<VideoBean> {
        val data = MutableLiveData<VideoBean>()
        val api = RetrofitManager.getInstance().create(RetrofitApi::class.java)
        val observers = api?.toDayVideo()
        observers?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<VideoBean> {
                override fun onComplete() {
                    println("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    println("onSubscribe")
                }

                override fun onNext(t: VideoBean) {
                    data.value = t
                    println("onNext: $t")
                }

                override fun onError(e: Throwable) {
                    println("onError: $e")
                }

            })
        return data
    }
}