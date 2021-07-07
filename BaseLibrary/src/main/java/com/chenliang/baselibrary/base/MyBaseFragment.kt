package com.chenliang.baselibrary.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.annotation.activityRefresh
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.base_activity_content.*
import kotlinx.android.synthetic.main.base_activity_content.base_root
import kotlinx.android.synthetic.main.base_fragment_content.*

abstract class MyBaseFragment<BINDING : ViewDataBinding> : Fragment() {
    lateinit var rootView: View
    lateinit var refresh: SmartRefreshLayout
    lateinit var binding: BINDING
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("MyActivityManager", this.javaClass.name);
        rootView = layoutInflater.inflate(R.layout.base_fragment_content, null)
        bindView()
        var onCreateStart = System.currentTimeMillis()
        initOnCreateView()
        var onCreateEnd = System.currentTimeMillis()
        if (onCreateEnd - onCreateStart > 200) {
            throw Exception("${this::class.simpleName} initOnCreateView耗时太长，请优化...")
        }
        return rootView
    }

    open fun refresh() {
    }

    open fun stopRefresh() {
        refresh.finishRefresh()
    }

    private fun bindView() {
        var content = layoutInflater.inflate(layoutId(), null)

        refresh = SmartRefreshLayout(context)
        refresh.setEnableRefresh(activityRefresh(this))
        refresh.setRefreshHeader(ClassicsHeader(context))
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

    abstract fun initOnCreateView()
    abstract fun layoutId(): Int

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}