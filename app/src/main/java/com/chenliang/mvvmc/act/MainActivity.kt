package com.chenliang.mvvmc.act

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.*
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

    fun viewPageFragmentDemoActivity(v: View) {
        goto(ViewPageFragmentDemoActivity::class.java)
    }

    fun dialogAction(v: View) {
        MyDialog().message("确定删除用户？")
            .y { toast("确定被点击") }
            .n { toast("取消被点击") }
            .show(this)

//        //或者
//        dialog("确定删除用户？")
//            .y { toast("确定被点击") }
//            .n { toast("取消被点击") }
//            .show(this)

    }

    fun dialogCustomTextAction(v: View) {
        MyDialog().message("确定提交订单？")
            .y("提交") { toast("提交被点击") }
            .n("关闭") { toast("关闭被点击") }
            .show(this)
//        //或者
//        dialog("确定删除用户？")
//            .y("提交") { toast("确定被点击") }
//            .n("关闭") { toast("取消被点击") }
//            .show(this)
    }

    fun customDialogAction(v: View) {
        var dialog = DialogDemo()
        dialog.show(this)
    }
}