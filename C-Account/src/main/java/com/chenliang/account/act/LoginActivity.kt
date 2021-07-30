package com.chenliang.account.act

import com.chenliang.account.bean.BeanUser
import com.chenliang.account.databinding.AccountActLoginBinding
import com.chenliang.account.vm.AccountViewModel
import com.chenliang.annotation.MyRoute
import com.chenliang.baselibrary.annotation.MyClass
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.base.obs
import com.chenliang.baselibrary.utils.*
import gorden.rxbus2.Subscribe
import kotlinx.android.synthetic.main.account_act_login.*

@MyClass(myToolbarTitle = "登录")
@MyRoute(path = "/account/Login")
class LoginActivity : MyBaseActivity<AccountActLoginBinding, AccountViewModel>() {
    var user = BeanUser()
    override fun initCreate() {
        mBinding.user = user
        mBinding.act = this
        //监听编辑框输入状态，手机号设置成130 7876 7657 格式
        account.changed { account.setText(it.insert(" ", 3, 7)) }
    }

    //去注册
    fun registerAction() {
        goto(RegisterActivity::class.java, "user", user)

    }

    fun loginAction() {
        //登录验证
        if (user.name.check(MyCheck.empty, "请输入账号", MyCheck.mobilePhone, "手机号格式不正确") ||
            user.password.check(MyCheck.empty, "请输入密码", MyCheck.LENGTH(6, 12), "密码长度在6-12之间")
        ) return
        //登录接口
        mViewModel.login(user.name, user.password).obs(this@LoginActivity) {
            it.code = 0//模拟登录成功
            it.data = user//模拟登录成功
            it.y { loginSuccess(it.data!!) }
        }
    }

    private fun loginSuccess(user: BeanUser) {
        user.save()
        goto("/app/main", "username", "tom", "age", 15)
        finish()
    }

    //注册后，登录界面回显账号信息
    @Subscribe(code = 100)
    fun eventRegister(user: BeanUser) {
        this.user = user
        user.password = ""
        mBinding.user = user
    }

}