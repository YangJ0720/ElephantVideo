package org.elephant.video.bean

import com.google.gson.annotations.SerializedName

/**
 * Auto-generated: 2018-10-26 16:3:37
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
class Data {

    @SerializedName("dataType")
    var datatype: String? = null
    var id: Int = 0
    var text: String? = null
    var type: String? = null

    override fun toString(): String {
        return "Data(datatype=$datatype, id=$id, text=$text, type=$type)"
    }

}