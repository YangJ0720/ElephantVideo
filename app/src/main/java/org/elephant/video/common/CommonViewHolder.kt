package org.elephant.video.common

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup

/**
 * 功能描述
 * @author YangJ
 * @since 2019/5/6
 */
class CommonViewHolder : RecyclerView.ViewHolder {

    private var mSparseArray: SparseArray<View>

    constructor(itemView: View) : super(itemView) {
        var initialCapacity = 1
        if (itemView is ViewGroup) {
            initialCapacity = itemView.childCount
        }
        mSparseArray = SparseArray(initialCapacity)
    }

    fun <V : View> getViewById(resId: Int): V {
        // 优先从缓存集合中查找
        if (mSparseArray.indexOfKey(resId) >= 0) {
            return mSparseArray.get(resId) as V
        }
        var childView = itemView.findViewById<V>(resId)
        mSparseArray.put(resId, childView)
        return childView
    }

}