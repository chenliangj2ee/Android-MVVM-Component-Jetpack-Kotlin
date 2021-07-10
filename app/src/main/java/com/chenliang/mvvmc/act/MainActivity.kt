package com.chenliang.mvvmc.act

import com.alibaba.android.arouter.facade.annotation.Route
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.log
import com.chenliang.mvvmc.R
import com.chenliang.mvvmc.databinding.ActivityMainBinding
import kotlin.math.log

/**
 * 主页
 */
@Route(path = "/app/main")
class MainActivity : MyBaseActivity<ActivityMainBinding>() {
    override fun layoutId() = R.layout.activity_main

    override fun initCreate() {
        log(intent.getStringExtra("username").toString())
        log(intent.getIntExtra("age", 0).toString())
    }

}