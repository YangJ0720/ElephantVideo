package org.elephant.video.adapter

import android.content.Context
import android.widget.TextView
import org.elephant.video.R
import org.elephant.video.bean.VideoHistory
import org.elephant.video.common.CommonAdapter
import org.elephant.video.common.CommonViewHolder

/**
 * 历史记录列表数据适配器
 * Created by YangJ on 2019/4/13.
 */
class PersonHistoryAdapter(context: Context, layoutResId: Int, list: List<VideoHistory>) :
    CommonAdapter<VideoHistory, CommonViewHolder>(context, layoutResId, list) {

    override fun onBindItemViewHolder(holder: CommonViewHolder, position: Int) {
        val item = getItem(position)
        holder.getViewById<TextView>(R.id.tvHistory).text = item.name
    }

}