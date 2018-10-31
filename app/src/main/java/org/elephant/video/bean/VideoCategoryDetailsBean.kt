package org.elephant.video.bean

/**
 * @author YangJ
 * @date 2018/10/30
 */
class VideoCategoryDetailsBean {

    var id = 0
    var data: Data? = null

    inner class Data {

        var content: Content? = null
        var header: Header? = null

        inner class Content {

            var data: Data? = null

            inner class Data {
                var description: String? = null
                var playUrl: String? = null
            }
        }

        inner class Header {

            var icon: String? = null
            var time: Long? = null
            var title: String? = null

        }

    }

}