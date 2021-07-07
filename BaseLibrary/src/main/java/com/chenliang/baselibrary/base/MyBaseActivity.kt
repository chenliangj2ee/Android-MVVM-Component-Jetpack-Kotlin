package com.chenliang.baselibrary.base

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.annotation.activityRefresh
import com.chenliang.baselibrary.annotation.activityTitle
import com.chenliang.baselibrary.annotation.activityToolbar
import com.chenliang.baselibrary.extend.show
import com.chenliang.baselibrary.net.MyHttpEvent
import com.chenliang.baselibrary.view.MyToolBar
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.base_activity_content.*

abstract class MyBaseActivity<BINDING : ViewDataBinding> : AppCompatActivity() {
    lateinit var toolBar: MyToolBar
    lateinit var refresh: SmartRefreshLayout
    lateinit var binding: BINDING
    lateinit var httpEvent: MyHttpEvent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("MyActivityManager", this.javaClass.name);
        setContentView(R.layout.base_activity_content)
//        binding = DataBindingUtil.setContentView<BINDING>(this,layoutId())
        initToolbar()
        bindView()
        httpEvent = MyHttpEvent(this)
        var onCreateStart = System.currentTimeMillis()
        initCreate()
        var onCreateEnd = System.currentTimeMillis()
        if (onCreateEnd - onCreateStart > 200) {
            throw Exception("${this::class.simpleName} initCreate耗时太长，请优化...")
        }
    }

    open fun refresh() {
    }

    open fun stopRefresh() {
        refresh.finishRefresh()
    }

    private fun bindView() {
        var content = layoutInflater.inflate(layoutId(), null)

        refresh = SmartRefreshLayout(this)
        refresh.setEnableRefresh(activityRefresh(this))
        refresh.setRefreshHeader(ClassicsHeader(this))
        refresh.setOnRefreshListener {
            refresh();
        }
        refresh.addView(
            content, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )
        binding = DataBindingUtil.bind<BINDING>(content)!!
        base_root.addView(
            refresh,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )
    }


    private fun initToolbar() {
        toolBar = MyToolBar(this)
        base_root.addView(
            toolBar,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )

        toolBar.setTitle(activityTitle(this))
        toolBar.show(activityToolbar(this))
    }

    fun setToolbarTitle(title: String) {
        if (title.isNullOrEmpty())
            return
        toolBar.setTitle(title)
        toolBar.show(true)
    }

    abstract fun layoutId(): Int
    abstract fun initCreate()

    override fun onResume() {
        super.onResume()
        httpEvent.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        httpEvent.onPause()
    }


    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
        httpEvent.onDestroy()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        httpEvent.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
}