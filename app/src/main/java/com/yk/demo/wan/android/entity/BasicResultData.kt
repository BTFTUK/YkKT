package com.yk.demo.wan.android.entity

/**
 * @author yk
 * @description 用于通用返回的情况
 */
class BasicResultData(
    val `data`: Any,
    val errorCode: Int,
    val errorMsg: String
)