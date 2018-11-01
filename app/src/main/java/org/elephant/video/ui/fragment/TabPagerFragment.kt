package org.elephant.video.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.elephant.video.R
import org.elephant.video.adapter.VideoAdapter
import org.elephant.video.base.BaseLazyFragment
import org.elephant.video.bean.VideoBean
import org.elephant.video.bean.VideoCategoryDetailsBean
import org.elephant.video.databinding.FragmentTabPagerBinding
import org.elephant.video.network.bean.BaseResponse
import org.elephant.video.ui.activity.PlayerActivity
import org.elephant.video.utils.InjectorUtils
import org.elephant.video.viewmodel.TabPagerViewModel

/**
 * @author YangJ
 */
class TabPagerFragment : BaseLazyFragment() {

    // Data
    private var mData: ArrayList<VideoBean>? = null
    private var mAdapter: VideoAdapter? = null
    // View
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    override fun initData() {
        mData = ArrayList()
        mAdapter = VideoAdapter(R.layout.item_tab_home, mData)
        mAdapter?.setOnItemClickListener { adapter, _, position ->
            val bean = adapter?.getItem(position) as VideoBean
            PlayerActivity.startActivity(context, bean?.title, bean?.playUrl, bean?.description)
        }
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = FragmentTabPagerBinding.inflate(inflater, container, false)
        mSwipeRefreshLayout = binding.refreshLayout
        mSwipeRefreshLayout?.setColorSchemeResources(R.color.colorAccent)
        mSwipeRefreshLayout?.setOnRefreshListener {
            Handler().postDelayed({
                mSwipeRefreshLayout?.isRefreshing = false
            }, 3000)
        }
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return binding.root
    }

    override fun lazyLoad() {
        // 发送视频分类详情接口请求
        val factory = InjectorUtils.provideTabPagerViewModelFactory(arguments?.getInt(ARG_PARAM_ID))
        val model = ViewModelProviders.of(this, factory).get(TabPagerViewModel::class.java)
        model.getLiveData()?.observe(this, Observer<BaseResponse<List<VideoCategoryDetailsBean>>> { response ->
            val result = response?.result
            result?.forEach { it ->
                val data = it?.data
                val header = data?.header
                val content = data?.content
                val author = content?.data?.author
                val consumption = content?.data?.consumption
                mData?.add(VideoBean(header?.title, header?.icon, content?.data?.duration, content?.data?.playUrl,
                    content?.data?.description, author?.name, author?.icon, consumption?.collectionCount!!,
                    consumption?.replyCount, consumption?.shareCount))
            }
            mAdapter?.notifyItemInserted(0)
            mSwipeRefreshLayout?.isRefreshing = false
        })
    }

    companion object {
        private const val ARG_PARAM_ID = "id"
        @JvmStatic
        fun newInstance(id: Int) = TabPagerFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_PARAM_ID, id)
            }
        }
    }
}
