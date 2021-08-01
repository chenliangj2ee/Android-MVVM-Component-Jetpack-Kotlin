package com.chenliang.account.act

import com.chenliang.account.bean.BeanUser
import com.chenliang.account.databinding.AccountActRegisterBinding
import com.chenliang.account.vm.AccountViewModel
import com.chenliang.annotation.MyRoute
import com.chenliang.baselibrary.annotation.MyClass
import com.chenliang.baselibrary.annotation.MyField
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.base.obs
import com.chenliang.baselibrary.utils.MyCheck
import com.chenliang.baselibrary.utils.check
import com.chenliang.baselibrary.utils.sendSelf
import com.chenliang.baselibrary.utils.toast

@MyClass(myToolbarTitle = "注册")
@MyRoute(path = "/account/Register")
class RegisterActivity : MyBaseActivity<AccountActRegisterBinding, AccountViewModel>() {
    @MyField(myKey = "user")
    lateinit var user: BeanUser//通过注解获取intent传值，不指定myKey时，默认key值为属性名称

    override fun initCreate() {
        mBinding.user = user
        mBinding.act = this
    }

    fun registerAction() {

        with(user) {
            if (name.check(MyCheck.empty, "请输入账号") || password.check(MyCheck.empty, "请输入密码")) return
            mViewModel.register(name, password).obs(this@RegisterActivity) {
                it.code = 0//模拟注册成功
                it.data = user//模拟注册成功
                it.y { registerSuccess(it) }
            }
        }

    }

    private fun registerSuccess(user: BeanUser) {
        user.sendSelf(100)
        finish()

    }

}