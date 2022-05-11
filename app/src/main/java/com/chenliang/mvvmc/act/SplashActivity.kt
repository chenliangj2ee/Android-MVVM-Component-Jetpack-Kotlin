package com.chenliang.mvvmc.act

import com.chenliang.account.bean.BeanUser
import com.chenliang.annotation.MyRoute
import com.chenliang.baselibrary.annotation.MyClass
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.goto
import com.chenliang.mvvmc.databinding.ActivitySplashBinding
import com.chenliang.processor.CAccount.MyRoutePath.accountLogin
import com.chenliang.processor.app.MyRoutePath.appMain

/**
 * 启动页
 */
@MyRoute(mPath = "app/splash")
@MyClass(mFullScreen = true)
class SplashActivity : MyBaseActivity<ActivitySplashBinding, DefaultViewModel>() {

    override fun initCreate() {
        postDelayed(1000) { next() }
    }

    fun next() {
        if (BeanUser().get<BeanUser>() == null) {
            goto(accountLogin)
        } else {
            goto(appMain)
        }
        finish()
    }
}