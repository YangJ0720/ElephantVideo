package org.elephant.video.bean

import com.google.gson.annotations.SerializedName

/**
 * Auto-generated: 2018-10-26 16:3:37
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
class Result {

    @SerializedName("adIndex")
    var adindex: Int = 0
    var data: Data? = null
    var id: Int = 0
    var type: String? = null

    override fun toString(): String {
        return "Result(adindex=$adindex, data=$data, id=$id, type=$type)"
    }

}