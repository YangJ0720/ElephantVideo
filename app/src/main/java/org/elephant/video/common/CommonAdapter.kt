package org.elephant.video.common

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * 功能描述
 * @author YangJ
 * @since 2019/5/6
 */
abstract class CommonAdapter<M, V> : RecyclerView.Adapter<CommonViewHolder> {

    private var mInflater: LayoutInflater
    private var mLayoutResId: Int
    /**
     * RecyclerView数据源
     */
    private var mList: List<M>

    /**
     * item点击事件监听器
     */
    private var mOnItemClickListener: OnItemClickListener? = null

    constructor(context: Context, layoutResId: Int, list: List<M>) : super() {
        this.mInflater = LayoutInflater.from(context)
        this.mLayoutResId = layoutResId
        this.mList = list
    }

    fun getItem(position: Int): M {
        return mList[position]
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onCreateViewHolder(group: ViewGroup, viewType: Int): CommonViewHolder {
        val holder = CommonViewHolder(mInflater.inflate(mLayoutResId, group, false))
        bindItemViewClickListener(holder)
        return holder
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        onBindItemViewHolder(holder, position)
    }

    abstract fun onBindItemViewHolder(holder: CommonViewHolder, position: Int)

    private fun bindItemViewClickListener(holder: CommonViewHolder) {
        mOnItemClickListener?.let { listener ->
            val itemView = holder.itemView
            itemView.setOnClickListener {
                listener.onItemClick(this@CommonAdapter, holder.itemView, holder.layoutPosition)
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mOnItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(adapter: CommonAdapter<*, *>, view: View, position: Int)
    }
}