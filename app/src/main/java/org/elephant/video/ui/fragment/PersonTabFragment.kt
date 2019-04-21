package org.elephant.video.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.elephant.video.R
import org.elephant.video.adapter.PersonHistoryAdapter
import org.elephant.video.base.BaseFragment
import org.elephant.video.bean.VideoHistory
import org.elephant.video.databinding.FragmentTabPersonBinding
import org.elephant.video.popup.PhotoPopupWindow
import org.elephant.video.ui.activity.HistoryActivity
import org.elephant.video.ui.activity.SettingsActivity
import org.elephant.video.ui.widget.CircleImageView
import org.elephant.video.utils.PortraitUtils
import yangj.simplepermission.library.PermissionFragment
import yangj.simplepermission.library.PermissionListener

/**
 * 选项卡 -> 我的
 * @author YangJ
 *
 */
class PersonTabFragment : BaseFragment() {

    private lateinit var mHistoryAdapter: PersonHistoryAdapter
    private lateinit var mIvPhoto: CircleImageView

    private val mPopupWindow by lazy {
        PhotoPopupWindow(context, object : PhotoPopupWindow.OnItemClickListener {
            override fun onItemClick(id: Int) {
                when (id) {
                    R.id.tvPhoto -> {
                        PortraitUtils.startSystemPhoto(this@PersonTabFragment)
                    }
                    R.id.tvCamera -> {
                        checkPermissions(arrayOf(Manifest.permission.CAMERA))
                    }
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Activity.RESULT_OK != resultCode) return
        when (requestCode) {
            PortraitUtils.REQUEST_CODE_PICK_PHOTO -> {
                data?.let {
                    PortraitUtils.startSystemCrop(this@PersonTabFragment, it.data, mIvPhoto.width, mIvPhoto.height)
                }
            }
            PortraitUtils.REQUEST_CODE_PICK_CAMERA -> {
                val context = context ?: return
                val uri = PortraitUtils.getUriByCamera(context)
                PortraitUtils.startSystemCrop(this@PersonTabFragment, uri, mIvPhoto.width, mIvPhoto.height)
            }
            PortraitUtils.REQUEST_CODE_PICK_CROP -> {
                data?.let {
                    val path = if (it.data == null) {
                        PortraitUtils.createOutputFile(context).absolutePath
                    } else {
                        it.data.path
                    }
                    println("path = $path")
                    mIvPhoto.setImageFileByGlide(path)
                }
            }
        }
    }

    override fun initData() {
        val size = 100
        var history = ArrayList<VideoHistory>(size)
        for (i in 0..size) {
            history.add(VideoHistory("第${i}行", ""))
        }
        mHistoryAdapter = PersonHistoryAdapter(R.layout.item_person_history, history)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = FragmentTabPersonBinding.inflate(inflater, container, false)
        // 用户头像
        mIvPhoto = binding.ivPhoto
        mIvPhoto.setImageResourceByGlide(R.drawable.a6m)
        mIvPhoto.setOnClickListener { view -> mPopupWindow.show(view) }
        binding.ivSettings.setOnClickListener {
            startActivity(Intent(context, SettingsActivity::class.java))
        }
        // 历史记录 -> 查看更多
        binding.tvHistorySub.setOnClickListener {
            startActivity(Intent(context, HistoryActivity::class.java))
        }
        // 历史记录
        binding.rvHistory.adapter = mHistoryAdapter
        binding.rvHistory.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )
        // 个人服务

        return binding.root
    }

    /**
     * 判断权限是否申请
     * @param permissions 参数为申请的权限
     */
    fun checkPermissions(permissions: Array<String>) {
        val context = context ?: return
        // 回调监听
        val listener = object : PermissionListener {
            override fun onGranted() {
                PortraitUtils.startSystemCamera(this@PersonTabFragment)
            }

            override fun onDenied(permissions: List<String>) {
                permissions.forEach {
                    println("onDenied = $it")
                }
            }
        }
        // 申请权限
        PermissionFragment.requestPermission(context, permissions, listener)
    }
}