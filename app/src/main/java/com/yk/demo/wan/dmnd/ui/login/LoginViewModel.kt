package com.yk.demo.wan.dmnd.ui.login

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.yk.demo.wan.android.base.NetworkState
import com.yk.demo.wan.android.base.safeLaunch
import com.yk.demo.wan.android.utils.DmndUtils
import com.yk.demo.wan.dmnd.entity.Captcha
import com.yk.demo.wan.dmnd.entity.LoginResponse
import com.yk.demo.wan.dmnd.entity.User

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    val mCaptchaMutableLiveData = MutableLiveData<Captcha>()
    val mLoginResponseMutableLiveData = MutableLiveData<LoginResponse>()
    val mUserMutableLiveData = MutableLiveData<User>()
    val netState: MutableLiveData<NetworkState> = MutableLiveData<NetworkState>()

    fun loadVerify(success: (Captcha) -> Unit, fail: (String) -> Unit) {
        viewModelScope.safeLaunch {
            block = {
                loginRepository.loadVerify().let {
                    if (it != null) {
                        if (it.isOk()) {
                            it.result?.let { it1 -> success(it1) }
                            mCaptchaMutableLiveData.value = it.result
                        } else {
                            fail(it.msg)
                        }
                    } else {
                        fail("获取验证码失败")
                    }
                }
            }
        }

    }

    fun login(
        name: String, pwd: String, verify: String,
        cookie: String, savePwd: Boolean
    ) {
//        loginRepository.login().let {  }
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("请输入用户名")
            return
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showShort("请输入密码")
            return
        }
        if (TextUtils.isEmpty(verify)) {
            ToastUtils.showShort("请输入验证码")
            return
        }
        viewModelScope.safeLaunch {
            block = {
                netState.postValue(NetworkState.LOADING)
                val data = DmndUtils.getLoginData(name, pwd, verify)
//                mLoginResponseMutableLiveData.postValue(loginRepository.login(data, cookie))
                loginRepository.login(data, cookie).let { response ->
                    if (response.code == 0) {
                        mLoginResponseMutableLiveData.value = response;
                        DmndUtils.saveToken(response.token)
                        getUserInfo()
                        if (savePwd) DmndUtils.savePwd(pwd)
                        else DmndUtils.clearPwd()
                    }
                }
                netState.postValue(NetworkState.LOADED)
            }

            onError = {
                netState.postValue(NetworkState.error(it.message))
            }
        }
    }

}

private fun getUserInfo() {
    TODO("Not yet implemented")
}
