package com.yk.demo.wan.dmnd.entity

/**
 * @author yk
 * @description 用于通用返回的情况
 */
class BaseResponse<T>(val result: T, val code: Int, val msg: String) {
    fun isOk(): Boolean = code == 0
}

