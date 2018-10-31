package org.elephant.video.ui.widget

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import org.elephant.video.R

/**
 * @author YangJ
 * @date 2018/10/31
 */
class ConsumptionView : View {

    /**
     * 评论、收藏、分享图标的间隔大小
     */
    private var mSpaceBetween = 0.0f

    /**
     * 画笔
     */
    private var mPaint: Paint? = null

    /**
     * 收藏
     */
    private var mCollectionRect: Rect? = null
    private var mCollectionIcon: Bitmap? = null
    private var mCollectionIconWidth = 0
    /**
     * 收藏次数
     */
    private var mCollectionCount = 0

    /**
     * 评论
     */
    private var mReplyRect: Rect? = null
    private var mReplyIcon: Bitmap? = null
    private var mReplyIconWidth = 0
    /**
     * 评论次数
     */
    private var mReplyCount = 0

    /**
     * 分享
     */
    private var mShareRect: Rect? = null
    private var mShareIcon: Bitmap? = null
    private var mShareIconWidth = 0
    /**
     * 分享次数
     */
    private var mShareCount = 0

    constructor(context: Context?) : super(context) {
        initialize(context, null, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(context, attrs, defStyleAttr)
    }

    private fun initialize(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.ConsumptionViewStyleable, defStyleAttr, 0)
        mSpaceBetween = typedArray?.getDimension(R.styleable.ConsumptionViewStyleable_space_between, 0.0f)!!
        val textSize = typedArray?.getDimension(R.styleable.ConsumptionViewStyleable_text_size, 36.0f)
        typedArray?.recycle()
        // 初始化画笔
        mPaint = Paint()
        mPaint?.isAntiAlias = true
        mPaint?.textSize = textSize
        mPaint?.color = Color.DKGRAY
        //
        mCollectionRect = Rect()
        mReplyRect = Rect()
        mShareRect = Rect()
        // 初始化bitmap
        mCollectionIcon = convertBitmap(R.drawable.ic_grade_black_24dp)
        mCollectionIconWidth = mCollectionIcon?.width!!
        mReplyIcon = convertBitmap(R.drawable.ic_chat_bubble_outline_black_24dp)
        mReplyIconWidth = mReplyIcon?.width!!
        mShareIcon = convertBitmap(R.drawable.ic_share_black_24dp)
        mShareIconWidth = mShareIcon?.width!!
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = mCollectionIcon?.width!! + mReplyIcon?.width!! + mShareIcon?.width!! + (mSpaceBetween * 4).toInt()
        var height = mCollectionIcon?.height!!
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        // 绘制收藏图标
        canvas?.drawBitmap(mCollectionIcon, mSpaceBetween, 0.0f, mPaint)
        // 绘制评论图标
        canvas?.drawBitmap(mReplyIcon, mCollectionIconWidth + mSpaceBetween * 2, 0.0f, mPaint)
        // 绘制分享图标
        canvas?.drawBitmap(mShareIcon, mCollectionIconWidth + mReplyIconWidth + mSpaceBetween * 3, 0.0f, mPaint)
        // 绘制收藏次数
        canvas?.drawText(mCollectionCount.toString(), mCollectionIconWidth + mSpaceBetween
                - mCollectionRect?.width()!!, mCollectionRect?.height()?.toFloat()!!, mPaint
        )
        // 绘制评论次数
        canvas?.drawText(mReplyCount.toString(), mCollectionIconWidth + mReplyIconWidth + mSpaceBetween * 2
                - mReplyRect?.width()!!, mReplyRect?.height()?.toFloat()!!, mPaint
        )
        // 绘制分享次数
        canvas?.drawText(mShareCount.toString(), mCollectionIconWidth + mReplyIconWidth + mShareIconWidth + mSpaceBetween * 3
                - mShareRect?.width()!!, mShareRect?.height()?.toFloat()!!, mPaint
        )
    }

    /**
     * 将vector转换为bitmap
     */
    private fun convertBitmap(resId: Int): Bitmap {
        var bitmap: Bitmap
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val drawable = context.getDrawable(resId)
            bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.RGB_565)
            val canvas = Canvas(bitmap)
            canvas.drawColor(Color.WHITE)
            drawable?.setBounds(0, 0, canvas.width, canvas.height)
            drawable?.draw(canvas)
        } else {
            bitmap = BitmapFactory.decodeResource(resources, resId)
        }
        return bitmap
    }

    /**
     * 设置收藏、评论、分享次数
     */
    fun setCount(collectionCount: Int, replyCount: Int, shareCount: Int) {
        // 测量文本的宽度和高度
        mPaint?.getTextBounds(collectionCount.toString(), 0, collectionCount.toString().length, mCollectionRect)
        mPaint?.getTextBounds(replyCount.toString(), 0, replyCount.toString().length, mReplyRect)
        mPaint?.getTextBounds(shareCount.toString(), 0, shareCount.toString().length, mShareRect)
        // 对成员变量赋值
        this.mCollectionCount = collectionCount
        this.mReplyCount = replyCount
        this.mShareCount = shareCount
        invalidate()
    }
}