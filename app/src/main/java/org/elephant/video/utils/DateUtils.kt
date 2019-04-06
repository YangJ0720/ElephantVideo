package org.elephant.video.utils

private const val UNIT_MINUTE = 60

/**
 * @author YangJ
 * @date 2018/10/31
 */
object DateUtils {

    /**
     * 格式化视频播放时长
     */
    fun convertPlayDuration(duration: Int): String {
        val minute = duration / UNIT_MINUTE
        return String.format("%02d:%02d", minute, duration % UNIT_MINUTE)
    }

}