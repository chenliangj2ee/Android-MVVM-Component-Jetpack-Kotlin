package com.chenliang.mvvmc.act

import android.widget.Toast
import com.chenliang.baselibrary.annotation.MVVM
import com.chenliang.baselibrary.base.MyBaseActivity
import com.chenliang.baselibrary.extend.click
import com.chenliang.baselibrary.extend.hasNull
import com.chenliang.baselibrary.extend.initVM
import com.chenliang.baselibrary.extend.toAct
import com.chenliang.baselibrary.net.c
import com.chenliang.baselibrary.net.n
import com.chenliang.baselibrary.net.obs
import com.chenliang.baselibrary.net.y
import com.chenliang.mvvmc.R
import com.chenliang.mvvmc.bean.BeanUser
import com.chenliang.mvvmc.databinding.ActivityMainBinding
import com.chenliang.mvvmc.vm.AccountViewModel
import kotlinx.android.synthetic.main.activity_login.*

@MVVM(title = "登录", toolbar = true, refresh = false)
class LoginActivity : MyBaseActivity<ActivityMainBinding>() {
    override fun layoutId() = R.layout.activity_login
    override fun initCreate() {
        login.click { loginAction() }
    }

    private fun loginAction() {
        var loginVM = initVM(AccountViewModel::class.java)
        var name = account.text.toString()
        var pass = password.text.toString()

        if (hasNull(name, pass)) {
            Toast.makeText(this, "请输入账号或密码", Toast.LENGTH_LONG).show()
            return
        }

        loginVM.test(name, pass).obs(this) {
            it.c { }
            it.y { loginSucess(it.data) }
            it.n { loginFail(it.message) }
        }
    }

    /**
     * 登录成功
     */
    private fun loginSucess(user: BeanUser?) {
        toAct(MainActivity::class.java)
    }

    /**
     * 登录失败
     */
    fun loginFail(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}