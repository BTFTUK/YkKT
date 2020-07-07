package com.yk.demo.wan.android.ui

import android.content.Intent
import android.os.Bundle
import com.yk.demo.wan.android.R
import com.yk.demo.wan.android.base.BaseActivity
import com.yk.demo.wan.android.base.delayLaunch
import com.yk.demo.wan.android.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initActivity(savedInstanceState: Bundle?) {
        delayLaunch(2000) {
            block = {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}
