package org.elephant.video.bean

/**
 * @author YangJ
 * @date 2018/10/30
 */
class VideoCategoryBean {

    var adExist: Boolean = false

    var count = 0

     var itemList: ArrayList<ItemList>? = null

    var total = 0

    inner class ItemList {

        var adIndex = -1

        var data: Data? = null

        var id = 0

        var type: String? = null

        inner class Data {
            var icon: String? = null
            var title: String? = null
        }
    }

}