package com.chenliang.mvvmc.demo

import android.os.Handler
import com.chenliang.account.bean.BeanUser
import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.mvvmc.R
import com.chenliang.mvvmc.databinding.ActivityRecyclerviewBinding
import com.chenliang.mvvmc.databinding.ItemRecyclerviewBinding
import kotlinx.android.synthetic.main.activity_recyclerview.*

@My(myToolbarTitle = "分页列表" )
class RefreshRecyclerViewActivity : MyBaseActivity<ActivityRecyclerviewBinding, DefaultViewModel>() {
    override fun initCreate() {

        refresh.bindData<BeanUser> { (it.binding as ItemRecyclerviewBinding).user = it }
        refresh.loadData {
            Handler().postDelayed(Runnable {
                var datas = ArrayList<BeanUser>()
                for (i in 1..20) {
                    datas.add(BeanUser())
                }
                refresh.addData(datas)
            }, 500)
        }

    }

}