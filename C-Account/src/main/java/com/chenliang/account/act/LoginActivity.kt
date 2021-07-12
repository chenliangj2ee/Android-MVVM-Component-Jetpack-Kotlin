package com.chenliang.account.act

import com.chenliang.account.bean.BeanUser
import com.chenliang.account.databinding.AccountActLoginBinding
import com.chenliang.account.vm.AccountViewModel
import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.base.obs
import com.chenliang.baselibrary.utils.goto
import com.chenliang.baselibrary.utils.hasNull
import gorden.rxbus2.Subscribe

@My(myToolbarTitle = "登录")
class LoginActivity : MyBaseActivity<AccountActLoginBinding, AccountViewModel>() {
    var user = BeanUser()
    override fun initCreate() {
        mBinding.user = user
        mBinding.act = this
    }

    fun registerAction() {
        goto(RegisterActivity::class.java)
    }

    fun loginAction() {
        with(user) {
            if (hasNull(name, "请输入账号", password, "请输入密码")) return
            mViewModel.login(name, password).obs(this@LoginActivity) {
                it.y { loginSuccess(it.data!!) }
            }
        }
    }

    private fun loginSuccess(user: BeanUser) {
        user.save()
        goto("/app/main", "username", "tom", "age", 15)
        finish()
    }

    @Subscribe(code = 100)
    fun eventRegister(user: BeanUser) {  mBinding.user = user   }

}