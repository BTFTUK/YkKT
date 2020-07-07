package com.yk.demo.wan.android.base

import android.app.Activity

/**
 * @author yk
 * @description activity 栈管理
 */
object ActivityStackManager {

    private val activities = mutableListOf<Activity>()//可变列表

    @JvmStatic
    fun addActivity(activity: Activity) = activities.add(activity)

    @JvmStatic
    fun removeActivity(activity: Activity) {
        if (activities.contains(activity)) {
            activities.remove(activity)
            activity.finish()
        }
    }

    @JvmStatic
    fun getTopActivity(): Activity? =
        if (activities.isEmpty()) null else activities[activities.size - 1]

    @JvmStatic
    fun finishAll() =
        activities.filter { it.isFinishing }.forEach { it.finish() }
}