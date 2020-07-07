package com.yk.demo.wan.android.ui.todoedit

import com.yk.demo.wan.android.WanApplication
import com.yk.demo.wan.android.data.PreferencesHelper
import com.yk.demo.wan.android.network.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author yk
 * @description
 */
class TodoEditRepository {

    suspend fun addTodo(param: HashMap<String, Any>) = withContext(Dispatchers.IO) {
        RetrofitManager.apiService.addTodo(param, PreferencesHelper.fetchCookie(WanApplication.instance))
    }

    suspend fun updateTodo(id: Int, param: HashMap<String, Any>) = withContext(Dispatchers.IO) {
        RetrofitManager.apiService.updateTodo(id, PreferencesHelper.fetchCookie(WanApplication.instance), param)
    }

    suspend fun deleteTodo(id: Int) = withContext(Dispatchers.IO) {
        RetrofitManager.apiService.deleteTodo(id, PreferencesHelper.fetchCookie(WanApplication.instance))
    }
}