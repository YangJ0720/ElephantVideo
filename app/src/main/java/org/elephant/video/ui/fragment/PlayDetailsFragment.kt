package org.elephant.video.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.elephant.video.R
import org.elephant.video.base.BaseLazyFragment

private const val ARG_PARAM_DESCRIPTION = "description"
private const val ARG_PARAM2 = "param2"

/**
 * @author YangJ 视频简介选项卡
 */
class PlayDetailsFragment : BaseLazyFragment() {

    private var mDescription: String? = null
    private var param2: String? = null

    override fun lazyLoad() {

    }

    override fun initData() {
        arguments?.let {
            mDescription = it.getString(ARG_PARAM_DESCRIPTION)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        val view = inflater.inflate(R.layout.fragment_play_details, container, false)
        view.findViewById<TextView>(R.id.tvDescription).text = mDescription
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlayDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM_DESCRIPTION, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
