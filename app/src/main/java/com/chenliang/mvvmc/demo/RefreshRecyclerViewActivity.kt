package com.chenliang.mvvmc.demo

import android.os.Handler
import com.chenliang.baselibrary.annotation.MVVM
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.mvvmc.R
import com.chenliang.mvvmc.bean.BeanUser
import com.chenliang.mvvmc.databinding.ActivityRecyclerviewBinding
import com.chenliang.mvvmc.databinding.ItemRecyclerviewBinding
import com.chenliang.mvvmc.databinding.ItemRecyclerviewBindingImpl
import kotlinx.android.synthetic.main.activity_recyclerview.*

@MVVM(title = "分页列表", toolbar = true)
class RefreshRecyclerViewActivity : MyBaseActivity<ActivityRecyclerviewBinding>() {
    override fun initCreate() {
        refresh.bindData<BeanUser> { (it.binding as ItemRecyclerviewBinding).user = it }
        refresh.loadData {
            Handler().postDelayed(Runnable {
                var datas = ArrayList<BeanUser>()
                for (i in 1..20) {
                    datas.add(BeanUser())
                }
                refresh.addData(datas)
            }, 2000)
        }

    }

    override fun layoutId(): Int {
        return R.layout.activity_recyclerview
    }

}