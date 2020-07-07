package com.yk.demo.wan.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.blankj.utilcode.util.Utils

/**
 * @author yk
 * @description
 */
class WanApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = applicationContext
        init();
    }

    fun init(){
        Utils.init(applicationContext)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Context
    }
}