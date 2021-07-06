package com.chenliang.mvvmc.act

import com.chenliang.baselibrary.annotation.MVVM
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.base.MyBaseDialog
import com.chenliang.mvvmc.R
import com.chenliang.mvvmc.databinding.ActivityMainBinding
import com.chenliang.mvvmc.databinding.DialogLoginBinding
import kotlinx.coroutines.DEBUG_PROPERTY_NAME

//@MVVM(id = R.layout.b_activity_main)
class MainActivity : MyBaseActivity<ActivityMainBinding>() {
    override fun initCreate() {
    }

    override fun layoutId(): Int {
        TODO("Not yet implemented")
    }

}