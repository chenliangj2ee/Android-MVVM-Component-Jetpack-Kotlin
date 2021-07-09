package com.chenliang.component_b.act

import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.component_b.R
import com.chenliang.component_b.databinding.BActivityMainBinding

@My(myToolbarTitle = "模块B", myToolbarShow = true)
class MainActivity : MyBaseActivity<BActivityMainBinding>() {
    override fun layoutId() = R.layout.b_activity_main

    override fun initCreate() {

    }


}