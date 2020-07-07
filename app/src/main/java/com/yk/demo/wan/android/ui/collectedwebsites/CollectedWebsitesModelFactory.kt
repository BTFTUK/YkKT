@file:Suppress("UNCHECKED_CAST")

package com.yk.demo.wan.android.ui.collectedwebsites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/**
 * @author Taonce.
 * @description
 */
class CollectedWebsitesModelFactory(private val repository: CollectedWebsitesRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CollectedWebsitesViewModel(repository) as T
    }
}

