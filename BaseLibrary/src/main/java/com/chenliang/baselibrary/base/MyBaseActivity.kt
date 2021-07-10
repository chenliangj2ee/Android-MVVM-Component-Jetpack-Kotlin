package com.chenliang.baselibrary.base

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.annotation.activityFullScreen
import com.chenliang.baselibrary.annotation.activityRefresh
import com.chenliang.baselibrary.annotation.activityTitle
import com.chenliang.baselibrary.annotation.activityToolbar
import com.chenliang.baselibrary.net.utils.MyHttpEvent
import com.chenliang.baselibrary.utils.MyApp
import com.chenliang.baselibrary.utils.log
import com.chenliang.baselibrary.utils.show
import com.chenliang.baselibrary.view.MyToolBar
import com.github.xubo.statusbarutils.StatusBarUtils
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import gorden.rxbus2.RxBus
import kotlinx.android.synthetic.main.base_activity_content.*

abstract class MyBaseActivity<BINDING : ViewDataBinding> : AppCompatActivity() {
    lateinit var mToolBar: MyToolBar
    lateinit var mRefresh: SmartRefreshLayout
    lateinit var mBinding: BINDING
    open lateinit var mHttpEvent: MyHttpEvent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("MyActivityManager", javaClass.name)
        setContentView(R.layout.base_activity_content)
        RxBus.get().register(this)
        mHttpEvent = MyHttpEvent(this)
        MyApp.activityToTranslucent(this)
        initStatusBar()
        initToolbar()
        bindView()

        var onCreateStart = System.currentTimeMillis()
        initCreate()
        var onCreateEnd = System.currentTimeMillis()
        if (onCreateEnd - onCreateStart > 200) {
            throw Exception("${this::class.simpleName} initCreate耗时太长，请优化...")
        }
    }

    /**
     * 刷新回调
     */
    open fun refresh() {
    }
    /**
     * 结束刷新
     */
    open fun stopRefresh() {
        mRefresh.finishRefresh()
    }

    /**
     * 绑定view
     */
    private fun bindView() {
        var content = layoutInflater.inflate(layoutId(), null)

        mRefresh = SmartRefreshLayout(this)
        mRefresh.setEnableRefresh(activityRefresh(this))
        mRefresh.setRefreshHeader(ClassicsHeader(this))
        mRefresh.setOnRefreshListener {
            refresh();
        }
        mRefresh.addView(
            content, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )
        mBinding = DataBindingUtil.bind<BINDING>(content)!!
        base_root.addView(
            mRefresh,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )
    }

    /**
     * 状态栏默认状态，白底，黑字
     */
    private fun initStatusBar() {
        if (activityFullScreen(this)) {
            StatusBarUtils.setStatusBarTransparen(this)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            StatusBarUtils.setStatusBarColor(this, Color.WHITE)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

    }

    /**
     * 初始化标题栏
     */
    private fun initToolbar() {
        mToolBar = MyToolBar(this)
        base_root.addView(
            mToolBar,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )

        mToolBar.setTitle(activityTitle(this))
        mToolBar.show(activityToolbar(this))
    }

    /**
     * 设置标题栏标题
     * @param title String
     */
    fun setToolbarTitle(title: String) {
        if (title.isNullOrEmpty())
            return
        mToolBar.setTitle(title)
        mToolBar.show(true)
    }

    abstract fun layoutId(): Int
    abstract fun initCreate()

    override fun onResume() {
        super.onResume()
        mHttpEvent.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        overridePendingTransition(R.anim.base_left_in, R.anim.base_right_out)
    }

    override fun onPause() {
        super.onPause()
        mHttpEvent.onPause()
    }

    open fun initFragment() {

    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.get().unRegister(this)
        mBinding.unbind()
        mHttpEvent.onDestroy()
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        mHttpEvent.dispatchTouchEvent(ev)
        return super.dispatchTouchEvent(ev)
    }

    var currentFragment: Fragment? = null

    fun replace(id: Int, f: Fragment) {
        if (f === currentFragment) return
        val mft: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (currentFragment != null) mft.hide(currentFragment!!)
        if (f.isAdded) {
            mft.show(f)
        } else {
            mft.add(id, f)
        }
        mft.commitAllowingStateLoss()
        currentFragment = f
    }

    /**
     * 展示fragment
     *
     * @param f
     * @param id
     */
    open fun showFragment(id: Int, f: Fragment) {
        val mft = supportFragmentManager.beginTransaction()
        if (f.isAdded) {
            mft.show(f)
        } else {
            mft.add(id, f)
        }
        mft.commitAllowingStateLoss()
    }

    /**
     * 隐藏fragment
     *
     * @param f
     */
    open fun hideFragment(f: Fragment) {
        val mft = supportFragmentManager.beginTransaction()
        if (f.isAdded) {
            mft.hide(f)
        }
        mft.commitAllowingStateLoss()
    }

    open fun delayed(delay: Long, func: () -> Unit) {
        Handler().postDelayed(Runnable {
            func()
        }, delay)
    }

}