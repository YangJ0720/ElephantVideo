package org.elephant.video.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.elephant.video.bean.*
import org.elephant.video.network.bean.BaseResponse
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
        val observable = api.toDayVideo()
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RxSubscribe<TodayVideoBean>() {
                override fun onSuccess(t: BaseResponse<TodayVideoBean>) {
                    data.value = t
                }

                override fun onFailed(e: Throwable) {
                    data.value = null
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
        val observable = api.videoHomeTab()
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RxSubscribe<List<VideoHomeTabBean>>() {
                override fun onSuccess(t: BaseResponse<List<VideoHomeTabBean>>) {
                    data.value = t
                }

                override fun onFailed(e: Throwable) {
                    data.value = null
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
        val observable = api.videoCategory()
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RxSubscribe<VideoCategoryBean>() {
                override fun onSuccess(t: BaseResponse<VideoCategoryBean>) {
                    data.value = t
                }

                override fun onFailed(e: Throwable) {
                    data.value = null
                }

            })
        return data
    }

    /**
     * 视频分类详情接口
     */
    fun getVideoBean(id: Int?): MutableLiveData<List<VideoBean>> {
        val data = MutableLiveData<List<VideoBean>>()
        val api = RetrofitManager.getInstance().create(RetrofitApi::class.java)
        val observable = api.videoCategoryDetails(id)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RxSubscribe<List<VideoCategoryDetailsBean>>() {
                override fun onSuccess(t: BaseResponse<List<VideoCategoryDetailsBean>>) {
                    if (t.result == null) {
                        data.value = null
                    } else {
                        val list = ArrayList<VideoBean>(t.result!!.size)
                        t.result!!.forEach { it ->
                            val data = it.data
                            list.add(VideoBean.convert(data))
                        }
                        data.value = list
                    }
                }

                override fun onFailed(e: Throwable) {
                    data.value = null
                }

            })
        return data
    }

    abstract class RxSubscribe<T> : Observer<BaseResponse<T>> {

        override fun onComplete() {

        }

        override fun onSubscribe(d: Disposable) {

        }

        override fun onNext(t: BaseResponse<T>) {
            onSuccess(t)
        }

        override fun onError(e: Throwable) {
            onFailed(e)
        }

        /**
         * 数据加载成功
         */
        abstract fun onSuccess(t: BaseResponse<T>)

        /**
         * 数据加载失败
         */
        abstract fun onFailed(e: Throwable)
    }

}