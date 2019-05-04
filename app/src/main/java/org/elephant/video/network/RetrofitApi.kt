package yangj.mvvm

import io.reactivex.Observable
import org.elephant.video.bean.*
import org.elephant.video.network.bean.BaseResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @author YangJ
 * Created by YangJ on 2018/6/20.
 */
interface RetrofitApi {

    /**
     * 每日视频推荐接口
     */
    @GET("todayVideo")
    fun toDayVideo(): Observable<BaseResponse<TodayVideoBean>>

    /**
     * 视频大纲获取接口
     */
    @GET("videoHomeTab")
    fun videoHomeTab(): Observable<BaseResponse<List<VideoHomeTabBean>>>

    /**
     * 视频分类推荐接口
     */
    @GET("videoCategory")
    fun videoCategory(): Observable<BaseResponse<VideoCategoryBean>>

    /**
     * 视频分类详情接口
     */
    @GET("videoCategoryDetails")
    fun videoCategoryDetails(@Query("id") id: Int?): Observable<BaseResponse<List<VideoCategoryDetailsBean>>>

    /**
     * 开发者注册
     */
    @POST("developerRegister")
    fun developerRegister(@Query("name") name: String, @Query("passwd") passwd: String,
                          @Query("email") email: String): Observable<BaseResponse<DeveloperRegisterBean>>
}