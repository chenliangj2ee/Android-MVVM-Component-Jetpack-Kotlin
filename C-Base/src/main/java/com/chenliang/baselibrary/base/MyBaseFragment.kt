package com.chenliang.baselibrary.base

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.annotation.activityRefresh
import com.chenliang.baselibrary.annotation.activityTitle
import com.chenliang.baselibrary.utils.*
import com.chenliang.baselibrary.view.MyToolBar
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.base_activity_content.*
import kotlinx.android.synthetic.main.base_fragment_content.view.*

abstract class MyBaseFragment<BINDING : ViewDataBinding, VM : ViewModel> : Fragment() {
    lateinit var mRootView: LinearLayout
    lateinit var mToolBar: MyToolBar
    lateinit var mRefresh: SmartRefreshLayout
    lateinit var mBinding: BINDING
    lateinit var mViewModel: VM
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        log("MyActivityManager", "启动》》》${javaClass.name}")
        mRootView = layoutInflater.inflate(R.layout.base_fragment_content, null) as LinearLayout
        mViewModel = MyKotlinClass.createByName<VM>(
            this::class.java.genericSuperclass.toString().split(",")[1].trim().replace(">", "")
        )!!
        initToolbar()
        bindView()

        anrCheck { initOnCreateView() }
        return mRootView
    }

    private fun initToolbar() {
        mToolBar = MyToolBar(context)
        mRootView.addView(
            mToolBar,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
        mToolBar.showLeft(false)
        mToolBar.showRight(false)
        mToolBar.setTitle(activityTitle(this))
        mToolBar.show(activityTitle(this).isNullOrEmpty().not())
    }

    open fun refresh() {
    }

    open fun stopRefresh() {
        mRefresh.finishRefresh()
    }

    private fun bindView() {
        var content = layoutInflater.inflate(layoutId(), null)
        mBinding = DataBindingUtil.bind<BINDING>(content)!!

        mRefresh = SmartRefreshLayout(context)
        mRefresh.setEnableRefresh(activityRefresh(this))
        mRefresh.setEnableRefresh(true)
        mRefresh.setEnableLoadMore(true)
        mRefresh.setRefreshHeader(ClassicsHeader(context))
        mRefresh.setOnRefreshListener {
            refresh();
        }
        mRefresh.addView(
            content, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )

        mRootView.addView(
            mRefresh,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )

        postDelayed(3000){
            mRefresh.setEnableRefresh(true)
            mRefresh.setEnableLoadMore(true)
            toast("setEnableRefresh")
        }

    }

    abstract fun initOnCreateView()
    private fun layoutId(): Int {
        return MyKotlinClass.getLayoutIdByBinding(
            requireContext(),
            this::class.java.genericSuperclass.toString().split("<")[1].split(",")[0]
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }


    var currentFragment: Fragment? = null

    fun replace(id: Int, f: Fragment) {
        if (f === currentFragment) return
        val mft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        if (currentFragment != null) mft?.hide(currentFragment!!)
        if (f.isAdded) {
            mft?.show(f)
        } else {
            mft?.add(id, f)
        }
        mft?.commitAllowingStateLoss()
        currentFragment = f
    }

    /**
     * 展示fragment
     *
     * @param f
     * @param id
     */
    open fun showFragment(id: Int, f: Fragment) {
        val mft = activity?.supportFragmentManager?.beginTransaction()
        if (f.isAdded) {
            mft?.show(f)
        } else {
            mft?.add(id, f)
        }
        mft?.commitAllowingStateLoss()
    }

    /**
     * 隐藏fragment
     *
     * @param f
     */
    open fun hideFragment(f: Fragment) {
        val mft = activity?.supportFragmentManager?.beginTransaction()
        if (f.isAdded) {
            mft?.hide(f)
        }
        mft?.commitAllowingStateLoss()
    }

    private var handler = Handler()

    var handlerRunnable = ArrayList<Runnable>()
    open fun postDelayed(delay: Long, func: () -> Unit) {
        var run = Runnable {
            func()
        }
        handler.postDelayed(run, delay)
        handlerRunnable.add(run)
    }
}