package com.chenliang.mvvmc.demo

import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.mvvmc.R
import com.chenliang.mvvmc.databinding.ActivityMainBinding

@My(myToolbarTitle = "主页" )
class ToolBarDemoActivity : MyBaseActivity<ActivityMainBinding, DefaultViewModel>() {
    override fun initCreate() {

    }

}