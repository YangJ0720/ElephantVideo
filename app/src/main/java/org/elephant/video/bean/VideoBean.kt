package org.elephant.video.bean

/**
 * @author YangJ
 * @date 2018/10/26
 */
class VideoBean {

    private var code: Int = 0
    private var message: String? = null
    private var result: List<Result>? = null
    fun setCode(code: Int) {
        this.code = code
    }

    fun getCode(): Int {
        return code
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getMessage(): String? {
        return message
    }

    fun setResult(result: List<Result>) {
        this.result = result
    }

    fun getResult(): List<Result>? {
        return result
    }

}