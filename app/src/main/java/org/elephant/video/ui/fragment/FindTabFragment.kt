package org.elephant.video.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.elephant.video.databinding.FragmentTabFindBinding

/**
 * @author YangJ
 */
class FindTabFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTabFindBinding.inflate(inflater, container, false)
        return binding.root
    }

}