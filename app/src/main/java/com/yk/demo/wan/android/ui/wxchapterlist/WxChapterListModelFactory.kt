package com.yk.demo.wan.android.ui.wxchapterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author yk
 * @description
 */
class WxChapterListModelFactory(private val repository: WxChapterListRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WxChapterListViewModel(repository) as T
    }
}