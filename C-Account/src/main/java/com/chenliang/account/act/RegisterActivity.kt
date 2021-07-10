package com.chenliang.account.act

import com.chenliang.account.R
import com.chenliang.account.bean.BeanUser
import com.chenliang.account.databinding.AccountActRegisterBinding
import com.chenliang.account.vm.AccountViewModel
import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.base.obs
import com.chenliang.baselibrary.utils.*
import kotlinx.android.synthetic.main.account_act_login.*

@My(myToolbarTitle = "注册", myToolbarShow = true)
class RegisterActivity : MyBaseActivity<AccountActRegisterBinding>() {
    override fun layoutId() = R.layout.account_act_register

    override fun initCreate() {
        register.click { loginAction() }
    }

    private fun loginAction() {
        var loginVM = initVM(AccountViewModel::class.java)
        var name = account.text.toString()
        var pass = password.text.toString()

        if (hasNull(name, "请输入账号", pass, "请输入密码")) {

            return
        }

        loginVM.register(name, pass).obs(this) {
            it.c { }//注册不处理缓存
            it.y { loginSucess(it.data!!) }
            it.n { loginFail(it.message) }

        }
    }

    /**
     * 登录成功
     */
    private fun loginSucess(user: BeanUser) {
        user.save()
        finish()
    }

    /**
     * 登录失败
     */
    fun loginFail(message: String) {
        toast(message)

        var user = BeanUser()
        user.name = account.text.toString()
        user.password = password.text.toString()
        user.sendSelf(100)
        finish()
    }

}