package org.elephant.video.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.elephant.video.adapter.TabPagerAdapter
import org.elephant.video.base.BaseFragment
import org.elephant.video.bean.VideoHomeTabBean
import org.elephant.video.databinding.FragmentTabHomeBinding
import org.elephant.video.listener.SmartVPScrollListener
import org.elephant.video.network.bean.BaseResponse
import org.elephant.video.utils.InjectorUtils
import org.elephant.video.utils.MemoryLeakUtils
import org.elephant.video.viewmodel.HomeTabViewModel

/**
 * 选项卡 -> 视频
 * @author YangJ
 *
 */
class HomeTabFragment : BaseFragment() {

    private lateinit var mAdapter: TabPagerAdapter

    private lateinit var mTabLayout: TabLayout

    override fun initData() {
        val labels = ArrayList<String?>()
        val ids = ArrayList<Int>()
        mAdapter = TabPagerAdapter(childFragmentManager, ids)
        // 请求视频大纲获取接口
        val factory = InjectorUtils.provideHomeTabViewModelFactory()
        val model = ViewModelProviders.of(this, factory).get(HomeTabViewModel::class.java)
        model.getVideoHomeTabLiveData()
            .observe(this, Observer<BaseResponse<List<VideoHomeTabBean>>> { response ->
                response?.let { rsp ->
                    val result = rsp.result
                    val size = result?.size
                    if (size!! <= 0) {
                        return@let
                    }
                    for (i in 0 until size) {
                        val id = result[i].id
                        if (id < 0) {
                            continue
                        }
                        labels.add(result[i].name)
                        ids.add(id)
                    }
                    mAdapter.notifyDataSetChanged()
                    // 刷新ViewPager数据
                    notifyTabPager(labels)
                }
            })
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = FragmentTabHomeBinding.inflate(inflater, container, false)
        val viewPager = binding.viewPager
        viewPager.addOnPageChangeListener(SmartVPScrollListener(context!!))
        viewPager.adapter = mAdapter
        viewPager.offscreenPageLimit = 5
        mTabLayout = binding.tabLayout
        mTabLayout.setupWithViewPager(viewPager, true)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MemoryLeakUtils.fixInputMethodMemoryLeak(context)
    }

    private fun notifyTabPager(names: ArrayList<String?>) {
        val tabCount = mTabLayout.tabCount
        for (i in 0 until tabCount) {
            val tab = mTabLayout.getTabAt(i)
            tab?.text = names[i]
        }
    }

}