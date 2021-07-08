package com.chenliang.component_a.act

import android.icu.text.CaseMap
import com.chenliang.baselibrary.annotation.MVVM
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.component_a.R
import com.chenliang.component_a.databinding.AActivityMainBinding

@MVVM(title = "模块A", toolbar = true)
class MainActivity : MyBaseActivity<AActivityMainBinding>() {
    override fun layoutId() = R.layout.a_activity_main

    override fun initCreate() {
    }


}