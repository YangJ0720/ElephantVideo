package org.elephant.video.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import org.elephant.video.BuildConfig
import java.io.File

/**
 * Created by YangJ on 2018/12/17.
 */
class PortraitUtils {

    companion object {
        /**
         * 打开系统相册
         */
        const val REQUEST_CODE_PICK_PHOTO = 0
        /**
         * 打开系统相机
         */
        const val REQUEST_CODE_PICK_CAMERA = 1
        /**
         * 打开系统裁剪
         */
        const val REQUEST_CODE_PICK_CROP = 2

        /**
         * 从系统相机返回数据中获取uri
         */
        fun getUriByCamera(context: Context): Uri {
            val file = PortraitUtils.createOutputFile(context)
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file)
            } else {
                Uri.fromFile(file)
            }
        }

        /**
         * 从系统裁剪返回的数据中获取bitmap
         */
        fun getBitmapByCrop(context: Context?, data: Uri): Bitmap {
            return if (data == null) {
                val path = createOutputFile(context).absolutePath
                BitmapFactory.decodeFile(path)
            } else {
                BitmapFactory.decodeFile(data.path)
            }
        }

        /**
         * 创建头像裁剪临时文件
         */
        fun createOutputFile(context: Context?): File {
            return File(context?.externalCacheDir, "photo.jpg")
        }

        /**
         * 裁剪照片
         */
        fun onCropPicture(context: Context?, uri: Uri, width: Int, height: Int): Intent {
            return onCropPicture(uri, width, height, Uri.fromFile(createOutputFile(context)))
        }

        /**
         * 裁剪照片
         */
        private fun onCropPicture(uri: Uri, width: Int, height: Int, output: Uri): Intent {
            val intent = Intent("com.android.camera.action.CROP")
            // 设置裁剪类型为图片
            intent.setDataAndType(uri, "image/*")
            intent.putExtra("crop", true)
            // 设置裁剪框宽高比例
            intent.putExtra("aspectX", 1)
            intent.putExtra("aspectY", 1)
            // 设置裁剪之后照片的宽度和高度
            intent.putExtra("outputX", width)
            intent.putExtra("outputY", height)
            // 是否保留比例
            intent.putExtra("scale", true)
            // 设置裁剪之后照片是否通过intent返回
            intent.putExtra("return-data", false)
            // 设置裁剪之后照片的格式
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
            // 设置裁剪之后照片输出到自定义路径
            // intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFilePath));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, output)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }
            return intent
        }
    }

}