package com.chenliang.baselibrary.fragment

import com.chenliang.baselibrary.databinding.BaseFragmentWebviewBinding
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseFragment
import com.chenliang.baselibrary.annotation.MyField
import kotlinx.android.synthetic.main.base_fragment_webview.view.*

/**
 * author:chenliang
 * date:2022/4/6
 */
class WebViewFragment: MyBaseFragment<BaseFragmentWebviewBinding, DefaultViewModel>() {
    @MyField
    var url="";
    @MyField
    var type=0;
    override fun initOnCreateView() {
       mRootView.webview.load(url,type)
    }

}