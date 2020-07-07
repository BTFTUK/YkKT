package com.yk.demo.wan.android.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import com.yk.demo.wan.android.R
import com.yk.demo.wan.android.base.BaseActivity
import com.yk.demo.wan.android.data.PreferencesHelper
import com.yk.demo.wan.android.databinding.ActivityMainBinding
import com.yk.demo.wan.android.ui.main.MainFragment
import com.yk.demo.wan.android.utils.ApplicationUtils
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private var availableCount = 0

    //by lazy  语句只执行一次 后面在用就是直接取值
    //lazy()是接受一个 lambda 并返回一个 Lazy <T> 实例的函数，返回的实例可以作为实现延迟属性的委托：
    // 第一次调用 get() 会执行已传递给 lazy() 的 lambda 表达式并记录结果，
    // 后续调用 get() 只是返回记录的结果。
    // https://www.jianshu.com/p/e2cb4c65d4ff

    private val manager: ConnectivityManager by lazy {
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val request: NetworkRequest by lazy {
        NetworkRequest.Builder().build()
    }

    private val netStateCallback: ConnectivityManager.NetworkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                availableCount++
                checkState()
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                availableCount--
                checkState()
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initActivity(savedInstanceState: Bundle?) {
        manager.registerNetworkCallback(request, netStateCallback)

        if (PreferencesHelper.isFirstIn(this)) {
            alert(
                String.format(
                    resources.getString(R.string.operate_helper),
                    ApplicationUtils.getAppVersionName(this)
                ), resources.getString(R.string.operate_title)
            ) {
                isCancelable = false
                yesButton { PreferencesHelper.saveFirstState(this@MainActivity, false) }
            }.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        manager.unregisterNetworkCallback(netStateCallback)
    }

    private fun checkState() {
        mBinding.netAvailable = availableCount > 0
    }

    override fun needTransparentStatus(): Boolean = true

    override fun onBackPressed() {
        supportFragmentManager.fragments.first()
            .childFragmentManager.fragments.last().let {
            if (it is MainFragment) {
                startActivity(Intent(Intent.ACTION_MAIN)
                    .apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        addCategory(Intent.CATEGORY_HOME)
                    })
                return
            }
        }

        super.onBackPressed()
    }
}
