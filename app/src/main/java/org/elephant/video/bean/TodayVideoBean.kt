package org.elephant.video.bean

/**
 * @author YangJ
 * @date 2018/10/26
 */
class TodayVideoBean {

    var adIndex = 0

    var data: VideoData? = null

    var id = 0

    var type: String? = null

    inner class VideoData {

        var content: VideoContent? = null

        var dataType: String? = null

        var header: VideoHeader? = null

        inner class VideoContent {
            var adIndex = 0
            var data: VideoContentData? = null
            var id = 0
            var type: String? = null

            inner class VideoContentData {
                var createTime: Long? = null
                var status: String? = null
                var playUrl: String? = null
                var title: String? = null
            }
        }

        inner class VideoHeader {
            var icon: String? = null
            var id = 0
        }
    }

}