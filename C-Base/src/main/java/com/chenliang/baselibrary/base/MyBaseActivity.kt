package com.chenliang.baselibrary.base

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import com.chenliang.annotation.ApiModel
import com.github.xubo.statusbarutils.StatusBarUtils
import com.chenliang.baselibrary.BaseInit
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.annotation.*
import com.chenliang.baselibrary.utils.*
import com.chenliang.baselibrary.view.MyNetWorkMessage
import com.chenliang.baselibrary.view.MyToolBar
import com.donkingliang.imageselector.utils.ImageSelector
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import gorden.rxbus2.RxBus
import kotlinx.android.synthetic.main.base_activity_content.*
import java.lang.Exception

abstract class MyBaseActivity<BINDING : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {
    lateinit var mToolBar: MyToolBar
    lateinit var mRefresh: SmartRefreshLayout
    lateinit var mBinding: BINDING
    lateinit var mViewModel: VM
    var mW: Int = 0
    var mH: Int = 0
    open lateinit var mHttpEvent: MyHttpEvent
    private var exitEvent: MyExitEvent? = null

    @MyField
    open var bottom = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        log("MyActivityManager", "启动-----------${javaClass.name}")
        setContentView(R.layout.base_activity_content)
        exitEvent = MyExitEvent()
        exitEvent!!.register(this)
        initSelf()
        initStatusBar()
        initToolbar()
        initNetWorkMessage()
        bindView()
        anrCheck(200) {
            initCreate()
        }

        initClick()
    }

    open fun initClick() {

    }

    private fun initSelf() {
        BaseInit.act = this
        mW = MyScreen.getScreenWidth()
        mH = MyScreen.getScreenHeight()
        mHttpEvent = MyHttpEvent(this)
        mHttpEvent.register()
        RxBus.get().register(this)
        initValueFromIntent(this)

        mViewModel = initVM(
            MyKotlinClass.getViewModelClass<VM>(
                this::class.java.genericSuperclass.toString().split(",")[1].trim()
                    .replace(">", "")!!
            )
        )!!
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

    open fun backAction(view: View) {
        finish()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { initValueFromIntent(intent) }

    }

    /**
     * 绑定view
     */
    private fun bindView() {
        var content = layoutInflater.inflate(layoutId(), null)

        mRefresh = SmartRefreshLayout(this)
        mRefresh.setEnableRefresh(activityRefresh(this))
        mRefresh.setEnableLoadMore(false)
        mRefresh.setRefreshHeader(ClassicsHeader(this))
        mRefresh.setOnRefreshListener {
            refresh();
        }



        if (isScroll(this)) {
            var scrollView = NestedScrollView(this)
            scrollView.isFocusable = false
            scrollView.addView(
                content, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            )
            mRefresh.addView(
                scrollView, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            )
        } else {
            mRefresh.isFocusable = false
            content.isFocusable = false
            mRefresh.addView(
                content, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            )
        }

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
    public fun initStatusBar() {
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
        mToolBar.show(activityTitle(this).isNullOrEmpty().not())
    }

    fun fullscreenTransparentBar(boo: Boolean) {
        if (boo) {
            MyScreen.setFullscreen(this, isShowStatusBar = false, true)
            StatusBarUtils.setStatusBarTransparen(this)
        } else {
            MyScreen.setFullscreen(this, isShowStatusBar = true, true)
            initStatusBar()
        }
    }

    /**
     * 初始化网络异常状态
     */
    private fun initNetWorkMessage() {
        var netWorkMessage = MyNetWorkMessage(this)
        base_root.addView(
            netWorkMessage,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )

        netWorkMessage.showNetworkError(myShowNetworkError(this))
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

    private fun layoutId(): Int {

        return MyKotlinClass.getLayoutIdByBinding(
            this,
            this::class.java.genericSuperclass.toString().split("<")[1].split(",")[0]
        )
    }

    abstract fun initCreate()

    override fun onResume() {
        super.onResume()
        BaseInit.act = this
        mHttpEvent.register()
    }


    override fun onStart() {
        super.onStart()
        mHttpEvent.register()
    }

    override fun onBackPressed() {
        if (isFullScreen) {
            screenPortrait()
        } else {
            super.onBackPressed()
            finish()
            if (bottom) {
                overridePendingTransition(R.anim.base_top_in, R.anim.base_bottom_out)
            } else {
                overridePendingTransition(R.anim.base_left_in, R.anim.base_right_out)
            }

        }

    }

    override fun onPause() {
        super.onPause()
        mHttpEvent.onPause()
    }

    override fun onStop() {
        super.onStop()
        mHttpEvent.unRegister()
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyResource()
    }

    private fun destroyResource() {
        try {
            mBinding.unbind()
            mHttpEvent.onDestroy()
            RxBus.get().unRegister(this)
            exitEvent?.unRegister()
            handlerRunnable.forEach { handler.removeCallbacks(it) }
        }catch (e:Exception){
            log("destroyResource Exception:${e.message}")
        }
    }


    var myUser = getBeanUser()
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (!ApiModel.release)
            mHttpEvent.dispatchTouchEvent(ev)
        return super.dispatchTouchEvent(ev)
    }

    var currentFragment: Fragment? = null

    fun replace(id: Int, f: Any?) {
        if (f == null)
            return
        f as Fragment
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

    private var handler = Handler()

    var handlerRunnable = ArrayList<Runnable>()
    open fun postDelayed(delay: Long, func: () -> Unit) {
        var run = Runnable {
            func()
        }
        handler.postDelayed(run, delay)
        handlerRunnable.add(run)
    }

    var selectImages = arrayListOf<String>()
    fun selectImage(video: Boolean) {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        ).checkPermissions(this) {
            if (it) {
                ImageSelector.builder()
                    .setCrop(true)
                    .video(video)
                    .setSelected(selectImages)
                    .start(this, REQUEST_CODE_CAMERA)
            } else {
//                toast("没有权限")
            }
        }
    }

    fun selectImage(video: Boolean, crop: Boolean) {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        ).checkPermissions(this) {
            if (it) {
                ImageSelector.builder()
                    .setCrop(crop)
                    .video(video)
                    .setSelected(selectImages)
                    .start(this, REQUEST_CODE_CAMERA)
            } else {
//                toast("没有权限")
            }
        }
    }

    fun selectImageSingle(crop: Boolean) {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        ).checkPermissions(this) {
            if (it) {
                ImageSelector.builder()
                    .setCrop(crop)
                    .setSingle(true)
                    .video(false)
                    .setSelected(selectImages)
                    .start(this, REQUEST_CODE_CAMERA)
            } else {
//                toast("没有权限")
            }
        }
    }

    fun selectImages(video: Boolean) {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        ).checkPermissions(this) {
            if (it) {
                ImageSelector.builder()
                    .setCrop(false)
                    .setSingle(false)
                    .video(video)
                    .setMaxSelectCount(9)
                    .setSelected(selectImages)
                    .start(this, REQUEST_CODE_CAMERA)
            } else {
//                toast("没有权限")
            }
        }
    }

    fun selectImages(video: Boolean, maxCount: Int) {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        ).checkPermissions(this) {
            if (it) {
                ImageSelector.builder()
                    .setCrop(false)
                    .setSingle(false)
                    .video(video)
                    .setMaxSelectCount(maxCount)
                    .setSelected(selectImages)
                    .start(this, REQUEST_CODE_CAMERA)
            } else {
//                toast("没有权限")
            }
        }
    }

    open fun resultSelectImage(array: ArrayList<String>) {
        selectImages = array
    }

    private val REQUEST_CODE_CAMERA = 2
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {

                REQUEST_CODE_CAMERA -> {
                    var images = data?.getStringArrayListExtra(ImageSelector.SELECT_RESULT)
                    if (images == null)
                        images = ArrayList<String>()
                    resultSelectImage(images!!)

                }
            }
        }
    }

    var isFullScreen = false
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isFullScreen = true;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            isFullScreen = false;
        }
    }


    open fun checkUpgrade(loading: Boolean) {


    }

    fun applyOverlays(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.data = Uri.parse("package:" + context.packageName)
            context.startActivity(intent)
        } else {
        }
    }

}