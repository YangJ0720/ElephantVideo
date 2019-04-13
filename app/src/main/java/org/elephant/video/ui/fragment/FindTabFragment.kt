package org.elephant.video.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.elephant.video.base.BaseFragment
import org.elephant.video.databinding.FragmentTabFindBinding

/**
 * 选项卡 -> 发现
 * @author YangJ
 */
class FindTabFragment : BaseFragment() {

    override fun initData() {

    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = FragmentTabFindBinding.inflate(inflater, container, false)
        return binding.root
    }

}