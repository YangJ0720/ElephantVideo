package org.elephant.video.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author YangJ
 * @date 2018/11/1
 */
abstract class BaseLazyFragment : BaseFragment() {

    private var mIsFirstLoad = false
    private var mIsCreateView = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        mIsCreateView = true
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (userVisibleHint){
            lazyLoad()
            mIsFirstLoad = true
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && mIsCreateView && !mIsFirstLoad) {
            lazyLoad()
            mIsFirstLoad = true
        }
    }

    /**
     * 数据懒加载
     */
    abstract fun lazyLoad()

}