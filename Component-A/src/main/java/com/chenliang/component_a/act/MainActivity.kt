package com.chenliang.component_a.act

import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.component_a.R
import com.chenliang.component_a.databinding.AActivityMainBinding

@My(myToolbarTitle = "模块A", myToolbarShow = true ,myRefresh = true)
class MainActivity : MyBaseActivity<AActivityMainBinding>() {
    override fun layoutId() = R.layout.a_activity_main

    override fun initCreate() {
    }


}