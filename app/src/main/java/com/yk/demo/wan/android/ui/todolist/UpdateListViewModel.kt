package com.yk.demo.wan.android.ui.todolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author yk
 * @description
 */
class UpdateListViewModel : ViewModel() {

    val needUpdate = MutableLiveData<Boolean>()

    init {
        needUpdate.value = false
    }
}