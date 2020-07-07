package com.yk.demo.wan.android.network

import com.yk.demo.wan.dmnd.entity.BaseResponse
import com.yk.demo.wan.dmnd.entity.Captcha
import com.yk.demo.wan.dmnd.entity.LoginResponse
import com.yk.demo.wan.dmnd.entity.User
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * @author yk
 * @description
 */
interface CommonService {
    /**
     * 登录
     */
    @Multipart
    @POST("/sys/login")
    suspend fun login(@Part partLis: List<MultipartBody.Part>,
                      @Header("Cookie") s: String)
            :LoginResponse

    /**
     * 获取验证码
     */
    @GET("/captcha.jpg")
    suspend fun loadVerify(): BaseResponse<Captcha?>?

    /**
     * 获取用户信息
     */
    @POST("/am/wOrderApply/getUserInfo")
    suspend fun getUserInfo(): BaseResponse<User?>?
}