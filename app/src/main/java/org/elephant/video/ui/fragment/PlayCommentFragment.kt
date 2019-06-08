package org.elephant.video.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.elephant.video.R
import org.elephant.video.base.BaseLazyFragment

/**
 * @author YangJ 视频评论选项卡
 */
class PlayCommentFragment : BaseLazyFragment() {

    override fun lazyLoad() {

    }

    override fun initData() {

    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_play_comment, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlayCommentFragment()
    }
}
