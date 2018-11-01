package org.elephant.video.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author YangJ
 * @date 2018/10/29
 */
abstract class BaseFragment : Fragment() {

    private var mView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null) {
            mView = initView(inflater, container)
        } else {
            val parent = mView?.parent
            if (parent != null) {
                (parent as ViewGroup).removeView(mView)
            }
        }
        return mView
    }

    /**
     * 初始化基础数据
     */
    abstract fun initData()

    /**
     * 初始化界面视图
     */
    abstract fun initView(inflater: LayoutInflater, container: ViewGroup?): View

}