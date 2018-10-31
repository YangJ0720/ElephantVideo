package org.elephant.video.adapter

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.elephant.video.R
import org.elephant.video.bean.VideoBean

/**
 * Created by YangJ on 2018/10/27.
 */
class VideoAdapter : BaseQuickAdapter<VideoBean, BaseViewHolder> {

    constructor(layoutResId: Int, data: MutableList<VideoBean>?) : super(layoutResId, data)

    override fun convert(helper: BaseViewHolder?, item: VideoBean?) {
        val ivIcon = helper?.getView<ImageView>(R.id.ivIcon)
        if (ivIcon != null) {
            Glide.with(mContext).load(item?.icon).into(ivIcon)
        }
        val tvTitle = helper?.getView<TextView>(R.id.tvTitle)
        tvTitle?.text = item?.title
    }

}