package com.chenliang.account.fragment

import com.chenliang.account.databinding.AccountFgMyBinding
import com.chenliang.annotation.MyRoute
import com.chenliang.baselibrary.annotation.MyClass
import com.chenliang.baselibrary.annotation.MyField
import com.chenliang.baselibrary.base.DefaultViewModel
import com.chenliang.baselibrary.base.MyBaseFragment

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.account.fragment
 * @author: chenliang
 * @date: 2021/07/27
 */

@MyClass(mShowNetworkError = true)
@MyRoute(path = "/account/my")
class MyFragment : MyBaseFragment<AccountFgMyBinding, DefaultViewModel>() {

    @MyField
    lateinit var value: String

    override fun initOnCreateView() {
        mBinding.value = value
    }
}