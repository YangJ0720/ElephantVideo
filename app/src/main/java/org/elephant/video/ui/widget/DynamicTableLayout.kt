package org.elephant.video.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import org.elephant.video.R
import org.elephant.video.bean.PersonServiceBean

/**
 * Created by YangJ on 2019/4/11.
 */
class DynamicTableLayout : TableLayout {

    private var mNumColumns = DEFAULT_NUM_COLUMNS
    private lateinit var mInflater: LayoutInflater

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        // 解析控件布局属性
        val array = context.obtainStyledAttributes(attrs, R.styleable.DynamicTableLayoutStyleable)
        mNumColumns = array.getInteger(R.styleable.DynamicTableLayoutStyleable_numColumns, DEFAULT_NUM_COLUMNS)
        array.recycle()
        // 初始化LayoutInflater
        mInflater = LayoutInflater.from(context)
    }

    /**
     * 根据list添加TableRow
     */
    fun addView(list: List<PersonServiceBean>) {
        // 对list长度进行判断
        val size = list.size
        if (size < 1) return
        // 对子控件数量进行判断，如果有子控件就先移除
        if (childCount > 0) {
            removeAllViews()
        }
        // 对list进行遍历
        var tableRow = TableRow(context)
        list.forEachIndexed { index, bean ->
            if (index % mNumColumns == mNumColumns - 1) {
                addView(tableRow)
                if (tableRow != null) {
                    tableRow = TableRow(context)
                }
            }
            val childView = mInflater.inflate(R.layout.item_person_service, tableRow)
            val params = TableLayout.LayoutParams(0, 0, 1.0f)
            childView.layoutParams = params
            childView.findViewById<TextView>(R.id.tvService).text = bean.name
            childView.findViewById<ImageView>(R.id.ivService).setImageResource(bean.icon)
            childView.setBackgroundResource(R.color.colorAccent)
        }
    }

    companion object {
        private const val DEFAULT_NUM_COLUMNS = 1
    }

}