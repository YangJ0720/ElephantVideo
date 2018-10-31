package org.elephant.video.utils

private const val UNIT_MINUTE = 60

/**
 * @author YangJ
 * @date 2018/10/31
 */
object DateUtils {

    fun convertPlayDuration(duration: Int?): String? {
        return if (duration == null) {
            null
        } else {
            val minute = duration / UNIT_MINUTE
            String.format("%02d:%02d", minute, duration % UNIT_MINUTE)
        }
    }

}