package org.elephant.video.ui.widget

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import java.text.DecimalFormat

/**
 * Created by YangJ on 2019/4/12.
 */
class LoadingView : AppCompatImageView {

    private lateinit var mAnimation: AnimationDrawable

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize()
    }

    private fun initialize() {
        mAnimation = AnimationDrawable()
        mAnimation.isOneShot = false
        val format = DecimalFormat("loading_#00")
        val packageName = context.packageName
        for (i in 0..29) {
            val resId = resources.getIdentifier(format.format(i), "drawable", packageName)
            val bitmap = BitmapFactory.decodeResource(resources, resId)
            val drawable = BitmapDrawable(resources, bitmap)
            mAnimation.addFrame(drawable, DURATION)
        }
        background = mAnimation
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        if (View.VISIBLE == visibility) {
            if (mAnimation.isRunning) return
            mAnimation.start()
        } else {
            if (mAnimation.isRunning) {
                mAnimation.stop()
            }
        }
    }

    companion object {
        private const val DURATION = 5
    }

}