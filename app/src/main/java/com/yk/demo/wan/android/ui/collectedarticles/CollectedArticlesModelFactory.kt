package com.yk.demo.wan.android.ui.collectedarticles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/**
 * Author: Taonce
 * Date: 2019/7/19
 * Project: CoroutinesWanAndroid
 * Desc:
 */
class CollectedArticlesModelFactory(private val repo: CollectedArticlesRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CollectedArticlesViewModel(repo) as T
    }
}

