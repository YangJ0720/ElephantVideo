package org.elephant.video.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

/**
 * @author YangJ
 * @date 2018/10/29
 */
abstract class BaseFragment : Fragment() {

    protected var mView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    abstract fun initData()
}