package com.chenliang.baselibrary.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
        app:my_item_layout="@layout/item_layout"/>
布局参数说明：
        app:my_item_layout="@layout/item_layout"：item所对应的layout布局

代码：
        //单type类型
        recyclerview.binding<BeanItem> {
            (it.binding as ItemRecyclerviewBinding).item = it
        }
       //多type类型
        recyclerview.bindTypeToItemView(0, R.layout.item_recyclerview0)
        recyclerview.bindTypeToItemView(1, R.layout.item_recyclerview1)
        recyclerview.binding<BeanItem> {

         if(it.type==0){
            (it.binding as ItemRecyclerviewBinding0).item = it
         }
         if(it.type==1){
            (it.binding as ItemRecyclerviewBinding1).item = it
         }

        }

代码参数说明：
        BeanItem：你的数据model
        it.binding ：你的item布局对应的binding
        item为你的item布局里声明的变量名称（你可以修改为其他名称）
        it:你的数据model实列
数据更新：数据加载后，添加到列表：
        recyclerView.addData(list)
 */
class MyRecyclerView : RecyclerView {
    lateinit var listAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    var layoutIds = HashMap<Int, Int>()
    var layoutId = -1
    var loadFun: (() -> Unit?)? = null
    var layoutManagerValue = 0
    var myOrientation = 0
    var spanCount = 0

    constructor(context: Context?) : super(context!!) {

    }

    constructor(context: Context?, attributeSet: AttributeSet) : super(context!!, attributeSet) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.MyRecyclerView)
        layoutId = typedArray.getResourceId(R.styleable.MyRecyclerView_my_item_layout, 0)
        layoutManagerValue = typedArray.getInt(R.styleable.MyRecyclerView_my_layout_manager, 0)
        spanCount = typedArray.getInt(R.styleable.MyRecyclerView_my_span_count, 2)
        myOrientation = typedArray.getInt(R.styleable.MyRefreshRecyclerView_my_orientation, 1)
    }

    fun bindTypeToItemView(type: Int, layoutId: Int) {
        layoutIds[type] = layoutId
    }

    fun <D : MyBaseBean> binding(
        func: (d: D) -> Unit
    ) {

        if (layoutManager == null) {
            if (layoutManagerValue == 0) {
                var m = LinearLayoutManager(context)
                if (myOrientation == 0) {
                    m.orientation = RecyclerView.HORIZONTAL
                } else {
                    m.orientation = RecyclerView.VERTICAL
                }
                layoutManager = m
            }
            if (layoutManagerValue == 1) {
                var m = GridLayoutManager(context, spanCount)
                if (myOrientation == 0) {
                    m.orientation = RecyclerView.HORIZONTAL
                } else {
                    m.orientation = RecyclerView.VERTICAL
                }
                layoutManager = m
            }
            if (layoutManagerValue == 2) {
                var m = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
                if (myOrientation == 0) {
                    m.orientation = RecyclerView.HORIZONTAL
                } else {
                    m.orientation = RecyclerView.VERTICAL
                }
                layoutManager = m

            }
        }

        if (layoutIds.isEmpty())
            layoutIds[31415926] = layoutId
        listAdapter = MyBaseAdapter<D>(context, layoutIds, func)
        adapter = listAdapter
    }

    fun addLoading(func: (() -> Unit?)) {
        loadFun = func
        (listAdapter as MyBaseAdapter<*>).loadFun = func
    }

    public fun <D : MyBaseBean> addData(list: ArrayList<D>) {
        (listAdapter as MyBaseAdapter<D>).data.addAll(list)
        listAdapter.notifyDataSetChanged()
    }

    fun clearData() {

        (listAdapter as MyBaseAdapter<*>).data.clear()
        listAdapter.notifyDataSetChanged()
    }

    public fun <D : MyBaseBean> getData(): ArrayList<D> {
        return (listAdapter as MyBaseAdapter<D>).data

    }

    fun finishLoading() {
        (listAdapter as MyBaseAdapter<*>).finishLoading()
    }

    fun isLoading(): Boolean {
        return (listAdapter as MyBaseAdapter<*>).loading
    }

    fun getPosition(): Int {
        return (listAdapter as MyBaseAdapter<*>).position
    }
}