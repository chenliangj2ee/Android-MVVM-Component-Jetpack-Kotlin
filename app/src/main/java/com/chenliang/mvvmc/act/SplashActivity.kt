package com.chenliang.mvvmc.act

import com.alibaba.android.arouter.facade.annotation.Route
import com.chenliang.account.bean.BeanUser
import com.chenliang.baselibrary.annotation.MyRoute
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.goto
import com.chenliang.mvvmc.databinding.ActivitySplashBinding
import com.chenliang.processorapp.MyRoutePath

/**
 * 启动页
 */
@MyRoute
class SplashActivity : MyBaseActivity<ActivitySplashBinding, DefaultViewModel>() {

    override fun initCreate() {
        postDelayed(1000) { next() }
    }

    fun next() {
        if (BeanUser().get() == null) {
            goto(com.chenliang.processorCAccount.MyRoutePath.LoginActivity)
        } else {
            goto(MyRoutePath.MainActivity)
        }
        finish()
    }
}