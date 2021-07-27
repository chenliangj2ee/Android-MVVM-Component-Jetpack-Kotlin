package com.chenliang.account.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.chenliang.account.databinding.AccountFgMyBinding
import com.chenliang.baselibrary.annotation.MyIntent
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseFragment

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.account.fragment
 * @author: chenliang
 * @date: 2021/07/27
 */

@Route(path = "/account/my")
class MyFragment : MyBaseFragment<AccountFgMyBinding, DefaultViewModel>() {

    @MyIntent
    lateinit var value: String

    override fun initOnCreateView() {
        mBinding.value = value
    }
}