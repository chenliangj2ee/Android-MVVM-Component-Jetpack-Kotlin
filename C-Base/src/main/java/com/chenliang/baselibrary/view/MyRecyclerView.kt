package com.chenliang.baselibrary.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.base.MyBaseAdapter
import com.chenliang.baselibrary.base.MyBaseBean

/**
 * chenliang
 * email:chenliangj2ee@163.com
 * 2021-03-13
 */

/*
 * 使用说明：
 *
布局：
    <com.chenliang.baselibrary.view.MyRecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:item="@layout/item_layout"/>
布局参数说明：
        app:item="@layout/item_layout"：item所对应的layout布局

代码：
        recyclerView!!.binding(R.layout.item,
            fun(binding: ItemBinding, bean: MyBean) {
                binding.bean = bean
            })
        或者：xml布局里指定app:item="@layout/item_layout"，使用以下形式
        recyclerView!!.binding(fun(binding: ItemBinding, bean: MyBean) {
                binding.bean = bean
            })
代码参数说明：
        R.layout.item：你的item布局
        MyBean：你的数据model
        ItemBinding：你的item布局对应的binding
        binding.bean：bean为你的item布局里声明的变量名称（你可以修改为其他名称）
数据更新：数据加载后，添加到列表：
        recyclerView.addData(list)
 */
class MyRecyclerView : RecyclerView {
    lateinit var listAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    var layoutIds = HashMap<Int, Int>()
    var layoutId = -1
    var loadFun: (() -> Unit?)? = null
    constructor(context: Context?) : super(context!!) {

    }

    constructor(context: Context?, attributeSet: AttributeSet) : super(context!!, attributeSet) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.MyRecyclerView)
        layoutId = typedArray.getResourceId(R.styleable.MyRefreshRecyclerView_my_item_layout, 0)

    }


    fun <D : MyBaseBean> binding(
        func: (d: D) -> Unit
    ) {

        if (layoutManager == null)
            layoutManager = LinearLayoutManager(context)
        if (layoutIds.isEmpty())
            layoutIds[31415926] = layoutId
        listAdapter = MyBaseAdapter<D>(context, layoutIds,func)
        adapter = listAdapter
    }

    fun addLoading(func: (() -> Unit?)){
        loadFun=func
        (listAdapter as MyBaseAdapter<*>).loadFun=func
    }

    public fun <D : MyBaseBean> addData(list: ArrayList<D>) {
        (listAdapter as MyBaseAdapter<D>).data.addAll(list)
        listAdapter.notifyDataSetChanged()
    }

    public fun <D : MyBaseBean> clearData() {

        (listAdapter as MyBaseAdapter<D>).data.clear()
        listAdapter.notifyDataSetChanged()
    }

    public fun <D : MyBaseBean> getData(): ArrayList<D> {
        return (listAdapter as MyBaseAdapter<D>).data

    }

    fun  finishLoading(){
        (listAdapter as MyBaseAdapter<*>).finishLoading()
    }
    fun  isLoading(): Boolean {
       return  (listAdapter as MyBaseAdapter<*>).loading
    }
    fun  getPosition(): Int {
       return (listAdapter as MyBaseAdapter<*>).position
    }
}