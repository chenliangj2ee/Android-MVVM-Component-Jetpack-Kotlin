package com.chenliang.mvvmc.demo

import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.mvvmc.R
import com.chenliang.mvvmc.databinding.ActivityMainBinding
import com.chenliang.mvvmc.databinding.ActivityToolbarBinding

@My(myToolbarTitle = "显示Toolbar" )
class ToolBarDemoActivity : MyBaseActivity<ActivityToolbarBinding, DefaultViewModel>() {
    override fun initCreate() {

    }

}