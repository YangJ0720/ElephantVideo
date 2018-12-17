package yangj.mvvm

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.elephant.video.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author YangJ
 * Created by YangJ on 2018/6/20.
 */
class RetrofitManager private constructor() {

    companion object {
        fun getInstance() = SingletonHolder.INSTANCE
    }

    private object SingletonHolder {
        val INSTANCE = RetrofitManager()
    }

    private val mRetrofit by lazy {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(RetrofitConfig.TIMEOUT, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }
        val url = HttpUrl.parse(RetrofitConfig.HOST)
        Retrofit.Builder().baseUrl(url)
            .client(builder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun <T> create(cls: Class<T>): T {
        return mRetrofit.create(cls)
    }

}