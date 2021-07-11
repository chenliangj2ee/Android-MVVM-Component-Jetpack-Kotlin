package com.chenliang.mvvmc.act

import com.chenliang.account.act.LoginActivity
import com.chenliang.account.bean.BeanUser
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.goto
import com.chenliang.mvvmc.R
import com.chenliang.mvvmc.databinding.ActivitySplashBinding

/**
 * 启动页
 */
class SplashActivity : MyBaseActivity<ActivitySplashBinding>() {
    override fun layoutId() = R.layout.activity_splash

    override fun initCreate() {
        delayed(1000) { next() }
    }

    fun next() {
        if (BeanUser().get() == null) {
            goto(LoginActivity::class.java)
        } else {
            goto(MainActivity::class.java)
        }
        finish()
    }
}