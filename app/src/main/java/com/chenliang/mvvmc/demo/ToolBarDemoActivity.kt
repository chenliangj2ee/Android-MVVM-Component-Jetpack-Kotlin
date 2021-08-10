package com.chenliang.mvvmc.demo

import com.chenliang.baselibrary.annotation.MyClass
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.mvvmc.databinding.ActivityToolbarBinding

@MyClass(mToolbarTitle = "显示Toolbar" )
class ToolBarDemoActivity : MyBaseActivity<ActivityToolbarBinding, DefaultViewModel>() {
    override fun initCreate() {

    }

}