package com.chenliang.mvvmc.demo

import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.utils.log
import com.chenliang.mvvmc.databinding.ActivityRefreshBinding

@My(myRefresh = true,myToolbarTitle = "下拉刷新")
class RefreshDemoActivity : MyBaseActivity<ActivityRefreshBinding, DefaultViewModel>() {
    override fun initCreate() {
    }


    /**
     * 重写refresh方法
     */
    override fun refresh() {
        log("MainActivity refresh....")
        android.os.Handler().postDelayed(Runnable {
            /**
             * 调用stopRefresh，停止刷新
             */
            stopRefresh()
        }, 2000)
    }

}