package org.elephant.video.bean

import java.util.*

/**
 * Created by YangJ on 2018/10/27.
 */
class BaseResponse<T> {

    var code = 0

    var message: String? = null

    var result: ArrayList<T>? = null

}