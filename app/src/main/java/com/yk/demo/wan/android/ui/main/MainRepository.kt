package com.yk.demo.wan.android.ui.main

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yk.demo.wan.android.WanApplication
import com.yk.demo.wan.android.data.PreferencesHelper
import com.yk.demo.wan.android.entity.BannerData
import com.yk.demo.wan.android.network.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author yk
 * @description
 */
class MainRepository {

    fun getCachedBanners(): List<BannerData>? = Gson().fromJson(
        PreferencesHelper.fetchBannerCache(WanApplication.instance),
        object : TypeToken<List<BannerData>>() {}.type
    )

    suspend fun getCoins() = withContext(Dispatchers.IO) {
        RetrofitManager.apiService.fetchUserCoins(PreferencesHelper.fetchCookie(WanApplication.instance)).data
    }

    suspend fun getHomeBanners() = withContext(Dispatchers.IO) {
        RetrofitManager.apiService.homeBanner().data
    }

    suspend fun login(username: String, password: String) = withContext(Dispatchers.IO) {
        RetrofitManager.apiService.login(username, password)
    }

    suspend fun register(username: String, password: String, repass: String) = withContext(Dispatchers.IO) {
        RetrofitManager.apiService.register(username, password, repass)
    }

    suspend fun loginOut() = withContext(Dispatchers.IO) {
        RetrofitManager.apiService.loginOut()
    }
}