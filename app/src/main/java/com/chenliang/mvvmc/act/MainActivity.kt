package com.chenliang.mvvmc.act

import android.util.Log
import com.chenliang.baselibrary.annotation.MVVM
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.extend.initVM
import com.chenliang.baselibrary.net.c
import com.chenliang.baselibrary.net.n
import com.chenliang.baselibrary.net.obs
import com.chenliang.baselibrary.net.y
import com.chenliang.mvvmc.R
import com.chenliang.mvvmc.databinding.ActivityMainBinding
import com.chenliang.mvvmc.vm.AccountViewModel

@MVVM(title = "主页", toolbar = true, refresh = true)
class MainActivity : MyBaseActivity<ActivityMainBinding>() {
    override fun initCreate() {

    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun refresh() {
        Log.i("MyLog", "MainActivity refresh....")
        android.os.Handler().postDelayed(Runnable { stopRefresh() }, 3000)
    }

}