package com.chenliang.mvvmc.act

import com.chenliang.account.bean.BeanUser
import com.chenliang.annotation.MyRoute
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.MyDevice
import com.chenliang.baselibrary.utils.goto
import com.chenliang.mvvmc.databinding.ActivitySplashBinding
import com.chenliang.processorCAccount.MyRoutePath.accountLogin
import com.chenliang.processorapp.MyRoutePath.appMain

/**
 * 启动页
 */
@MyRoute(path = "app/splash")
class SplashActivity : MyBaseActivity<ActivitySplashBinding, DefaultViewModel>() {

    override fun initCreate() {
        postDelayed(1000) { next() }
    }

    fun next() {
        if (BeanUser().get() == null) {
            goto(accountLogin)
        } else {
            goto(appMain)
        }
        finish()
    }
}