package com.chenliang.mvvmc.act

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.goto
import com.chenliang.baselibrary.utils.log
import com.chenliang.mvvmc.databinding.ActivityMainBinding
import com.chenliang.mvvmc.demo.*

/**
 * 主页
 */
@Route(path = "/app/main")
@My(myToolbarTitle = "主页")
class MainActivity : MyBaseActivity<ActivityMainBinding, DefaultViewModel>() {

    override fun initCreate() {
        log(intent.getStringExtra("username").toString())
        log(intent.getIntExtra("age", 0).toString())
    }


    fun defaultActivity(v: View) {
        goto(DefaultDemoActivity::class.java)
    }

    fun toolbarActivity(v: View) {
        goto(ToolBarDemoActivity::class.java)
    }

    fun refreshActivity(v: View) {
        goto(RefreshDemoActivity::class.java)
    }

    fun refreshRecyclerViewActivity(v: View) {
        goto(RefreshRecyclerViewActivity::class.java)
    }

    fun noToolbarRefreshFragment(v: View) {
        goto(FragmentNoTBRefreshDemoActivity::class.java)
    }

    fun toolbarRefreshFragment(v: View) {
        goto(FragmentToolBarRefreshDemoActivity::class.java)
    }
}