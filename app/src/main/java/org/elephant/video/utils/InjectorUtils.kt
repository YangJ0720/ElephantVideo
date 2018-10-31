package org.elephant.video.utils

import org.elephant.video.repository.RemoteDataRepository
import org.elephant.video.viewmodel.HomeTabViewModelFactory
import org.elephant.video.viewmodel.TabPagerViewModelFactory

/**
 * @author YangJ
 * @date 2018/10/30
 */
object InjectorUtils {

    /**
     * 获取本地数据
     */
    private fun getLocalRepository() {

    }

    /**
     * 获取远程数据
     */
    private fun getRemoteRepository(): RemoteDataRepository {
        return RemoteDataRepository.getInstance()
    }

    fun provideHomeTabViewModelFactory(): HomeTabViewModelFactory {
        val repository = getRemoteRepository()
        return HomeTabViewModelFactory(repository)
    }

    fun provideTabPagerViewModelFactory(id: Int?): TabPagerViewModelFactory {
        val repository = getRemoteRepository()
        return TabPagerViewModelFactory(repository, id)
    }

}