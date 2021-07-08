package com.chenliang.component_b.act

import com.chenliang.baselibrary.annotation.MVVM
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.component_b.R
import com.chenliang.component_b.databinding.BActivityMainBinding

@MVVM(title = "模块B", toolbar = true)
class MainActivity : MyBaseActivity<BActivityMainBinding>() {
    override fun layoutId() = R.layout.b_activity_main

    override fun initCreate() {

    }


}