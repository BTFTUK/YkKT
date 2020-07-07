package com.yk.demo.wan.android.network

import com.yk.demo.wan.android.data.PreferencesHelperNew
import com.yk.demo.wan.android.utils.LogUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * @author yk
 * @description
 */
object RetrofitManager {
    private var BASE_URL = "https://www.wanandroid.com"
    private var BASE_URL2 = ""

    val apiService: ApiService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(genericOkClient())
            .build().create(ApiService::class.java)
    }

    val commonService: CommonService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .client(genericOkClient())
            .build().create(CommonService::class.java)
    }

    private fun genericOkClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(
            object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {

                    // 如果是 json 格式内容则打印 json
                    if ((message.startsWith("{") && message.endsWith("}")) ||
                        (message.startsWith("[") && message.endsWith("]"))
                    )
                        LogUtils.json(message)
                    else
                        LogUtils.verbose(message)
                }
            })

        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val interceptor: Interceptor = object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()
                val path = request.url.toUrl().path
                val newRequest: Request
                newRequest = if (path == "/sys/login" || path == "captcha.jpg") {
                    request.newBuilder()
                        .addHeader("Referer", BASE_URL2)
                        .build()
                } else {
                    request.newBuilder()
                        .addHeader("Referer", BASE_URL2)
                        .addHeader("token", PreferencesHelperNew.getToken())
                        .build()
                }
                return chain.proceed(newRequest)
            }
        }

        return OkHttpClient.Builder()
            .connectTimeout(5_000L, TimeUnit.MILLISECONDS)
            .readTimeout(10_000, TimeUnit.MILLISECONDS)
            .writeTimeout(30_000, TimeUnit.MILLISECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(RequestInterceptor())
            .addInterceptor(interceptor)
            .build()
    }

}