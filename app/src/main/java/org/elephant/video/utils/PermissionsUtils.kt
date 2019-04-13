package org.elephant.video.utils

import android.app.Activity
import yangj.simplepermission.library.PermissionFragment
import yangj.simplepermission.library.PermissionListener

/**
 * Created by YangJ on 2019/4/10.
 */
object PermissionsUtils {

    /**
     * 判断权限是否申请
     * @param activity 参数为当前上下文对象
     * @param permissions 参数为申请的权限
     */
    fun checkPermissions(activity: Activity, permissions: Array<String>) {
        // 回调监听
        val listener = object : PermissionListener {
            override fun onGranted() {
                PortraitUtils.startSystemCamera(activity)
            }

            override fun onDenied(permissions: List<String>) {
                permissions.forEach {
                    println("onDenied = $it")
                }
            }
        }
        // 申请权限
        PermissionFragment.requestPermission(activity, permissions, listener)
    }
}