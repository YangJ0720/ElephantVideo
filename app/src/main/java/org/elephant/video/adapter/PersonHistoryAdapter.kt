package org.elephant.video.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.elephant.video.R
import org.elephant.video.bean.VideoHistory

/**
 * 历史记录列表数据适配器
 * Created by YangJ on 2019/4/13.
 */
class PersonHistoryAdapter : BaseQuickAdapter<VideoHistory, BaseViewHolder> {

    constructor(layoutResId: Int, data: MutableList<VideoHistory>?) : super(layoutResId, data)
    constructor(data: MutableList<VideoHistory>?) : super(data)
    constructor(layoutResId: Int) : super(layoutResId)

    override fun convert(helper: BaseViewHolder, item: VideoHistory) {
        helper.getView<TextView>(R.id.tvHistory).text = item.name
    }

}