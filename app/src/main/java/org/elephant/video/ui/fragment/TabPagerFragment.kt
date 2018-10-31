package org.elephant.video.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.elephant.video.R
import org.elephant.video.adapter.VideoAdapter
import org.elephant.video.base.BaseFragment
import org.elephant.video.bean.VideoBean
import org.elephant.video.bean.VideoCategoryDetailsBean
import org.elephant.video.databinding.FragmentTabPagerBinding
import org.elephant.video.network.bean.BaseResponse
import org.elephant.video.ui.activity.PlayerActivity
import org.elephant.video.utils.InjectorUtils
import org.elephant.video.viewmodel.TabPagerViewModel

private const val ARG_PARAM_ID = "id"

/**
 * @author YangJ
 */
class TabPagerFragment : BaseFragment() {

    private var mData: ArrayList<VideoBean>? = null
    private var mAdapter: VideoAdapter? = null

    override fun initData() {
        val id = arguments?.getInt(ARG_PARAM_ID)
        mData = ArrayList()
        mAdapter = VideoAdapter(R.layout.item_tab_home, mData)
        mAdapter?.setOnItemClickListener { adapter, _, position ->
            val bean = adapter?.getItem(position) as VideoBean
            val intent = Intent(context, PlayerActivity::class.java)
            val bundle = Bundle()
            bundle.putString("title", bean?.title)
            bundle.putString("playUrl", bean?.playUrl)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        //
        val factory = InjectorUtils.provideTabPagerViewModelFactory(id)
        val model = ViewModelProviders.of(this, factory).get(TabPagerViewModel::class.java)
        model.getLiveData()?.observe(this, Observer<BaseResponse<List<VideoCategoryDetailsBean>>> { response ->
            val result = response?.result
            result?.forEach { it ->
                val data = it?.data
                val header = data?.header
                mData?.add(VideoBean(header?.title, header?.icon, data?.content?.data?.playUrl))
            }
            mAdapter?.notifyItemInserted(0)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null) {
            val binding = FragmentTabPagerBinding.inflate(inflater, container, false)
            binding.recyclerView.adapter = mAdapter
            binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
            mView = binding.root
        } else {
            val parent = mView?.parent
            if (parent != null) {
                (parent as ViewGroup).removeView(mView)
            }
        }
        return mView
    }

    companion object {
        @JvmStatic
        fun newInstance(id: Int) = TabPagerFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_PARAM_ID, id)
            }
        }
    }
}
