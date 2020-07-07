package com.yk.demo.wan.android.data

import android.content.Context
import com.blankj.utilcode.util.Utils
import com.yk.demo.wan.android.utils.SharePreferencesUtils

/**
 * @author yk
 * @description
 */
object PreferencesHelperNew {
    private const val STATE_KEY_FIRST_INT = "wan.state.first.in"
    private const val USER_KEY_ID = "wan.user.id"
    private const val USER_KEY_NAME = "wan.user.name"
    private const val USER_KEY_COOKIE = "wan.user.cookie"
    private const val CACHE_KEY_BANNER = "wan.cache.banner"

    private const val TOKEN = "dmnd.token"
    private const val USER = "dmnd.user"
    private const val USERNAME = "dmnd.user.name"
    private const val PWD = "dmnd.pwd"
    private const val MODULES = "dmnd.modules"
    private const val SEARCH_HISTORY = "dmnd.search.history"
    const val SUDOKU_DATA ="suduku_data"

    fun saveToken(token: String) {
        SharePreferencesUtils.saveString(Utils.getApp(), TOKEN, token)
    }

    fun getToken() = SharePreferencesUtils.getString(Utils.getApp(), TOKEN)

    fun saveFirstState(context: Context, isFirst: Boolean) =
        SharePreferencesUtils.saveBoolean(context, STATE_KEY_FIRST_INT, isFirst)

    fun isFirstIn(context: Context) =
        SharePreferencesUtils.getBoolean(context, STATE_KEY_FIRST_INT, true)

    fun saveUserId(context: Context, id: Int) =
        SharePreferencesUtils.saveInteger(context, USER_KEY_ID, id)

    fun hasLogin(context: Context) =
        SharePreferencesUtils.getString(context, TOKEN) != ""

    fun saveUserName(context: Context, name: String) =
        SharePreferencesUtils.saveString(context, USER_KEY_NAME, name)

    fun fetchUserName(context: Context) =
        SharePreferencesUtils.getString(context, USER_KEY_NAME)

    fun saveCookie(context: Context, cookie: String) =
        SharePreferencesUtils.saveString(context, USER_KEY_COOKIE, cookie)

    fun fetchCookie(context: Context) =
        SharePreferencesUtils.getString(context, USER_KEY_COOKIE)

    // =======================> LOCAL CACHES <=================================

    fun saveBannerCache(context: Context, bannerJson: String) =
        SharePreferencesUtils.saveString(context, CACHE_KEY_BANNER, bannerJson)

    fun fetchBannerCache(context: Context) =
        SharePreferencesUtils.getString(context, CACHE_KEY_BANNER)

    fun getUserName() = SharePreferencesUtils.getString(Utils.getApp(), USERNAME)

    fun getPwd() = SharePreferencesUtils.getString(Utils.getApp(), PWD)

    fun savePwd(pwd: String){
        SharePreferencesUtils.saveString(Utils.getApp(), PWD, pwd)
    }
}