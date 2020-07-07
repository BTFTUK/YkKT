package com.yk.demo.wan.dmnd.entity

/**
 * @author yk
 * @description 用于通用返回的情况
 */
class LoginResponse(
    val token: String,
    val code: Int,
    val expire: Int,
    val msg: String
)