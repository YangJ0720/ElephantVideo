package org.elephant.video.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.TextView
import org.elephant.video.R
import org.elephant.video.adapter.TabPagerAdapter
import org.elephant.video.base.BaseFragment
import org.elephant.video.bean.VideoHomeTabBean
import org.elephant.video.databinding.FragmentTabHomeBinding
import org.elephant.video.network.bean.BaseResponse
import org.elephant.video.utils.InjectorUtils
import org.elephant.video.viewmodel.HomeTabViewModel
import org.elephant.video.viewmodel.HomeTabViewModelFactory

/**
 * @author YangJ
 *
 */
class HomeTabFragment : BaseFragment() {

    private var mAdapter: TabPagerAdapter? = null

    private var mTabLayout: TabLayout? = null

    override fun initData() {
        val labels = ArrayList<String?>()
        val fragments = ArrayList<Fragment>()
        mAdapter = TabPagerAdapter(fragmentManager, fragments)
        // 请求视频大纲获取接口
        val factory = InjectorUtils.provideHomeTabViewModelFactory()
        val model = ViewModelProviders.of(this, factory).get(HomeTabViewModel::class.java)
        model.getVideoHomeTabLiveData()?.observe(this, Observer<BaseResponse<List<VideoHomeTabBean>>> { response ->
            val result = response?.result
            val size = result?.size
            for (i in 0 until size!!) {
                val id = result[i].id
                if (id < 0) {
                    continue
                }
                labels?.add(result[i].name)
                fragments?.add(TabPagerFragment.newInstance(result[i].id))
            }
            mAdapter?.notifyDataSetChanged()
            // 刷新ViewPager数据
            notifyTabPager(labels)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // 初始化界面布局
        if (mView == null) {
            val binding = FragmentTabHomeBinding.inflate(inflater, container, false)
            val viewPager = binding.viewPager
            viewPager.adapter = mAdapter
            mTabLayout = binding.tabLayout
            mTabLayout?.setupWithViewPager(viewPager)
            mView = binding.root
        } else {
            val parent = mView?.parent
            if (parent != null) {
                (parent as ViewGroup).removeView(mView)
            }
        }
        return mView
    }

    private fun notifyTabPager(names: ArrayList<String?>) {
        val tabCount = mTabLayout?.tabCount!!
        for (i in 0 until tabCount) {
            val tab = mTabLayout?.getTabAt(i)
            tab?.text = names[i]
        }
    }

}