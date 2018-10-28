package org.elephant.video.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.elephant.video.R
import org.elephant.video.bean.VideoBean

/**
 * Created by YangJ on 2018/10/27.
 */
class VideoAdapter : BaseQuickAdapter<VideoBean, BaseViewHolder> {

    constructor(layoutResId: Int, data: MutableList<VideoBean>?) : super(layoutResId, data)
    constructor(data: MutableList<VideoBean>?) : super(data)
    constructor(layoutResId: Int) : super(layoutResId)

    override fun convert(helper: BaseViewHolder?, item: VideoBean?) {
        val tvTitle = helper?.getView<TextView>(R.id.tvTitle)
        tvTitle?.text = item?.data?.content?.data?.title
        println("item = $item")
    }
}