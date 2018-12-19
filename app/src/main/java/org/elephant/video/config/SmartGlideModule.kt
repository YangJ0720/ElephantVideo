package org.elephant.video.config

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

/**
 * @author YangJ
 * @date 2018/11/7
 */
@GlideModule
class SmartGlideModule: AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // 设置缓存大小
        // builder.setMemoryCache(LruResourceCache(50 * 1024 * 1024))
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        // 设置缓存级别
        // glide.setMemoryCategory(MemoryCategory.HIGH)
    }
}