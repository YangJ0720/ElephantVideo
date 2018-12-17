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

    companion object {

        /**
         * 对服务端返回数据进行转换
         */
        fun convert(data: VideoCategoryDetailsBean.Data?): VideoBean {
            val bean = VideoBean()
            data?.let { it ->
                // header
                val header = it.header
                header?.let { h ->
                    bean.title = h.title
                    bean.icon = h.icon
                }
                // content data
                val contentData = it.content?.data
                contentData?.let { d ->
                    bean.duration = d.duration
                    bean.playUrl = d.playUrl
                    bean.description = d.description
                    // author
                    val author = d.author
                    author?.let { a ->
                        bean.authorName = a.name
                        bean.authorIcon = a.icon
                    }
                    // consumption
                    val consumption = d.consumption
                    consumption?.let { c ->
                        bean.collectionCount = c.collectionCount
                        bean.replyCount = c.replyCount
                        bean.shareCount = c.shareCount
                    }
                    // cover
                    val cover = d.cover
                    cover?.let { c ->
                        bean.detail = c.detail
                        bean.feed = c.feed
                        bean.homepage = c.homepage
                    }
                }
            }
            return bean
        }
    }

}