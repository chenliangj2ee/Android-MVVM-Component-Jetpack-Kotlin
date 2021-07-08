package com.chenliang.mvvmc.demo

import com.chenliang.baselibrary.annotation.MVVM
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.mvvmc.R
import com.chenliang.mvvmc.databinding.ActivityMainBinding

@MVVM(title = "主页", toolbar = true)
class ToolBarDemoActivity : MyBaseActivity<ActivityMainBinding>() {
    override fun initCreate() {

    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

}