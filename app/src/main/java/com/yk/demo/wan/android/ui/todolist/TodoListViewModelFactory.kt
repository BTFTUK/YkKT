package com.yk.demo.wan.android.ui.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author yk
 * @description
 */
class TodoListViewModelFactory(private val repository: TodoRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodoListViewModel(repository) as T
    }
}