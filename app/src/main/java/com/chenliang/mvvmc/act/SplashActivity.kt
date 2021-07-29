package com.chenliang.mvvmc.act

import com.alibaba.android.arouter.facade.annotation.Route
import com.chenliang.account.bean.BeanUser
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.goto
import com.chenliang.mvvmc.databinding.ActivitySplashBinding
import com.chenliang.processorCAccount.MyRoute
import com.chenliang.processorCBase.MySp

/**
 * 启动页
 */
@Route(path = "/app/Splash")
class SplashActivity : MyBaseActivity<ActivitySplashBinding, DefaultViewModel>() {

    override fun initCreate() {
        postDelayed(1000) { next() }
    }

    fun next() {
        if (BeanUser().get() == null) {
            goto(MyRoute.accountLogin)
        } else {
            goto(com.chenliang.processorapp.MyRoute.appMain)
        }
        finish()
    }
}