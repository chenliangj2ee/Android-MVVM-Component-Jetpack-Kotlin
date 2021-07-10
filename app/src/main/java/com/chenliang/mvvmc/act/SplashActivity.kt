package com.chenliang.mvvmc.act

import android.content.Intent
import android.os.Handler
import com.chenliang.account.act.LoginActivity
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.mvvmc.R
import com.chenliang.mvvmc.databinding.ActSplashBinding

class SplashActivity : MyBaseActivity<ActSplashBinding>() {
    override fun layoutId() = R.layout.act_splash

    override fun initCreate() {
        Handler().postDelayed(Runnable {
            startActivity(Intent(this, LoginActivity::class.java))
        }, 2000)
    }
}