package org.elephant.video.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction

/**
 * @author YangJ
 * Created by YangJ on 2016/10/18.
 */
object FragmentManagerUtil {

    /**
     * 显示Fragment
     *
     * @param transaction
     * @param fragment
     */
    fun onShow(transaction: FragmentTransaction, containerViewId: Int, fragment: Fragment) {
        if (fragment.isAdded) {
            transaction.show(fragment)
        } else {
            transaction.add(containerViewId, fragment)
        }
        transaction.commitAllowingStateLoss()
    }

    /**
     * 隐藏Fragment
     *
     * @param transaction
     * @param fragment
     */
    fun onHide(transaction: FragmentTransaction, fragment: Fragment) {
        if (fragment.isVisible) {
            transaction.hide(fragment)
            transaction.commitAllowingStateLoss()
        }
    }

}
