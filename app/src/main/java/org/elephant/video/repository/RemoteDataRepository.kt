package org.elephant.video.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.elephant.video.network.bean.BaseResponse
import org.elephant.video.bean.TodayVideoBean
import org.elephant.video.bean.VideoCategoryBean
import org.elephant.video.bean.VideoCategoryDetailsBean
import org.elephant.video.bean.VideoHomeTabBean
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

    /**
     * 每日视频推荐接口
     */
    fun getToDayVideo(): MutableLiveData<BaseResponse<TodayVideoBean>> {
        val data = MutableLiveData<BaseResponse<TodayVideoBean>>()
        val api = RetrofitManager.getInstance().create(RetrofitApi::class.java)
        val observable = api?.toDayVideo()
        observable?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<BaseResponse<TodayVideoBean>> {
                override fun onComplete() {
                    println("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    println("onSubscribe")
                }

                override fun onNext(t: BaseResponse<TodayVideoBean>) {
                    println("onNext")
                    data.value = t
                }

                override fun onError(e: Throwable) {
                    println("onError: $e")
                }

            })
        return data
    }

    /**
     * 视频大纲获取接口
     */
    fun getVideoHomeTab(): LiveData<BaseResponse<List<VideoHomeTabBean>>> {
        val data = MutableLiveData<BaseResponse<List<VideoHomeTabBean>>>()
        val api = RetrofitManager.getInstance().create(RetrofitApi::class.java)
        val observable = api?.videoHomeTab()
        observable?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<BaseResponse<List<VideoHomeTabBean>>> {
                override fun onComplete() {
                    println("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    println("onSubscribe")
                }

                override fun onNext(t: BaseResponse<List<VideoHomeTabBean>>) {
                    println("onNext")
                    data.value = t
                }

                override fun onError(e: Throwable) {
                    println("onError: $e")
                }

            })
        return data
    }

    /**
     * 视频分类推荐接口
     */
    fun getVideoCategory(): MutableLiveData<BaseResponse<VideoCategoryBean>> {
        val data = MutableLiveData<BaseResponse<VideoCategoryBean>>()
        val api = RetrofitManager.getInstance().create(RetrofitApi::class.java)
        val observable = api?.videoCategory()
        observable?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<BaseResponse<VideoCategoryBean>> {
                override fun onComplete() {
                    println("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    println("onSubscribe")
                }

                override fun onNext(t: BaseResponse<VideoCategoryBean>) {
                    println("onNext")
                    data.value = t
                }

                override fun onError(e: Throwable) {
                    println("onError: $e")
                }

            })
        return data
    }

    /**
     * 视频分类详情接口
     */
    fun getVideoCategoryDetails(id: Int?): MutableLiveData<BaseResponse<List<VideoCategoryDetailsBean>>> {
        val data = MutableLiveData<BaseResponse<List<VideoCategoryDetailsBean>>>()
        val api = RetrofitManager.getInstance().create(RetrofitApi::class.java)
        val observable = api?.videoCategoryDetails(id)
        observable?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<BaseResponse<List<VideoCategoryDetailsBean>>> {
                override fun onComplete() {
                    println("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    println("onSubscribe")
                }

                override fun onNext(t: BaseResponse<List<VideoCategoryDetailsBean>>) {
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