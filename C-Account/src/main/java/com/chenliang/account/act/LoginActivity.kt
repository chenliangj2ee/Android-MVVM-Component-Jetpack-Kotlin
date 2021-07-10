package com.chenliang.account.act

import com.chenliang.account.R
import com.chenliang.account.bean.BeanUser
import com.chenliang.account.databinding.AccountActLoginBinding
import com.chenliang.account.vm.AccountViewModel
import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.base.obs
import com.chenliang.baselibrary.utils.click
import com.chenliang.baselibrary.utils.hasNull
import com.chenliang.baselibrary.utils.initVM
import com.chenliang.baselibrary.utils.toast
import kotlinx.android.synthetic.main.account_act_login.*

@My(myToolbarTitle = "模块A", myToolbarShow = true, myRefresh = true)
class LoginActivity : MyBaseActivity<AccountActLoginBinding>() {
    override fun layoutId() = R.layout.account_act_login

    override fun initCreate() {

        var user = BeanUser().get()
        if (user != null) {//已登录
        }

        login.click { loginAction() }


    }

    private fun loginAction() {
        var loginVM = initVM(AccountViewModel::class.java)
        var name = account.text.toString()
        var pass = password.text.toString()

        if (hasNull(name, "请输入账号", pass, "请输入密码")) {

            return
        }

        loginVM.login(name, pass).obs(this) {
            it.c { }
            it.y { loginSucess(it.data!!) }
            it.n { loginFail(it.message) }

        }
    }

    /**
     * 登录成功
     */
    private fun loginSucess(user: BeanUser) {
        user.save()//保存user数据到sp缓存
    }

    /**
     * 登录失败
     */
    fun loginFail(message: String) {
        toast(message)
    }

}