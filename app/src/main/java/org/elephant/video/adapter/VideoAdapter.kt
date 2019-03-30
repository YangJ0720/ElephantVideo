package org.elephant.video.adapter

import android.content.Context
import android.util.TypedValue
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

    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private val mOptionsVideo by lazy { RequestOptions().placeholder(R.drawable.ic_loading).override(mWidth, mHeight) }
    private val mOptionsAuthor by lazy { RequestOptions.circleCropTransform() }

    constructor(context: Context?, layoutResId: Int, data: MutableList<VideoBean>?) : super(layoutResId, data) {
        if (context != null) {
            val metrics = context.resources.displayMetrics
            mWidth = metrics.widthPixels
            val d = context.resources.getDimension(R.dimen.dp_150)
            mHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, d, metrics).toInt()
        }
    }

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
        val duration = DateUtils.convertPlayDuration(item.duration)
        if (duration != null) {
            val tvDuration = helper.getView<TextView>(R.id.tvDuration)
            tvDuration.text = duration
        }
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