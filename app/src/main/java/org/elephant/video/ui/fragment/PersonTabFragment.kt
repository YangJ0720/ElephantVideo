package org.elephant.video.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
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
import org.elephant.video.utils.PermissionsUtils
import org.elephant.video.utils.PortraitUtils

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
                        startSystemPhoto(activity)
                    }
                    R.id.tvCamera -> {
                        val permissions = arrayOf(Manifest.permission.CAMERA)
                        PermissionsUtils.checkPermissions(activity!!, permissions)
                    }
                }
            }
        })
    }

    /**
     * 打开系统相册
     */
    private fun startSystemPhoto(activity: Activity?) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity?.startActivityForResult(intent, PortraitUtils.REQUEST_CODE_PICK_PHOTO)
    }

    /**
     * 打开系统裁剪
     */
    private fun startSystemCrop(activity: Activity?, data: Uri, width: Int, height: Int) {
        val intent = PortraitUtils.onCropPicture(activity, data, width, height)
        activity?.startActivityForResult(intent, PortraitUtils.REQUEST_CODE_PICK_CROP)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("onActivityResult -> requestCode = $requestCode, resultCode = $resultCode")
        if (Activity.RESULT_OK != resultCode) return
        when (requestCode) {
            PortraitUtils.REQUEST_CODE_PICK_PHOTO -> {
                data?.let {
                    startSystemCrop(activity, it.data, mIvPhoto.width, mIvPhoto.height)
                }
            }
            PortraitUtils.REQUEST_CODE_PICK_CAMERA -> {
                val uri = PortraitUtils.getUriByCamera(activity!!)
                PortraitUtils.startSystemCrop(activity!!, uri, mIvPhoto.width, mIvPhoto.height)
            }
            PortraitUtils.REQUEST_CODE_PICK_CROP -> {
                data?.let {
                    val path = if (it.data == null) {
                        PortraitUtils.createOutputFile(context).absolutePath
                    } else {
                        it.data.path
                    }
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

}