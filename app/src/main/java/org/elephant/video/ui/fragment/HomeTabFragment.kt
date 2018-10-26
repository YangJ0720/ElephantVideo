package org.elephant.video.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.elephant.video.bean.VideoBean
import org.elephant.video.databinding.FragmentTabHomeBinding
import org.elephant.video.viewmodel.HomeTabViewModel
import java.util.*

/**
 * @author YangJ
 *
 */
class HomeTabFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTabHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initData() {
        val model = ViewModelProviders.of(this).get(HomeTabViewModel::class.java)
        model.getLiveData()?.observe(this, Observer<VideoBean> { bean ->
            println("bean = $bean")
            val list = bean?.getResult()
            val size = list?.size!!
            for (i in 0 until size) {
                val result = list[i]
                println("$i = ${result?.toString()}")
            }
        })
    }

}