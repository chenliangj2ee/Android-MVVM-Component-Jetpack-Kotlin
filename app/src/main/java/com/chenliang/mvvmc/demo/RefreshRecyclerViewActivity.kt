package com.chenliang.mvvmc.demo

import com.chenliang.baselibrary.annotation.MyClass
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.mvvmc.bean.BeanItem
import com.chenliang.mvvmc.databinding.ActivityRecyclerviewBinding
import com.chenliang.mvvmc.databinding.ItemRecyclerviewBinding
import kotlinx.android.synthetic.main.activity_recyclerview.*

@MyClass(mToolbarTitle = "分页")
class RefreshRecyclerViewActivity :
    MyBaseActivity<ActivityRecyclerviewBinding, DefaultViewModel>() {
    override fun initCreate() {
        refresh.bindDataToItem<BeanItem, ItemRecyclerviewBinding> { bind, bean -> bind.item = bean }
        refresh.loadData { httpGetData() }
    }

    /**
     * 分页会提前加载下一页：当查看第1页时，会自动预加载第2页，当查看第2页时，会自动加载第3页
     */
    private fun httpGetData() {
        postDelayed(300) {
            var datas = ArrayList<BeanItem>()
            for (i in 1..20) {
                var bean = BeanItem()
                bean.title = "我是item:第${refresh.pageIndex}页 ： $i"
                datas.add(bean)
            }
            refresh.addDatas(datas)
            mToolBar.setTitle("分页：${refresh.pageIndex}")
        }
    }

}