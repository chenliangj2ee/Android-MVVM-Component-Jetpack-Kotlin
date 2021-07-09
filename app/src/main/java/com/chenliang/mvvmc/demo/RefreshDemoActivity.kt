package com.chenliang.mvvmc.demo

import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.log
import com.chenliang.mvvmc.R
import com.chenliang.mvvmc.databinding.ActivityMainBinding

@My(myRefresh = true)
class RefreshDemoActivity : MyBaseActivity<ActivityMainBinding>() {
    override fun initCreate() {
    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun refresh() {
        log("MainActivity refresh....")
        android.os.Handler().postDelayed(Runnable { stopRefresh() }, 2000)
    }

}