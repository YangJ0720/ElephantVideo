package org.elephant.video.adapter

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.elephant.video.R
import org.elephant.video.bean.VideoBean
import org.elephant.video.config.SmartGlideModule
import org.elephant.video.ui.widget.ConsumptionView
import org.elephant.video.utils.DateUtils

/**
 * Created by YangJ on 2018/10/27.
 */
class VideoAdapter : BaseQuickAdapter<VideoBean, BaseViewHolder> {

    private var mOptionsVideo: RequestOptions? = null
    private var mOptionsAuthor: RequestOptions? = null

    constructor(layoutResId: Int, data: MutableList<VideoBean>?) : super(layoutResId, data) {
        mOptionsVideo = RequestOptions().placeholder(R.drawable.ic_loading)
        mOptionsVideo?.priority(Priority.HIGH)
        mOptionsAuthor = RequestOptions.circleCropTransform()
    }

    override fun convert(helper: BaseViewHolder?, item: VideoBean?) {
        // 设置视频图片
        val ivIcon = helper?.getView<ImageView>(R.id.ivIcon)
        if (ivIcon != null) {
            Glide.with(mContext).load(item?.playUrl).thumbnail(0.1f).apply(mOptionsVideo!!).into(ivIcon)
        }
        // 设置视频标题
        val tvTitle = helper?.getView<TextView>(R.id.tvTitle)
        tvTitle?.text = item?.title
        // 设置视频时长
        val duration = DateUtils.convertPlayDuration(item?.duration)
        if (duration != null) {
            val tvDuration = helper?.getView<TextView>(R.id.tvDuration)
            tvDuration?.text = duration
        }
        // 设置视频作者姓名
        helper?.getView<TextView>(R.id.tvAuthorName)?.text = item?.authorName
        // 设置视频作者头像
        val ivAuthorIcon = helper?.getView<ImageView>(R.id.ivAuthorIcon)
        if (ivAuthorIcon != null) {
            Glide.with(mContext).load(item?.authorIcon).thumbnail(0.5f).apply(mOptionsAuthor!!).into(ivAuthorIcon)
        }
        // 设置收藏、评论、分享次数
        helper?.getView<ConsumptionView>(R.id.view)?.setCount(item?.collectionCount!!, item?.replyCount!!, item?.shareCount!!)
    }

}