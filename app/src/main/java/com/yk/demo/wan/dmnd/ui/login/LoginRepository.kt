package com.yk.demo.wan.dmnd.ui.login

import com.yk.demo.wan.android.network.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class LoginRepository {

    suspend fun loadVerify() = withContext(Dispatchers.IO) {
        RetrofitManager.commonService.loadVerify()
    }

    suspend fun login(data: String, cookie: String) = withContext(Dispatchers.IO) {
        val builder: MultipartBody.Builder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
        builder.addFormDataPart("data", data)
        val parts = builder.build().parts
        RetrofitManager.commonService.login(parts, cookie)
    }

    suspend fun getUserInfo() = withContext(Dispatchers.IO){
        RetrofitManager.commonService.getUserInfo()
    }
}