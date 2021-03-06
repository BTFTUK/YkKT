package com.yk.demo.wan.android.ui.coins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author yk
 * @description
 */

@Suppress("UNCHECKED_CAST")
class CoinModelFactory(private val repository: CoinRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CoinViewModel(repository) as T
    }
}