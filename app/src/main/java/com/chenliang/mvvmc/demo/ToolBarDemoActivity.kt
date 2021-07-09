package com.chenliang.mvvmc.demo

import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.mvvmc.R
import com.chenliang.mvvmc.databinding.ActivityMainBinding

@My(myToolbarTitle = "主页", myToolbarShow = true)
class ToolBarDemoActivity : MyBaseActivity<ActivityMainBinding>() {
    override fun initCreate() {

    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

}