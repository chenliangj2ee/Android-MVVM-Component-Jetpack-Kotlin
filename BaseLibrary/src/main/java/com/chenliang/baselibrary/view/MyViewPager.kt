package com.chenliang.baselibrary.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


/**demo_viewpager
 * 使用说明：
1、添加fragment：addFragments(fragment1) 或 addFragments(fragment1,fragment2,...)
2、监听ViewPager滑动事件：onPageSelected{it}
3、配置TabLayout：setTabLayout(tab,titles) 或 自定义布局：setTabLayout(tab, titles,layoutId) {}
4、监听配置TabLayout点击事件：onTabSelected{it}
5、禁止ViewPager滑动：setEnableScroll(false)  或  android:tag="false"
 */

/**
 * TabLayout使用说明：https://blog.csdn.net/csdnxia/article/details/105947804
1、非自定义布局，设置字体大小、样式 app:tabTextAppearance="@style/TabLayoutTheme"，style如下：
<style name="TabLayoutTheme">
<item name="android:textSize">26sp</item>
<item name="android:textStyle">bold</item>
</style>
 */

class MyViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    private var isScroll = true
    private var fragments = ArrayList<Fragment>()
    private var tabLayout: TabLayout? = null
    private var titles = ArrayList<Any>()

    init {
        if (this.tag == "false") setEnableScroll(false)
    }

    /**
     * 添加fragment
     */
    fun addFragments(vararg fs: Fragment) {
        fs.forEach { fragments.add(it) }
        this.adapter =
            FragmentAdapter(fragments, (context as FragmentActivity).supportFragmentManager)
    }


    /**
     * tab被选中回调方法
     */
    var tabSelectedFun: (position :Int) -> Unit = { Unit }
    fun onTabSelected(func: (position :Int) -> Unit) {
        tabSelectedFun = func
    }

    /**
     * ViewPager被选中回调方法
     */
    fun onPageSelected(func: (position: Int) -> Unit) {
        setOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                func(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }


    /**
     * 设置TabLayout
     */
    fun setTabLayout(tabLayout: TabLayout, titles: ArrayList<String>) {
        this.tabLayout = tabLayout
        titles.forEach { tabLayout.addTab(tabLayout.newTab().setText(it)) }
        this.titles.add(titles)
        initTabLayoutListener()
    }

    /**
     *设置自定义TabLayout布局
     *可以通过tab.customView获取到布局ViewItem，通过tab.position获得Item对应的数据index
     */
    fun <T> setTabLayout(
        tabLayout: TabLayout,
        titles: ArrayList<T>,
        layoutId: Int,
        tabLayoutFun: (tab: TabLayout.Tab) -> Unit
    ) {
        this.tabLayout = tabLayout

        tabLayout.removeAllTabs()
        titles.forEach { _ ->
            val tab = tabLayout.newTab()
            tab.customView = View.inflate(context, layoutId, null)
            tabLayout.addTab(tab)
            tabLayoutFun(tab)
        }
        this.titles.add(titles)
        initTabLayoutListener()
    }

    /**
     * tabLayout事件监听，ViewPager关联
     */
    private fun initTabLayoutListener() {
        tabLayout?.addOnTabSelectedListener(onTabSelectedListener)
        addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout?.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(this))
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return if (isScroll) {
            super.onTouchEvent(ev)
        } else {
            true
        }
    }

    public fun setEnableScroll(scroll: Boolean) {
        isScroll = scroll
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, false)
    }



    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if (isScroll) {
            super.onInterceptTouchEvent(ev)
        } else {
            false
        }
    }

    private class FragmentAdapter(fragments: ArrayList<Fragment>, fm: FragmentManager) :
        FragmentPagerAdapter(
            fm
        ) {

        var fs: ArrayList<Fragment> = fragments

        override fun getItem(position: Int): Fragment {
            return fs[position]
        }

        override fun getCount(): Int {
            return fs.size
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        }
    }

    private var onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {

            if (tabSelectedFun != null) {
                tabSelectedFun(tab!!.position)
            }

        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}

        override fun onTabReselected(tab: TabLayout.Tab?) {}
    }
}