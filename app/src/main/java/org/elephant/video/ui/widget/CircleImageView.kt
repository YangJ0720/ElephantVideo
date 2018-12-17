package org.elephant.video.ui.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by YangJ on 2018/12/17.
 */
class CircleImageView : ImageView {

    private val mOptions by lazy { RequestOptions().circleCrop() }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setImageBitmapByGlide(bm: Bitmap?) {
        Glide.with(this).load(bm).apply(mOptions).into(this)
    }

    fun setImageDrawableByGlide(drawable: Drawable?) {
        Glide.with(this).load(drawable).apply(mOptions).into(this)
    }

    fun setImageResourceByGlide(resId: Int) {
        Glide.with(this).load(resId).apply(mOptions).into(this)
    }

}