package com.chenliang.mvvmc.act

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.view.View
import com.chenliang.annotation.MyRoute
import com.chenliang.baselibrary.annotation.MyClass
import com.chenliang.baselibrary.annotation.MyField
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.*
import com.chenliang.mvvmc.BuildConfig
import com.chenliang.mvvmc.databinding.ActivityMainBinding
import com.chenliang.mvvmc.demo.*
import gorden.rxbus2.Subscribe

/**
 * 主页
 */
@MyRoute(path = "/app/main")
@MyClass(myToolbarTitle = "Demo主页", myShowNetworkError = true)
class MainActivity : MyBaseActivity<ActivityMainBinding, DefaultViewModel>() {

    @MyField//Intent传参
    lateinit var username: String

    @MyField//Intent传参
    var age: Int = 0

    override fun initCreate() {
        mToolBar.showLeft(false)
        networkChangeListener()
        arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
            .checkPermissions(this) {
                if (it) {

                } else {

                }
            }
    }

    /**
     * 网络监听
     */
    private fun networkChangeListener() {
        networkChange {
            if (it) {
                log("来网了")
            } else {
                log("断网了")
            }
        }
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

    fun refreshRecyclerViewMoreTypeActivity(v: View) {
        goto(RefreshRecyclerViewMoreTypeActivity::class.java)
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
        //或者
//        dialog("确定删除用户？")
//            .y("提交") { toast("确定被点击") }
//            .n("关闭") { toast("取消被点击") }
//            .show(this)
    }

    fun customDialogAction(v: View) {
        var dialog = DialogDemo()
        dialog.show(this)
    }

    fun evnetCallBackAction(v: View) {
        goto(EventCallBackActivity::class.java)
    }

    @Subscribe(code = 100)
    fun eventCallBack(event: RxBusEvent<String>) {
        toast("消息收到：${event.data}")
        event.callback("message", "回调成功", "age", 11)
    }

    /**
     * 跨组件fragment调用
     * @param v View
     */
    fun libraryFragmentAction(v: View) {
        goto(LibraryFragmentActivity::class.java)
    }


}