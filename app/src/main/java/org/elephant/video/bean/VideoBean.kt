package org.elephant.video.bean

/**
 * @author YangJ
 * @date 2018/10/30
 */
class VideoBean {

    /**
     * 视频标题
     */
    var title: String? = null
    /**
     * 视频图片
     */
    var icon: String? = null
    /**
     * 视频时长
     */
    var duration: Int? = null
    /**
     * 视频播放地址
     */
    var playUrl: String? = null
    /**
     * 视频描述
     */
    var description: String? = null
    /**
     * 视频作者姓名
     */
    var authorName: String? = null
    /**
     * 视频作者头像
     */
    var authorIcon: String? = null
    /**
     * 收藏次数
     */
    var collectionCount = 0
    /**
     * 评论次数
     */
    var replyCount = 0
    /**
     * 分享次数
     */
    var shareCount = 0

    var detail: String? = null
    var feed: String? = null
    var homepage: String? = null

    constructor(title: String?, icon: String?, duration: Int?, playUrl: String?, description: String?,
                authorName: String?, authorIcon: String?, collectionCount: Int, replyCount: Int, shareCount: Int,
                detail: String?, feed: String?, homepage: String?) {
        this.title = title
        this.icon = icon
        this.duration = duration
        this.playUrl = playUrl
        this.description = description
        this.authorName = authorName
        this.authorIcon = authorIcon
        this.collectionCount = collectionCount
        this.replyCount = replyCount
        this.shareCount = shareCount
        this.detail = detail
        this.feed = feed
        this.homepage = homepage
    }
}