package yangj.mvvm

import io.reactivex.Observable
import org.elephant.video.bean.VideoBean
import retrofit2.http.GET

/**
 * @author YangJ
 * Created by YangJ on 2018/6/20.
 */
interface RetrofitApi {

    @GET("todayVideo")
    fun toDayVideo() : Observable<VideoBean>

}