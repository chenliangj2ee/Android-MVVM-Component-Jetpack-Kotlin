package com.chenliang.mvvmc.act

import com.alibaba.android.arouter.facade.annotation.Route
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.log
import com.chenliang.mvvmc.databinding.ActivityMainBinding

/**
 * 主页
 */
@Route(path = "/app/main")
class MainActivity : MyBaseActivity<ActivityMainBinding, DefaultViewModel>() {

    override fun initCreate() {
        log(intent.getStringExtra("username").toString())
        log(intent.getIntExtra("age", 0).toString())
    }

}