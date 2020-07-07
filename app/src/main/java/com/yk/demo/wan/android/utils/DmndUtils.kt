package com.yk.demo.wan.android.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.blankj.utilcode.util.CacheMemoryUtils
import com.blankj.utilcode.util.Utils
import com.yk.demo.wan.android.data.PreferencesHelperNew

object DmndUtils {
    @JvmStatic
    fun isLogin() = PreferencesHelperNew.hasLogin(Utils.getApp())

    /**
     * string转成bitmap
     *
     * @param st
     */
    @JvmStatic
    fun convertStringToIcon(st: String?): Bitmap? {
        // OutputStream out;
        var bitmap: Bitmap? = null
        return try {
            val bitmapArray: ByteArray = Base64.decode(st, Base64.DEFAULT)
            bitmap = BitmapFactory.decodeByteArray(
                bitmapArray, 0,
                bitmapArray.size
            )
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @JvmStatic
    fun getLoginData(name: String, pwd: String?, verify: String): String {
        return try {
            val sb = ("username=" + name
                    + "&rsapwd=" + SM2Utils.sm3encrypt(pwd)
                    + "&error=0" + "&captcha=" + verify)
            SM2Utils.encrypt(sb)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            ""
        }
    }

    @JvmStatic
    fun saveToken(token: String?) {
        if (token != null) {
            PreferencesHelperNew.saveToken(token)
        }
    }

    @JvmStatic
    fun logoutAndLogin() {

    }

    @JvmStatic
    fun savePwd(pwd: String) {
        PreferencesHelperNew.savePwd(pwd)
    }

    @JvmStatic
    fun clearPwd() {
        PreferencesHelperNew.savePwd("")
    }

    @JvmStatic
    fun getPwd() = PreferencesHelperNew.getPwd()
}

