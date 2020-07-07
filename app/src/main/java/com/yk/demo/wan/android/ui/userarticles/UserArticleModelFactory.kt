package com.yk.demo.wan.android.ui.userarticles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author yk
 * @description
 */
class UserArticleModelFactory(private val repository: UserArticleRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserArticleViewModel(repository) as T
    }
}