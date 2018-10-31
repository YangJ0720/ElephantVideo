package org.elephant.video.network.bean

/**
 * Created by YangJ on 2018/10/27.
 */
class BaseResponse<T> {

    var code = 0

    var message: String? = null

    var result: T? = null

}