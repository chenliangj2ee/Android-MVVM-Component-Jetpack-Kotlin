package com.chenliang.baselibrary.view

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.View.OnScrollChangeListener
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.base.MyBaseAdapter
import com.chenliang.baselibrary.base.MyBaseBean
import com.chenliang.baselibrary.utils.*
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.base_layout_empty.view.*

/**
 * chenliang
 * email:chenliangj2ee@163.com
 * 2021-03-13
 */

/*
 *
 * 布局说明：
    SmartRefreshLayout
        FrameLayout
            GroupView
            MyRecyclerView
 */

/*
 * 布局说明：
        <com.chenliang.library.view.MyRefreshRecyclerView
            android:id="@+id/refresh"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:empty_layout="@layout/layout_empty"
            app:item="@layout/item_product" />
布局参数说明：
            app:empty_layout="@layout/layout_empty"：列表数据为空时显示的布局
            app:item="@layout/item_layout"：item所对应的layout布局
 代码：
 * 绑定item
        //单type类型
        refresh.bindData<BeanItem> { (it.binding as ItemRecyclerviewBinding).item = it }
        // or
        refresh.bindData<BeanItem,ItemRecyclerviewBinding> { bind,bean->bind.item = bean }

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
*数据加载监听：下拉刷新，上拉加载
*         refresh.loadData { 分页请使用refresh.pageSize,refresh.pageIndex数据传递给后台 }
数据更新：数据加载后，添加到列表：
        recyclerView.addDatas(list)
 */

class MyRefreshRecyclerView : SmartRefreshLayout {

    private var rootView: FrameLayout? = null
    private var emptyLayout: View? = null
    private var topLayout: View? = null
    open var recyclerView: MyRecyclerView? = null
    private var params: ViewGroup.LayoutParams? = null

    private var emptyLayoutId: Int = 0
    private var topLayoutId: Int = 0
    private var layoutManagerValue = 0
    private var myOrientation = 0;
    private var spanCount = 0
    private var layoutIds = ArrayList<Int>()
    var layoutId = -1
    var layoutMap = HashMap<Int, Int>()
    var pageSize: Int = 20
    var defaultPageIndex: Int = 1
    var pageIndex: Int = defaultPageIndex
    private var lastId = "0"
    var enableTop = false
    var loading = false
    var divideColor = 0
    private var dividerHeight = 0F
    var marginLeft = 0
    var marginRight = 0
    private var loadFun: (() -> Unit?)? = null

    var isLastIdModel = false;

    var mEmptyIcon = 0
    var mEmptyTitle = ""

    fun lastId(): String {
        isLastIdModel = true;
        return lastId;
    }

    constructor(context: Context?) : super(context!!) {}

    constructor(context: Context?, attributeSet: AttributeSet) : super(context!!, attributeSet) {
        val typedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.MyRefreshRecyclerView)
        emptyLayoutId =
            typedArray.getResourceId(R.styleable.MyRefreshRecyclerView_my_empty_layout, 0)
        layoutId = typedArray.getResourceId(R.styleable.MyRefreshRecyclerView_my_item_layout, 0)
        topLayoutId = typedArray.getResourceId(R.styleable.MyRefreshRecyclerView_my_top_layout, 0)
        enableTop = typedArray.getBoolean(R.styleable.MyRefreshRecyclerView_my_top_enable, false)

        layoutManagerValue =
            typedArray.getInt(R.styleable.MyRefreshRecyclerView_my_layout_manager, 0)
        spanCount = typedArray.getInt(R.styleable.MyRefreshRecyclerView_my_span_count, 2)
        myOrientation = typedArray.getInt(R.styleable.MyRefreshRecyclerView_my_orientation, 1)
        divideColor = typedArray.getColor(
            R.styleable.MyRefreshRecyclerView_my_divider_color,
            Color.parseColor("#E6F0F2")
        )
        dividerHeight =
            typedArray.getLayoutDimension(R.styleable.MyRefreshRecyclerView_my_divider_height, 0)
                .toFloat()
        marginLeft =
            typedArray.getLayoutDimension(
                R.styleable.MyRefreshRecyclerView_my_divider_marginLeft,
                0
            )
        marginRight =
            typedArray.getLayoutDimension(
                R.styleable.MyRefreshRecyclerView_my_divider_marginRight,
                0
            )
        mEmptyIcon = typedArray.getResourceId(R.styleable.MyRefreshRecyclerView_my_empty_icon, 0)
        mEmptyTitle = typedArray.getString(R.styleable.MyRefreshRecyclerView_my_empty_title)?:""
        params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        setRefreshHeader(ClassicsHeader(context))
        setRefreshFooter(ClassicsFooter(context))
//        var header=BezierRadarHeader(context)
//        header.setPrimaryColor(Color.TRANSPARENT)
//        setRefreshHeader(header)
//        header.setAccentColorId(R.color.text_default)
//        header.setPrimaryColorId(R.color.text_default)
//        header.setPrimaryColor(Color.TRANSPARENT)

        setEnableAutoLoadMore(false)
        initRoot(context, attributeSet)
        initEmpty(context, attributeSet)
        initRecyclerView(context, attributeSet)
        initTop(context, attributeSet)

        initLoadListener()
        setEnableAutoLoadMore(true)
        setReboundDuration(300)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun bindTypeToItemView(type: Int, layoutId: Int): MyRefreshRecyclerView {
        layoutMap[type] = layoutId
        return this
    }

    private fun initRoot(context: Context?, attributeSet: AttributeSet) {
        rootView = FrameLayout(context!!)
        addView(rootView, params)
    }


    fun scrollToPosition(position: Int): MyRefreshRecyclerView {
        recyclerView?.scrollToPosition(position)
        return this
    }

    private fun initEmpty(context: Context?, attributeSet: AttributeSet) {
        if (emptyLayoutId != 0) {
            emptyLayout = View.inflate(context, emptyLayoutId, null)
            var emptyImageView=  emptyLayout!!.findViewById<ImageView>(R.id.emptyIcon)
            var emptyTextView=  emptyLayout!!.findViewById<TextView>(R.id.emptyTitle)
            if(this.mEmptyIcon!=0){
                emptyImageView.setImageResource(mEmptyIcon)
            }
            if(mEmptyTitle.isNotEmpty()){
                emptyTextView.text=mEmptyTitle
            }
            rootView!!.addView(emptyLayout, params)
            emptyLayout!!.show(true)
        }
    }

    private fun initRecyclerView(context: Context?, attributeSet: AttributeSet) {
        recyclerView = MyRecyclerView(context!!, attributeSet)
        recyclerView?.isFocusable = false
        recyclerView?.isNestedScrollingEnabled = false
        recyclerView?.addItemDecoration(
            DividerItemLine(
                context,
                dividerHeight,
                divideColor,
                marginLeft,
                marginRight
            )
        )
        rootView!!.addView(recyclerView, params)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerView!!.setOnScrollChangeListener(OnScrollChangeListener { _, _, _, _, _ ->
                if (topLayout != null) {
                    if (recyclerView!!.getPosition() > 50 && enableTop) {
                        topLayout!!.show(true)
                    } else {
                        topLayout!!.show(false)
                    }

                }
            })
        }
    }

    fun selected(position: Int) {
        getData<MyBaseBean>().forEach { it.itemSelected = false }
        getData<MyBaseBean>()[position].itemSelected = true
        notifyDataSetChanged()
    }

    /**
     * 预加载下一页
     */
    fun preload() {
        recyclerView?.autoLoadMore()
    }

    private fun initTop(context: Context?, attributeSet: AttributeSet) {
        if (topLayoutId == 0)
            topLayoutId = R.layout.base_layout_top
        if (topLayoutId != 0) {
            var params =
                FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            params.gravity = Gravity.RIGHT + Gravity.BOTTOM
            params.rightMargin = 100
            params.bottomMargin = 100
            topLayout = View.inflate(context, topLayoutId, null)
            rootView!!.addView(topLayout, params)
            topLayout!!.show(false)
            topLayout!!.setOnClickListener {
                recyclerView!!.smoothScrollToPosition(0)
                Handler().postDelayed(Runnable { recyclerView!!.scrollToPosition(0) }, 500)
                topLayout!!.show(false)
            }
        }
    }


    var enableRefresh = true
    var enableLoadMore = true

    fun disable(): MyRefreshRecyclerView {
        setEnableRefresh(false)
        setEnableLoadMore(false)
        enableRefresh = false
        enableLoadMore = false
        return this
    }

    fun enable(): MyRefreshRecyclerView {
        setEnableRefresh(true)
        setEnableLoadMore(true)
        enableRefresh = true
        enableLoadMore = true
        return this
    }

    fun setDisableLoadMore(): MyRefreshRecyclerView {
        setEnableLoadMore(false)
        enableLoadMore = false
        return this
    }

    fun refresh(): MyRefreshRecyclerView {
        autoRefresh()
        return this
    }

    fun resetPageIndex() {
        pageIndex = defaultPageIndex
        lastId = "0"
    }

    private fun initLoadListener() {
        setEnableRefresh(true)
        setEnableLoadMore(true)

        //下拉刷新
        this.setOnRefreshListener {

            if (MyNetWork.hasNetWork()) {
                pageIndex = defaultPageIndex;
                lastId = "0"
                loadFun?.invoke()
                postDelayed(Runnable {
                    stop()
                    emptyLayout?.show(recyclerView!!.getData<MyBaseBean>().size == 0)
                    recyclerView!!.show(recyclerView!!.getData<MyBaseBean>().size > 0)
                }, 2000)
            } else {
                toast("暂无网络")
                stop()
            }


        }

        //加载更多
        this.setOnLoadMoreListener {
            pageIndex =
                recyclerView!!.getData<MyBaseBean>().size / pageSize + defaultPageIndex
            if (recyclerView!!.isLoading())
                return@setOnLoadMoreListener
            loadFun?.invoke()
            postDelayed(Runnable {
                stop()
                emptyLayout?.show(recyclerView!!.getData<MyBaseBean>().size == 0)
                recyclerView!!.show(recyclerView!!.getData<MyBaseBean>().size > 0)
            }, 2000)
        }
    }


    /**
     *
     */
    fun <D : MyBaseBean> bindData(func: (bind: D) -> Unit): MyRefreshRecyclerView {

        recyclerView!!.layoutIds = layoutMap
        recyclerView!!.layoutId = layoutId
        recyclerView!!.layoutManagerValue = layoutManagerValue
        recyclerView!!.spanCount = spanCount
        recyclerView!!.myOrientation = myOrientation
        recyclerView!!.binding(func)
        return this
    }


    /**
     *
     */
    fun <D : MyBaseBean, B : ViewDataBinding> bindDataToItem(func: (bind: B, bean: D) -> Unit): MyRefreshRecyclerView {

        recyclerView!!.layoutIds = layoutMap
        recyclerView!!.layoutId = layoutId
        recyclerView!!.layoutManagerValue = layoutManagerValue
        recyclerView!!.spanCount = spanCount
        recyclerView!!.myOrientation = myOrientation
        recyclerView!!.binding2<D, B>(func)
        return this
    }

    fun <D : MyBaseBean> test(c: Class<D>, size: Int): MyRefreshRecyclerView {
        var datas = ArrayList<D>()
        for (i in 1..size) {
            datas.add(c.newInstance())
        }
        this.addDatas(datas)
        return this
    }

    fun <D : MyBaseBean> test(c: Class<D>): MyRefreshRecyclerView {
        var datas = ArrayList<D>()
        for (i in 1..20) {
            datas.add(c.newInstance())
        }
        this.addDatas(datas)
        return this
    }

    fun <T : MyBaseBean> loadData(
        mutableLiveData: MutableLiveData<ArrayList<T>>,
        func: () -> Unit
    ): MyRefreshRecyclerView {
        loadFun = func
        recyclerView!!.addLoading(func)
        autoRefresh()
        observeData(mutableLiveData)
        return this
    }

    fun loadData(
        func: () -> Unit
    ): MyRefreshRecyclerView {
        loadFun = func
//        if(pageIndex==defaultPageIndex){
//            loadFun?.invoke()
//        }
        recyclerView!!.addLoading(func)
//        autoRefresh()
        loadFun?.invoke()
        return this
    }

    public fun <D : MyBaseBean> addCaches(list: List<D>?): MyRefreshRecyclerView {
        if (list != null) {
            if (isLastIdModel) {
                if (lastId == "0") {
                    recyclerView!!.clearData()
                }
            } else {
                if (pageIndex == defaultPageIndex) {
                    recyclerView!!.clearData()
                }
            }
            recyclerView!!.addCache(list)

            emptyLayout?.show(recyclerView!!.getData<MyBaseBean>().size == 0)
            recyclerView!!.show(recyclerView!!.getData<MyBaseBean>().size > 0)

//            this.setEnableLoadMore(list.size >= pageSize)
        }
        stop()
        return this
    }


//    public fun <D : MyBaseBean> addCache(page: BeanPage<List<D>>?): MyRefreshRecyclerView {
//        page?.let {
//            if (!page.lastId.isNullOrEmpty())
//                lastId = page.lastId
//            this.addCaches(page.records)
//        }
//        return this
//    }
//
//    public fun <D : MyBaseBean> addData(page: BeanPage<List<D>>?): MyRefreshRecyclerView {
//        page?.let {
//            if (!page.lastId.isNullOrEmpty())
//                lastId = page.lastId + ""
//            this.addDatas(page.records)
//        }
//        return this
//    }

    public fun <D : MyBaseBean> addDatas(list: List<D>?): MyRefreshRecyclerView {
        setReboundDuration(200)
        if (list != null && list.isNotEmpty()) {
            if (isLastIdModel) {
                if (lastId == "0") {
                    recyclerView!!.clearData()
                }
            } else {
                if (pageIndex == defaultPageIndex) {
                    recyclerView!!.clearData()
                }
            }
            recyclerView!!.addData(list)

            emptyLayout?.show(recyclerView!!.getData<MyBaseBean>().size == 0)
            recyclerView!!.show(recyclerView!!.getData<MyBaseBean>().size > 0)

            if (enableLoadMore)
                this.setEnableLoadMore(list.size >= pageSize)
        } else {
            if (isLastIdModel) {
                if (lastId == "0") {
                    recyclerView!!.clearData()
                }
            } else {
                if (pageIndex == defaultPageIndex) {
                    recyclerView!!.clearData()
                }
            }
            emptyLayout?.show(recyclerView!!.getData<MyBaseBean>().size == 0)
            recyclerView!!.show(recyclerView!!.getData<MyBaseBean>().size > 0)
        }
        stop()
        return this
    }

    public fun <D : MyBaseBean> addData(bean: D?): MyRefreshRecyclerView {
        setReboundDuration(200)
        if (bean != null) {
            recyclerView!!.getData<D>().add(bean)

            emptyLayout?.show(recyclerView!!.getData<MyBaseBean>().size == 0)
            recyclerView!!.show(recyclerView!!.getData<MyBaseBean>().size > 0)

        } else {
            if (isLastIdModel) {
                if (lastId == "0") {
                    recyclerView!!.clearData()
                }
            } else {
                if (pageIndex == defaultPageIndex) {
                    recyclerView!!.clearData()
                }
            }
            emptyLayout?.show(recyclerView!!.getData<MyBaseBean>().size == 0)
            recyclerView!!.show(recyclerView!!.getData<MyBaseBean>().size > 0)
        }
        stop()
        return this
    }

    fun notifyDataSetChanged(): MyRefreshRecyclerView {
        this.recyclerView?.listAdapter?.notifyDataSetChanged()
        return this
    }

    fun remove(position: Int): MyRefreshRecyclerView {
        (recyclerView?.listAdapter as MyBaseAdapter<*>).data.removeAt(position)
        this.recyclerView?.listAdapter?.notifyDataSetChanged()
        emptyLayout?.show(recyclerView!!.getData<MyBaseBean>().size == 0)
        recyclerView!!.show(recyclerView!!.getData<MyBaseBean>().size > 0)
        return this
    }

    fun removeData(bean: MyBaseBean): MyRefreshRecyclerView {
        (recyclerView?.listAdapter as MyBaseAdapter<*>).data.removeAt(bean.itemPosition)
        this.recyclerView?.listAdapter?.notifyDataSetChanged()
        emptyLayout?.show(recyclerView!!.getData<MyBaseBean>().size == 0)
        recyclerView!!.show(recyclerView!!.getData<MyBaseBean>().size > 0)
        return this
    }

    fun clearData(): MyRefreshRecyclerView {
        recyclerView?.clearData()
        return this
    }

    fun stop(): MyRefreshRecyclerView {
        this.finishRefresh()
        this.finishLoadMore()
        recyclerView!!.finishLoading()
        pageIndex =
            recyclerView!!.getData<MyBaseBean>().size / pageSize + defaultPageIndex
        return this
    }

    private fun <T : MyBaseBean> observeData(mutableLiveData: MutableLiveData<ArrayList<T>>): MyRefreshRecyclerView {
        mutableLiveData.observe(this.context as LifecycleOwner, Observer<ArrayList<T>> {
            this.addDatas(it)
        })
        return this
    }

    public fun <D : MyBaseBean> getData(): ArrayList<D> {
        return recyclerView?.getData()!!
    }

    public fun getDataSize(): Int {
        return recyclerView?.adapter?.itemCount ?: 0
    }
}