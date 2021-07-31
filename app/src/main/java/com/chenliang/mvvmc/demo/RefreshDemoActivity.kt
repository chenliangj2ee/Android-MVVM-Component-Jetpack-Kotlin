package com.chenliang.mvvmc.demo

import com.chenliang.baselibrary.annotation.MyClass
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.mvvmc.databinding.ActivityRefreshBinding

@MyClass(myRefresh = true,myToolbarTitle = "下拉刷新")
class RefreshDemoActivity : MyBaseActivity<ActivityRefreshBinding, DefaultViewModel>() {
    override fun initCreate() {
    }


    /**
     * 重写refresh方法
     */
    override fun refresh() {
        android.os.Handler().postDelayed(Runnable {
            /**
             * 调用stopRefresh，停止刷新
             */
            stopRefresh()
        }, 2000)
    }

}