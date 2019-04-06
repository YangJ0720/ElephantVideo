package org.elephant.video.adapter

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.elephant.video.R
import org.elephant.video.bean.VideoBean
import org.elephant.video.ui.widget.ConsumptionView
import org.elephant.video.utils.DateUtils

/**
 * Created by YangJ on 2018/10/27.
 */
class VideoAdapter : BaseQuickAdapter<VideoBean, BaseViewHolder> {

    private val mOptionsVideo by lazy { RequestOptions().placeholder(R.drawable.ic_loading) }
    private val mOptionsAuthor by lazy { RequestOptions.circleCropTransform() }

    constructor(layoutResId: Int, data: MutableList<VideoBean>?) : super(layoutResId, data)

    override fun convert(helper: BaseViewHolder, item: VideoBean) {
        // 设置视频图片
        val ivIcon = helper.getView<ImageView>(R.id.ivIcon)
        ivIcon?.let { iv ->
            var homepage = Glide.with(iv).asDrawable().load(item.homepage).apply(mOptionsVideo)
            val feed = Glide.with(iv).asDrawable().load(item.feed).apply(mOptionsVideo).error(homepage)
            Glide.with(iv).asDrawable().load(item.detail).error(feed).apply(mOptionsVideo).into(iv)
        }
        // 设置视频标题
        val tvTitle = helper.getView<TextView>(R.id.tvTitle)
        tvTitle.text = item.title
        // 设置视频时长
        val tvDuration = helper.getView<TextView>(R.id.tvDuration)
        tvDuration.text = DateUtils.convertPlayDuration(item.duration)
        // 设置视频作者姓名
        helper.getView<TextView>(R.id.tvAuthorName).text = item.authorName
        // 设置视频作者头像
        val ivAuthorIcon = helper.getView<ImageView>(R.id.ivAuthorIcon)
        ivAuthorIcon?.let { iv ->
            Glide.with(iv).asDrawable().load(item.authorIcon).apply(mOptionsAuthor).into(iv)
        }
        // 设置收藏、评论、分享次数
        helper.getView<ConsumptionView>(R.id.view).setCount(item.collectionCount, item.replyCount, item.shareCount)
    }

}