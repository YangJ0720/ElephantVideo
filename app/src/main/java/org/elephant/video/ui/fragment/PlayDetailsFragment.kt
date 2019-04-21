package org.elephant.video.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.elephant.video.R
import org.elephant.video.base.BaseLazyFragment

private const val ARG_PARAM_DESCRIPTION = "description"

/**
 * @author YangJ 视频简介选项卡
 */
class PlayDetailsFragment : BaseLazyFragment() {

    private var mDescription: String? = null

    override fun lazyLoad() {

    }

    override fun initData() {
        arguments?.let {
            mDescription = it.getString(ARG_PARAM_DESCRIPTION)
        }
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        val view = inflater.inflate(R.layout.fragment_play_details, container, false)
        view.findViewById<TextView>(R.id.tvDescription).text = mDescription
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(description: String) =
            PlayDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM_DESCRIPTION, description)
                }
            }
    }
}
