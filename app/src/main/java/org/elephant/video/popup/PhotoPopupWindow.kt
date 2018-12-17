package org.elephant.video.popup

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.LinearLayout
import android.widget.PopupWindow
import kotlinx.android.synthetic.main.popup_window_photo.view.*
import org.elephant.video.R

private const val POPUP_WINDOW_ALPHA_SHOW = 0.5f
private const val POPUP_WINDOW_ALPHA_HIDE = 1.0f

/**
 * 更换用户头像PopupWindow
 * Created by YangJ on 2018/12/17.
 */
class PhotoPopupWindow : PopupWindow {

    private var mWindow: Window
    private var mParams: WindowManager.LayoutParams

    constructor(context: Context?, listener: OnItemClickListener) : super(context) {
        mWindow = (context as Activity).window
        mParams = mWindow.attributes
        // 设置需要显示的界面布局
        val view = LayoutInflater.from(context).inflate(R.layout.popup_window_photo, null)
        view.tvPhoto.setOnClickListener { it ->
            listener?.onItemClick(it.id)
            dismiss()
        }
        view.tvCamera.setOnClickListener { it ->
            listener?.onItemClick(it.id)
            dismiss()
        }
        view.tvCancel.setOnClickListener { dismiss() }
        contentView = view
        // 设置界面布局宽度
        width = LinearLayout.LayoutParams.MATCH_PARENT
        // 设置界面布局高度
        height = LinearLayout.LayoutParams.WRAP_CONTENT
        // 设置PopupWindow默认获取焦点
        isFocusable = true
        // 设置动画样式
        animationStyle = R.style.PortraitPopupWindowTheme
        // 设置点击PopupWindow以外的区域可以使其关闭
        isOutsideTouchable = true
        // 设置背景色
        setBackgroundDrawable(ColorDrawable(Color.WHITE))
        // 设置PopupWindow关闭监听
        setOnDismissListener { setAttributes(POPUP_WINDOW_ALPHA_HIDE) }
    }

    /**
     * 显示PopupWindow
     */
    fun show(view: View) {
        setAttributes(POPUP_WINDOW_ALPHA_SHOW)
        showAtLocation(view, Gravity.BOTTOM, 0, 0)
    }

    /**
     * 设置透明度
     */
    private fun setAttributes(alpha: Float) {
        mParams.alpha = alpha
        mWindow.attributes = mParams
    }

    interface OnItemClickListener {
        fun onItemClick(id: Int)
    }

}