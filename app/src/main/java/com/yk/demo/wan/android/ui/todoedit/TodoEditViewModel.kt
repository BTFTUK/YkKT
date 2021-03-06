package com.yk.demo.wan.android.ui.todoedit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yk.demo.wan.android.base.safeLaunch
import com.yk.demo.wan.android.utils.TimeUtils
import java.util.*

/**
 * @author yk
 * @description
 */
class TodoEditViewModel(private val repository: TodoEditRepository) : ViewModel() {

    val todoType = MutableLiveData<Int>()

    val todoPriority = MutableLiveData<Int>()

    val todoDate = MutableLiveData<String>()

    val todoState = MutableLiveData<Int>()

    init {
        val calendar = Calendar.getInstance()

        todoType.value = 1
        todoPriority.value = 1
        todoDate.value = TimeUtils.formatDate(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        todoState.value = 0
    }

    fun addTodo(param: HashMap<String, Any>, success: () -> Unit, fail: (String) -> Unit) {
        viewModelScope.safeLaunch {
            block = {
                repository.addTodo(param).let {
                    if (it.errorCode == 0) success() else fail(it.errorMsg)
                }
            }
            onError = {
                fail("网络出错啦~请检查网络")
            }
        }
    }

    fun updateTodo(id: Int, param: HashMap<String, Any>, success: () -> Unit, fail: (String) -> Unit) {
        viewModelScope.safeLaunch {
            block = {
                repository.updateTodo(id, param).let {
                    if (it.errorCode == 0) success() else fail(it.errorMsg)
                }
            }
            onError = { fail("网络出错啦~请检查网络") }
        }
    }

    fun deleteTodo(id: Int, success: () -> Unit, fail: (String) -> Unit) {
        viewModelScope.safeLaunch {
            block = {
                repository.deleteTodo(id).let {
                    if (it.errorCode == 0) success() else fail(it.errorMsg)
                }
            }
            onError = {
                fail("网络出错啦~请检查网络")
            }
        }
    }
}