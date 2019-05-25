package org.elephant.video.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.elephant.video.R
import org.elephant.video.bean.VideoBean
import org.elephant.video.common.CommonAdapter
import org.elephant.video.common.CommonViewHolder
import org.elephant.video.ui.widget.ConsumptionView
import org.elephant.video.utils.DateUtils

/**
 * Created by YangJ on 2018/10/27.
 */
class VideoAdapter : CommonAdapter<VideoBean, CommonViewHolder> {

    private val mOptionsVideo by lazy { RequestOptions().placeholder(R.drawable.ic_loading) }
    private val mOptionsAuthor by lazy { RequestOptions.circleCropTransform() }

    constructor(context: Context, layoutResId: Int, data: List<VideoBean>) : super(context, layoutResId, data)

    override fun onBindItemViewHolder(holder: CommonViewHolder, position: Int) {
        val item = getItem(position)
        // 设置视频图片
        val ivIcon = holder.getViewById<ImageView>(R.id.ivIcon)
        var homepage = Glide.with(ivIcon).asDrawable().load(item.homepage).apply(mOptionsVideo)
        val feed = Glide.with(ivIcon).asDrawable().load(item.feed).apply(mOptionsVideo).error(homepage)
        Glide.with(ivIcon).asDrawable().load(item.detail).error(feed).apply(mOptionsVideo).into(ivIcon)
        // 设置视频标题
        val tvTitle = holder.getViewById<TextView>(R.id.tvTitle)
        tvTitle.text = item.title
        // 设置视频时长
        val tvDuration = holder.getViewById<TextView>(R.id.tvDuration)
        tvDuration.text = DateUtils.convertPlayDuration(item.duration)
        // 设置视频作者姓名
        holder.getViewById<TextView>(R.id.tvAuthorName).text = item.authorName
        // 设置视频作者头像
        val ivAuthorIcon = holder.getViewById<ImageView>(R.id.ivAuthorIcon)
        Glide.with(ivAuthorIcon).asDrawable().load(item.authorIcon).apply(mOptionsAuthor).into(ivAuthorIcon)
        // 设置收藏、评论、分享次数
        holder.getViewById<ConsumptionView>(R.id.view).setCount(item.collectionCount, item.replyCount, item.shareCount)
        //
        onBindItemViewTag(holder.itemView, item.detail, item.authorIcon)
    }

    private fun onBindItemViewTag(itemView: View, cover: String?, author: String?) {
        var tag = itemView.tag
        if (tag == null) {
            tag = ItemViewTag()
            itemView.tag = tag
        }
        if (tag is ItemViewTag) {
            tag.mUrlCover = cover
            tag.mUrlAuthor = author
        }
    }

    override fun onViewAttachedToWindow(holder: CommonViewHolder) {
        super.onViewAttachedToWindow(holder)
        val tag = holder.itemView.tag as ItemViewTag
        val ivIcon = holder.getViewById<ImageView>(R.id.ivIcon)
        val ivAuthorIcon = holder.getViewById<ImageView>(R.id.ivAuthorIcon)
        Glide.with(ivIcon).load(tag.mUrlCover).into(ivIcon)
        Glide.with(ivAuthorIcon).load(tag.mUrlAuthor).into(ivAuthorIcon)
    }

    override fun onViewDetachedFromWindow(holder: CommonViewHolder) {
        super.onViewDetachedFromWindow(holder)
        val ivIcon = holder.getViewById<ImageView>(R.id.ivIcon)
        val ivAuthorIcon = holder.getViewById<ImageView>(R.id.ivAuthorIcon)
        Glide.with(ivIcon).clear(ivIcon)
        Glide.with(ivAuthorIcon).clear(ivAuthorIcon)
    }
}

class ItemViewTag {
    var mUrlCover: String? = null
    var mUrlAuthor: String? = null
}