package org.elephant.video.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import org.elephant.video.R
import org.elephant.video.adapter.VideoAdapter
import org.elephant.video.base.BaseLazyFragment
import org.elephant.video.bean.VideoBean
import org.elephant.video.common.CommonAdapter
import org.elephant.video.common.CommonViewHolder
import org.elephant.video.databinding.FragmentTabPagerBinding
import org.elephant.video.listener.SmartRVScrollListener
import org.elephant.video.ui.activity.PlayerActivity
import org.elephant.video.ui.widget.RefreshLayout
import org.elephant.video.utils.InjectorUtils
import org.elephant.video.viewmodel.TabPagerViewModel

/**
 * @author YangJ
 */
class TabPagerFragment : BaseLazyFragment() {

    // Data
    private lateinit var mData: ArrayList<VideoBean>
    private var mAdapter: VideoAdapter? = null
    // View
    private lateinit var mRefreshLayout: RefreshLayout
    private lateinit var mRecyclerView: RecyclerView

    override fun initData() {
        mData = ArrayList()
        mAdapter = VideoAdapter(context!!, R.layout.item_tab_home, mData)
        mAdapter?.setOnItemClickListener(object : CommonAdapter.OnItemClickListener {
            override fun onItemClick(adapter: CommonAdapter<*, *>, view: View, position: Int) {
                val item = adapter.getItem(position) as VideoBean
                PlayerActivity.startActivity(context, item.title, item.playUrl, item.description)
            }

        })
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = FragmentTabPagerBinding.inflate(inflater, container, false)
        mRefreshLayout = binding.refreshLayout
        mRefreshLayout.setOnRetryClickListener(object : RefreshLayout.OnRetryClickListener {
            override fun onRetry() {
                requestVideoList()
            }
        })
        val recyclerView = binding.recyclerView
        recyclerView.adapter = mAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addOnScrollListener(SmartRVScrollListener(context!!))
        mRecyclerView = recyclerView
        return binding.root
    }

    override fun lazyLoad() {
        requestVideoList()
    }

    private fun requestVideoList() {
        // 显示正在加载数据
        mRefreshLayout.showLoading(true)
        // 发送视频分类详情接口请求
        val factory = InjectorUtils.provideTabPagerViewModelFactory(arguments?.getInt(ARG_PARAM_ID))
        val model = ViewModelProviders.of(this, factory).get(TabPagerViewModel::class.java)
        model.getLiveData().observe(this, Observer<List<VideoBean>> { response ->
            // 隐藏正在加载数据
            mRefreshLayout.showLoading(false)
            if (response == null) {
                // 显示加载数据失败
                mRefreshLayout.showLoadFail(true)
            } else {
                if (response.isNotEmpty()) {
                    mData.addAll(response)
                    mAdapter?.notifyItemInserted(0)
                } else {
                    // 显示暂无数据
                    mRefreshLayout.showLoadEmpty(true)
                }
            }
        })
    }

    fun reload() {
        mAdapter?.let { adapter ->
            if (adapter.itemCount <= 0) return
            val layoutManager = mRecyclerView.layoutManager as LinearLayoutManager
            val firstPosition = layoutManager.findFirstVisibleItemPosition()
            val lastPosition = layoutManager.findLastVisibleItemPosition()
            adapter.notifyItemRangeChanged(firstPosition, lastPosition - firstPosition + 1)
        }
    }

    fun clearMemory() {
        mAdapter?.let { adapter ->
            if (adapter.itemCount <= 0) return
            val layoutManager = mRecyclerView.layoutManager as LinearLayoutManager
            val firstPosition = layoutManager.findFirstVisibleItemPosition()
            val lastPosition = layoutManager.findLastVisibleItemPosition()
            for (i in firstPosition..lastPosition) {
                val holder = mRecyclerView.findViewHolderForLayoutPosition(i)
                if (holder is CommonViewHolder) {
                    val imageView = holder.getViewById<ImageView>(R.id.ivIcon)
                    Glide.with(context!!).clear(imageView)
                }
            }
        }
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