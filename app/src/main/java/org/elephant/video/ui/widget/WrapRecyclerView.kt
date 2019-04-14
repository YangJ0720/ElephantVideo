package org.elephant.video.ui.widget

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by YangJ on 2019/4/14.
 */
class WrapRecyclerView : RecyclerView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun onViewRemoved(child: View?) {
        super.onViewRemoved(child)
        val tag = child?.tag
        println("onViewRemoved -> $tag")
        if (tag == null) return
        if (tag is ImageView) {
            Glide.with(tag).clear(tag)
        }
    }

}