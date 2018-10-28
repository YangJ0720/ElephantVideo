package org.elephant.video.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.elephant.video.R
import org.elephant.video.adapter.VideoAdapter
import org.elephant.video.bean.BaseResponse
import org.elephant.video.bean.VideoBean
import org.elephant.video.databinding.FragmentTabHomeBinding
import org.elephant.video.viewmodel.HomeTabViewModel

/**
 * @author YangJ
 *
 */
class HomeTabFragment : Fragment() {

    private var mData: ArrayList<VideoBean>? = null
    private var mAdapter: VideoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTabHomeBinding.inflate(inflater, container, false)
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return binding.root
    }

    private fun initData() {
        mData = ArrayList()
        mAdapter = VideoAdapter(R.layout.item_tab_home, mData)
        // 请求服务器数据
        val model = ViewModelProviders.of(this).get(HomeTabViewModel::class.java)
        model.getLiveData()?.observe(this, Observer<BaseResponse<VideoBean>> { response ->
            val list = response?.result
            val size = list?.size!!
            println("size = $size")
            for (i in 0 until size) {
                val result = list[i]
                println("$i = ${result?.toString()}")
            }
            if (mData?.isNotEmpty()!!){
                mData?.clear()
            }
            mData?.addAll(list)
            mAdapter?.notifyItemInserted(0)
        })
    }

}