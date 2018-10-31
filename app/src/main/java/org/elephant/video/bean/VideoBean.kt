package org.elephant.video.bean

/**
 * @author YangJ
 * @date 2018/10/30
 */
class VideoBean {

    var title: String? = null
    var icon: String? = null
    var playUrl: String? = null

    constructor(title: String?, icon: String?, playUrl: String?) {
        this.title = title
        this.icon = icon
        this.playUrl = playUrl
    }
}