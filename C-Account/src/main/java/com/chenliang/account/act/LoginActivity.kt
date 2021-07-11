package com.chenliang.account.act

import com.chenliang.account.R
import com.chenliang.account.bean.BeanUser
import com.chenliang.account.databinding.AccountActLoginBinding
import com.chenliang.account.vm.AccountViewModel
import com.chenliang.baselibrary.annotation.My
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.base.obs
import com.chenliang.baselibrary.utils.*
import gorden.rxbus2.Subscribe
import kotlinx.android.synthetic.main.account_act_login.*


@My(myToolbarTitle = "登录", myToolbarShow = true)
class LoginActivity : MyBaseActivity<AccountActLoginBinding>() {
    override fun layoutId() = R.layout.account_act_login
    var user = BeanUser();
    lateinit var loginVM: AccountViewModel
    override fun initCreate() {
        loginVM = initVM(AccountViewModel::class.java)
        mBinding.user = user
        mBinding.act = this

    }

    fun registerAction() {
        goto(RegisterActivity::class.java)
    }

    fun loginAction() {
        with(user) {
            if (hasNull(name, "请输入账号", password, "请输入密码")) return

            loginVM.login(name, password).obs(this@LoginActivity) {
                it.c { }//登录不处理缓存
                it.y { loginSuccess(it.data!!) }
                it.n { loginFail(it.message) }

            }
        }
    }

    /**
     * 登录成功
     */
    private fun loginSuccess(user: BeanUser) {
        user.save()
    }

    /**
     * 登录失败,登录失败，创建个账号信息，保存，模拟登录成功
     */
    private fun loginFail(message: String) {
        var user = BeanUser()
        user.name = "tom"
        user.age = 12
        user.save()

        goto("/app/main", "username", "tom", "age", 15)
        finish()
    }


}