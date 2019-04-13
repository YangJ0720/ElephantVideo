package org.elephant.video.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import org.elephant.video.R

/**
 * Created by YangJ on 2019/3/28.
 */
class RefreshLayout : FrameLayout {

    private var mViewLoadingResId = 0
    private var mViewLoading: View? = null

    private var mViewLoadEmptyResId = 0
    private var mViewLoadEmpty: View? = null

    private var mViewLoadFailResId = 0
    private var mViewLoadFail: View? = null

    // Listener
    private var mListener: OnRetryClickListener? = null

    constructor(context: Context) : super(context) {
        initialize(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.RefreshLayoutStyleable)
        mViewLoadingResId = array.getResourceId(R.styleable.RefreshLayoutStyleable_loading, 0)
        mViewLoadEmptyResId = array.getResourceId(R.styleable.RefreshLayoutStyleable_load_data_empty, 0)
        mViewLoadFailResId = array.getResourceId(R.styleable.RefreshLayoutStyleable_load_data_fail, 0)
        array.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 1) {
            throw IllegalStateException("RefreshLayout can only have one child.")
        }
    }

    override fun addView(child: View) {
        var count = childCount
        while (count > 1) {
            removeViewAt(--count)
        }
        super.addView(child)
    }

    private fun changed(childView: View, show: Boolean) {
        if (show) {
            addView(childView)
        } else {
            removeView(childView)
        }
    }

    fun showLoading(show: Boolean) {
        if (mViewLoadingResId == 0) return
        if (mViewLoading == null) {
            mViewLoading = View.inflate(context, mViewLoadingResId, null)
        }
        changed(mViewLoading!!, show)
    }

    fun showLoadEmpty(show: Boolean) {
        if (mViewLoadEmptyResId == 0) return
        if (mViewLoadEmpty == null) {
            mViewLoadEmpty = View.inflate(context, mViewLoadEmptyResId, null)
        }
        changed(mViewLoadEmpty!!, show)
    }

    fun showLoadFail(show: Boolean) {
        if (mViewLoadFailResId == 0) return
        if (mViewLoadFail == null) {
            mViewLoadFail = View.inflate(context, mViewLoadFailResId, null)
            mViewLoadFail?.setOnClickListener {
                mListener?.let { listener ->
                    showLoadFail(false)
                    listener.onRetry()
                }
            }
        }
        changed(mViewLoadFail!!, show)
    }

    fun setOnRetryClickListener(listener: OnRetryClickListener) {
        mListener = listener
    }

    interface OnRetryClickListener {
        fun onRetry()
    }

}