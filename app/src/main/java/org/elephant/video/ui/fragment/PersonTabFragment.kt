package org.elephant.video.ui.fragment

import android.os.Bundle
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null) {
            val binding = FragmentTabPersonBinding.inflate(inflater, container, false)
            mView = binding.root
        } else {
            val parent = mView?.parent
            if (parent != null) {
                (parent as ViewGroup).removeView(mView)
            }
        }
        return mView
    }

}