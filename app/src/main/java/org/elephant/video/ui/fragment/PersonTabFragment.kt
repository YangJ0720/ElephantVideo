package org.elephant.video.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.elephant.video.base.BaseFragment
import org.elephant.video.databinding.FragmentTabPersonBinding

/**
 * @author YangJ
 *
 */
class PersonTabFragment : BaseFragment() {

    override fun initData() {

    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = FragmentTabPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

}