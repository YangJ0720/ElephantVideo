package org.elephant.video.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tbruyelle.rxpermissions2.RxPermissions
import org.elephant.video.BuildConfig
import org.elephant.video.R
import org.elephant.video.base.BaseFragment
import org.elephant.video.databinding.FragmentTabPersonBinding
import org.elephant.video.popup.PhotoPopupWindow
import org.elephant.video.ui.widget.CircleImageView
import org.elephant.video.utils.PortraitUtils

/**
 * @author YangJ
 *
 */
class PersonTabFragment : BaseFragment() {

    private lateinit var mIvPhoto: CircleImageView

    private val mPopupWindow by lazy {
        PhotoPopupWindow(context, object : PhotoPopupWindow.OnItemClickListener {
            override fun onItemClick(id: Int) {
                when (id) {
                    R.id.tvPhoto -> {
                        startSystemPhoto()
                    }
                    R.id.tvCamera -> {
                        checkPermissions()
                    }
                }
            }
        })
    }

    private val mRxPermissions by lazy { RxPermissions(context as Activity) }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode) {
            when (requestCode) {
                PortraitUtils.REQUEST_CODE_PICK_PHOTO -> {
                    data?.let {
                        startSystemCrop(it.data)
                    }
                }
                PortraitUtils.REQUEST_CODE_PICK_CAMERA -> {
                    val uri = PortraitUtils.getUriByCamera(context!!)
                    startSystemCrop(uri)
                }
                PortraitUtils.REQUEST_CODE_PICK_CROP -> {
                    data?.let {
                        val bitmap = PortraitUtils.getBitmapByCrop(context!!, it.data)
                        mIvPhoto.setImageBitmapByGlide(bitmap)
                    }
                }
            }
        }
    }

    override fun initData() {

    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = FragmentTabPersonBinding.inflate(inflater, container, false)
        mIvPhoto = binding.ivPhoto
        mIvPhoto.setImageResourceByGlide(R.drawable.a6m)
        mIvPhoto.setOnClickListener { view -> mPopupWindow.show(view) }
        return binding.root
    }

    /**
     * 判断权限是否申请
     */
    private fun checkPermissions() {
        mRxPermissions.request(android.Manifest.permission.CAMERA).subscribe { t ->
            if (t) {
                startSystemCamera()
            } else {

            }
        }
    }

    /**
     * 打开系统相册
     */
    private fun startSystemPhoto() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PortraitUtils.REQUEST_CODE_PICK_PHOTO)
    }

    /**
     * 打开系统相机
     */
    private fun startSystemCamera() {
        val file = PortraitUtils.createOutputFile(context)
        //
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            FileProvider.getUriForFile(context!!, BuildConfig.APPLICATION_ID + ".provider", file)
        } else {
            Uri.fromFile(file)
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, PortraitUtils.REQUEST_CODE_PICK_CAMERA)
    }

    /**
     * 打开系统裁剪
     */
    private fun startSystemCrop(data: Uri) {
        val intent = PortraitUtils.onCropPicture(context, data, mIvPhoto.width, mIvPhoto.height)
        startActivityForResult(intent, PortraitUtils.REQUEST_CODE_PICK_CROP)
    }
}