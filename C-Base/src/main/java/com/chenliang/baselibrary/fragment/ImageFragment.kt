package com.chenliang.baselibrary.fragment

import com.chenliang.baselibrary.databinding.BaseFragmentImageBinding
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseFragment

/**
 * author:chenliang
 * date:2021/12/22
 */
class ImageFragment(url: String) : MyBaseFragment<BaseFragmentImageBinding, DefaultViewModel>() {
    var url = url
    override fun initOnCreateView() {
        mBinding.path = url
    }
}