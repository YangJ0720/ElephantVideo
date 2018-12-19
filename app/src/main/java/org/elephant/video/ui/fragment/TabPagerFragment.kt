package org.elephant.video.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import org.elephant.video.R
import org.elephant.video.adapter.VideoAdapter
import org.elephant.video.base.BaseLazyFragment
import org.elephant.video.bean.VideoBean
import org.elephant.video.databinding.FragmentTabPagerBinding
import org.elephant.video.listener.SmartRVScrollListener
import org.elephant.video.ui.activity.PlayerActivity
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
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mRecyclerView: RecyclerView

    override fun initData() {
        mData = ArrayList()
        mAdapter = VideoAdapter(R.layout.item_tab_home, mData)
        mAdapter?.setOnItemClickListener { adapter, _, position ->
            val bean = adapter.getItem(position) as VideoBean
            PlayerActivity.startActivity(context, bean.title, bean.playUrl, bean.description)
        }
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = FragmentTabPagerBinding.inflate(inflater, container, false)
        mSwipeRefreshLayout = binding.refreshLayout
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)
        mSwipeRefreshLayout.setOnRefreshListener {
            Handler().postDelayed({ mSwipeRefreshLayout.isRefreshing = false }, 3000)
        }
        mRecyclerView = binding.recyclerView
        mRecyclerView.adapter = mAdapter
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.addOnScrollListener(SmartRVScrollListener(context!!))
        return binding.root
    }

    override fun lazyLoad() {
        // 发送视频分类详情接口请求
        val factory = InjectorUtils.provideTabPagerViewModelFactory(arguments?.getInt(ARG_PARAM_ID))
        val model = ViewModelProviders.of(this, factory).get(TabPagerViewModel::class.java)
        model.getLiveData().observe(this, Observer<List<VideoBean>> { response ->
            response?.let {
                mData.addAll(it)
                mAdapter?.notifyItemInserted(0)
            }
            mSwipeRefreshLayout.isRefreshing = false
        })
    }

    fun reload() {
        mAdapter?.let {
            if (it.itemCount > 0) {
                val layoutManager = mRecyclerView.layoutManager as LinearLayoutManager
                val firstPosition = layoutManager.findFirstVisibleItemPosition()
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                it.notifyItemRangeChanged(firstPosition, lastPosition - firstPosition)
            }
        }
    }

    fun clearMemory() {
        val layoutManager = mRecyclerView.layoutManager as LinearLayoutManager
        val firstPosition = layoutManager.findFirstVisibleItemPosition()
        val lastPosition = layoutManager.findLastVisibleItemPosition()
        for (i in firstPosition until lastPosition) {
            val itemView = mAdapter?.getViewByPosition(mRecyclerView, i, R.id.ivIcon) ?: continue
            Glide.with(context!!).clear(itemView)
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
