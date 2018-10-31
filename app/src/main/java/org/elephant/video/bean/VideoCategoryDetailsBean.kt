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
                var author: Author? = null
                var consumption: Consumption? = null
                var description: String? = null
                var duration = 0
                var playUrl: String? = null

                inner class Author {
                    var icon: String? = null
                    var name: String? = null
                }

                inner class Consumption {
                    var collectionCount = 0
                    var replyCount = 0
                    var shareCount = 0
                }
            }
        }

        inner class Header {

            var icon: String? = null
            var time: Long? = null
            var title: String? = null

        }

    }

}