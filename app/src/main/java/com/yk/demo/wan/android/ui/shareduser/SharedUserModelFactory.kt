package com.yk.demo.wan.android.ui.shareduser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author yk
 * @description
 */
class SharedUserModelFactory(private val repository: UserSharedRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SharedUserViewModel(repository) as T
    }
}